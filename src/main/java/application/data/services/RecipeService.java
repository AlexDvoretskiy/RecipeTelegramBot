package application.data.services;


import application.data.persistance.entities.Recipe;
import application.data.persistance.entities.RecipeIngredient;
import application.data.persistance.entities.RecipeInstruction;
import application.data.persistance.entities.RecipeTag;
import application.data.persistance.dto.RecipeIngredientDto;
import application.data.persistance.dto.RecipeInstructionDto;
import application.data.persistance.dto.RecipeDto;
import application.data.persistance.dto.RecipeTagDto;
import application.data.persistance.repositories.RecipesRepositoryImpl;
import application.data.persistance.repositories.interfaces.RecipesRepository;

import java.util.ArrayList;
import java.util.List;

public class RecipeService {
	private RecipesRepository recipesRepository = new RecipesRepositoryImpl();

	public Recipe findRecipeById(Long id) {
		return recipesRepository.findByID(id);
	}

	public List<RecipeDto> findRecipeByTitleLike(String query, int recordLimit) {
		List<Recipe> recipes = recipesRepository.findByTitleLikeWithRecordLimit(query, recordLimit);
		List<RecipeDto> recipeDtos = new ArrayList<>();

		for (Recipe recipe : recipes) {
			RecipeDto recipeDTO = RecipeDto.builder()
					.title(recipe.getTitle())
					.image(recipe.getImage())
					.cookingTime(recipe.getCookingTime())
					.portionAmount(recipe.getPortionAmount())
					.description(recipe.getDescription())
					.advice(recipe.getAdvice())
			.build();

			setRecipeIngredientsToDTO(recipeDTO, recipe.getRecipeIngredients());
			setRecipeInstructionsToDTO(recipeDTO, recipe.getRecipeInstructions());
			setRecipeTagToDTO(recipeDTO, recipe.getRecipeTags());

			recipeDtos.add(recipeDTO);
		}
		return recipeDtos;
	}

	public void saveRecipe(RecipeDto recipeDto) {
		Recipe recipe = Recipe.builder()
				.title(recipeDto.getTitle())
				.image(recipeDto.getImage())
				.cookingTime(recipeDto.getCookingTime())
				.portionAmount(recipeDto.getPortionAmount())
				.description(recipeDto.getDescription())
				.advice(recipeDto.getAdvice())
		.build();

		setRecipeIngredientsFromDTO(recipe, recipeDto.getIngredientDTOs());
		setRecipeInstructionsFromDTO(recipe, recipeDto.getInstructionDTOs());
		setRecipeTagsFromDTO(recipe, recipeDto.getTagDTOs());

		if(recipesRepository.findByTitle(recipe.getTitle()).isEmpty()) {
			recipesRepository.save(recipe);
		}
	}

	public void updateRecipe(Recipe recipe) {
		recipesRepository.update(recipe);
	}

	public void deleteRecipe(Recipe recipe) {
		recipesRepository.delete(recipe);
	}

	private void setRecipeIngredientsFromDTO(Recipe recipe, List<RecipeIngredientDto> ingredientDTOs) {
		List<RecipeIngredient> ingredients = new ArrayList<>();

		for (RecipeIngredientDto ingredientDTO : ingredientDTOs) {
			RecipeIngredient recipeIngredient = RecipeIngredient.builder()
					.description(ingredientDTO.getDescription())
					.amount(ingredientDTO.getAmount())
					.recipe(recipe)
			.build();
			ingredients.add(recipeIngredient);
		}
		recipe.setRecipeIngredients(ingredients);
	}

	private void setRecipeIngredientsToDTO(RecipeDto recipeDTO, List<RecipeIngredient> recipeIngredients) {
		List<RecipeIngredientDto> recipeIngredientDtos = new ArrayList<>();

		for (RecipeIngredient recipeIngredient : recipeIngredients) {
			RecipeIngredientDto ingredientDTO = RecipeIngredientDto.builder()
					.description(recipeIngredient.getDescription())
					.amount(recipeIngredient.getAmount())
			.build();
			recipeIngredientDtos.add(ingredientDTO);
		}
		recipeDTO.setIngredientDTOs(recipeIngredientDtos);
	}

	private void setRecipeInstructionsFromDTO(Recipe recipe, List<RecipeInstructionDto> instructionDTOs) {
		List<RecipeInstruction> instructions = new ArrayList<>();

		for (RecipeInstructionDto instructionDTO : instructionDTOs) {
			RecipeInstruction recipeInstruction = RecipeInstruction.builder()
					.stepImage(instructionDTO.getStepImage())
					.stepNumber(instructionDTO.getStepNumber())
					.stepDescription(instructionDTO.getStepDescription())
					.recipe(recipe)
			.build();
			instructions.add(recipeInstruction);
		}
		recipe.setRecipeInstructions(instructions);
	}

	private void setRecipeInstructionsToDTO(RecipeDto recipeDTO, List<RecipeInstruction> recipeInstructions) {
		List<RecipeInstructionDto> recipeInstructionDtos = new ArrayList<>();

		for (RecipeInstruction recipeInstruction : recipeInstructions) {
			RecipeInstructionDto instructionDTO = RecipeInstructionDto.builder()
					.stepNumber(recipeInstruction.getStepNumber())
					.stepDescription(recipeInstruction.getStepDescription())
					.stepImage(recipeInstruction.getStepImage())
			.build();
			recipeInstructionDtos.add(instructionDTO);
		}
		recipeDTO.setInstructionDTOs(recipeInstructionDtos);
	}

	private void setRecipeTagsFromDTO(Recipe recipe, List<RecipeTagDto> tagDTOs) {
		List<RecipeTag> tags = new ArrayList<>();

		for (RecipeTagDto tagDTO : tagDTOs) {
			RecipeTag recipeTag = RecipeTag.builder()
					.description(tagDTO.getDescription())
					.recipe(recipe)
			.build();
			tags.add(recipeTag);
		}
		recipe.setRecipeTags(tags);
	}

	private void setRecipeTagToDTO(RecipeDto recipeDTO, List<RecipeTag> recipeTags) {
		List<RecipeTagDto> tagDTOs = new ArrayList<>();

		for (RecipeTag recipeTag : recipeTags) {
			RecipeTagDto recipeTagDTO = RecipeTagDto.builder()
					.description(recipeTag.getDescription())
			.build();
			tagDTOs.add(recipeTagDTO);
		}
		recipeDTO.setTagDTOs(tagDTOs);
	}
}
