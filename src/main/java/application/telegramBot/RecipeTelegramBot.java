package application.telegramBot;

import application.telegramBot.properties.BotConfiguration;
import application.telegramBot.tasks.RecipeBotTask;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class RecipeTelegramBot extends AbilityBot {
	private ExecutorService executorService;

	public RecipeTelegramBot(DefaultBotOptions botOptions) {
		super(BotConfiguration.getBotToken(), BotConfiguration.getBotUsername(), botOptions);
		executorService = Executors.newFixedThreadPool(BotConfiguration.getThreadPoolSize());
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage()) {
			Message message = update.getMessage();
			if (!message.hasText()) {
				return;
			}
			RecipeBotTask task = new RecipeBotTask(this, message, false);
			executorService.execute(task);
		} else if (update.hasCallbackQuery()) {
			RecipeBotTask task = new RecipeBotTask(this, update.getCallbackQuery(), true);
			executorService.execute(task);
		}
	}

	@Override
	public int creatorId() {
		return 0;
	}
}
