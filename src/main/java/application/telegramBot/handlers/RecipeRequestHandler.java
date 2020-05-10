package application.telegramBot.handlers;


import application.data.persistance.dto.RecipeDto;
import application.data.services.RecipeService;
import application.telegramBot.commands.Command;
import application.telegramBot.properties.BotConfiguration;
import application.telegramBot.reply.RecipeReply;
import application.telegramBot.reply.Reply;
import application.webParsing.WebParsingService;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;


public class RecipeRequestHandler extends AbstractHandler {
	private WebParsingService parsingService;
	private RecipeService recipeService;


	public RecipeRequestHandler(Command command) {
		super(command);
		parsingService = new WebParsingService(BotConfiguration.getRecordParsingLimit());
		recipeService = new RecipeService();
	}

	@Override
	public Reply handleRequest() {
		RecipeReply reply = new RecipeReply();
		List<RecipeDto> recipes = recipeService.findRecipeByTitleLike(command.getDescription(), BotConfiguration.getPreferredRecordOutputLimit());

		if (CollectionUtils.isEmpty(recipes) || recipes.size() < BotConfiguration.getPreferredRecordOutputLimit()) {
			if (CollectionUtils.isNotEmpty(recipes)) {
				reply.addAllRecipesToReply(recipes);
			}

			List<RecipeDto> parsedRecipes = parsingService.getRecipesFromWebResources(command.getDescription());
			if (CollectionUtils.isNotEmpty(parsedRecipes)) {
				for (RecipeDto parsedRecipe : parsedRecipes) {
					if (reply.size() == BotConfiguration.getPreferredRecordOutputLimit()) {
						break;
					}

					if (reply.notContainsRecipe(parsedRecipe)) {
						reply.addRecipeToReply(parsedRecipe);
						recipeService.saveRecipe(parsedRecipe);
					}
				}
			}
		} else {
			reply.addAllRecipesToReply(recipes);
		}

		reply.setTempIds();
		return reply;
	}
}
