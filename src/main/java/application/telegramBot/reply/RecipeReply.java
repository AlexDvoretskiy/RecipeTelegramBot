package application.telegramBot.reply;


import application.data.persistance.dto.RecipeDto;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


public class RecipeReply extends Reply {
	private List<RecipeDto> recipeList;

	public RecipeReply() {
		recipeList = new ArrayList<>();
	}

	public void setTempIds() {
		for (int i = 0; i < recipeList.size(); i++) {
			recipeList.get(i).setTempId(String.valueOf(i));
		}
	}

	public RecipeDto getRecipeById(String id) {
		for (RecipeDto recipeDTO : recipeList) {
			if (recipeDTO.getTempId().equals(id)) {
				return recipeDTO;
			}
		}
		return null;
	}

	public boolean notContainsRecipe(RecipeDto recipeDTO) {
		for (RecipeDto recipe : recipeList) {
			if (recipe.equals(recipeDTO)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		return CollectionUtils.isEmpty(recipeList);
	}

	public List<RecipeDto> getAllRecipes() {
		return recipeList;
	}

	public void addRecipeToReply(RecipeDto recipeDTO) {
		recipeList.add(recipeDTO);
	}

	public void addAllRecipesToReply(List<RecipeDto> recipeDtos) {
		recipeList.addAll(recipeDtos);
	}

	public int size() {
		return recipeList.size();
	}
}
