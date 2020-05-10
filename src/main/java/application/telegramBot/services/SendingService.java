package application.telegramBot.services;


import application.data.persistance.dto.RecipeDto;
import application.telegramBot.RecipeTelegramBot;
import application.telegramBot.properties.BotConfiguration;
import application.telegramBot.reply.CommandReply;
import application.telegramBot.reply.RecipeReply;
import application.telegramBot.reply.Reply;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SendingService {
	private static final Logger log = LogManager.getLogger(SendingService.class);

	private static volatile SendingService instance;
	private ConcurrentHashMap<Long, RecipeReply> cacheMap = new ConcurrentHashMap<>();

	private SendingService() {

	}

	public static SendingService getInstance() {
		SendingService localInstance = instance;
		if (localInstance == null) {
			synchronized (SendingService.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new SendingService();
				}
			}
		}
		return localInstance;
	}

	public void sendReply(RecipeTelegramBot telegramBot, Reply reply) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(reply.getChatId());

		if (reply instanceof RecipeReply) {
			RecipeReply recipeReply = (RecipeReply) reply;

			List<List<InlineKeyboardButton>> rowList= new ArrayList<>();
			List<RecipeDto> recipes = recipeReply.getAllRecipes();

			if (CollectionUtils.isEmpty(recipes)) {
				String errorMessage = "Мне не удалось найти информацию по Вашему запросу =( \n Пожалуйста, попробуйте еще раз";
				sendMessage.setText(errorMessage);
			} else {
				for (RecipeDto recipe : recipes) {
					List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
					keyboardButtons.add(new InlineKeyboardButton().setText(recipe.getTitle()).setCallbackData(recipe.getTempId()));
					rowList.add(keyboardButtons);
				}

				InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
				inlineKeyboardMarkup.setKeyboard(rowList);
				sendMessage.setText("Вот что мне удалось найти по вашему запросу:").setReplyMarkup(inlineKeyboardMarkup);

				cacheMap.put(reply.getChatId(), recipeReply);
			}
		}else if (reply instanceof CommandReply) {
			sendMessage.setText(((CommandReply) reply).getDescription());
		} else {
			String errorMessage = "Упс... Что-то пошло не так. Мне не удалось распознать Ваш запрос. Пожалуйста, попробуйте еще раз";
			sendMessage.setText(errorMessage);
		}
		send(telegramBot, sendMessage);
	}

	public void sendSelectedRecipeFromCache(RecipeTelegramBot telegramBot, Long chatId, String request) {
		RecipeReply recipeReply = cacheMap.get(chatId);
		SendMessage sendMessage = new SendMessage();
		sendMessage.setParseMode(ParseMode.HTML);

		if (recipeReply == null) {
			String errorMessage = "Упс... Что-то пошло не так. Боту не удалось получить данные по Вашему запросу";
			sendMessage.setText(errorMessage);
			return;
		}

		RecipeDto recipe = recipeReply.getRecipeById(request);

		if (recipe == null) {
			String errorMessage = "Упс... Что-то пошло не так. Боту не удалось получить данные по Вашему запросу";
			sendMessage.setText(errorMessage);
			return;
		}

		File file = new File(BotConfiguration.getProjectDirectory() + "images/" + recipe.getImage());

		if (file.exists()) {
			SendPhoto sendPhoto = new SendPhoto();
			sendPhoto.setChatId(chatId);
			sendPhoto.setNewPhoto(file);
			sendPhoto(telegramBot, sendPhoto);
		}

		sendMessage.setChatId(chatId);
		sendMessage.setText(recipe.getHtmlDescription());
		send(telegramBot, sendMessage);
	}

	public void sendAwaitingReply(RecipeTelegramBot telegramBot, Long chatId) {
		final String awaitMessage = "Подождите, пожалуйста, пока я собираю рецепты =)";
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
		sendMessage.setText(awaitMessage);
		send(telegramBot, sendMessage);
	}

	private void send(RecipeTelegramBot telegramBot, SendMessage sendMessage) {
		try {
			telegramBot.execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void sendPhoto(RecipeTelegramBot telegramBot, SendPhoto sendPhoto) {
		try {
			telegramBot.sendPhoto(sendPhoto);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
