package application.data.persistance.dto;

import application.webParsing.ParsedWebPage;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
@NoArgsConstructor
public class RecipeDto {
	private String tempId;
	private String title;
	private String image;
	private String cookingTime;
	private String portionAmount;
	private String description;
	private String advice;

	private List<RecipeIngredientDto> ingredientDTOs;
	private List<RecipeInstructionDto> instructionDTOs;
	private List<RecipeTagDto> tagDTOs;


	public RecipeDto(ParsedWebPage parsedWebPage) {
		this.title = parsedWebPage.getTitle();
		this.image = parsedWebPage.getImage();
		this.cookingTime = parsedWebPage.getCookingTime();
		this.portionAmount = parsedWebPage.getPortionAmount();
		this.description = parsedWebPage.getDescription();
		this.advice = parsedWebPage.getAdvice();

		this.ingredientDTOs = parsedWebPage.getIngredientPojos();
		this.instructionDTOs = parsedWebPage.getInstructionPojos();
		this.tagDTOs = parsedWebPage.getTagPojos();
	}

	@Builder
	public RecipeDto(String title, String image, String cookingTime, String portionAmount, String description, String advice) {
		this.title = title;
		this.image = image;
		this.cookingTime = cookingTime;
		this.portionAmount = portionAmount;
		this.description = description;
		this.advice = advice;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		RecipeDto recipeDTO = (RecipeDto) obj;

		return StringUtils.defaultIfEmpty(this.title, StringUtils.EMPTY).equals(recipeDTO.getTitle()) &&
		       StringUtils.defaultIfEmpty(this.cookingTime, StringUtils.EMPTY).equals(recipeDTO.getCookingTime()) &&
		       StringUtils.defaultIfEmpty(this.description, StringUtils.EMPTY).equals(recipeDTO.getDescription()) &&
		       StringUtils.defaultIfEmpty(this.portionAmount, StringUtils.EMPTY).equals(recipeDTO.getPortionAmount());
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(title).append("\n")
				.append(cookingTime).append("\n\n")
				.append("Ингредиенты: \n")
				.append(getIngredients()).append("\n")
				.append(getInstructions()).append("\n");
		return stringBuilder.toString();
	}

	public String getHtmlDescription() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<strong>").append(title).append("</strong>").append("\n")
				.append("<i>").append(cookingTime).append("</i>").append("\n\n")
				.append("<i>").append("Ингредиенты: \n").append("</i>")
				.append(getIngredients()).append("\n")
				.append(getInstructions()).append("\n");
		return stringBuilder.toString();
	}

	private String getIngredients() {
		StringBuilder stringBuilder = new StringBuilder();

		for (RecipeIngredientDto ingredientDTO : ingredientDTOs) {
			stringBuilder.append(ingredientDTO.getDescription()).append(" - ").append(ingredientDTO.getAmount()).append("\n");
		}
		return stringBuilder.toString();
	}

	private String getInstructions() {
		StringBuilder stringBuilder = new StringBuilder();

		for (RecipeInstructionDto instructionDTO : instructionDTOs) {
			stringBuilder.append(instructionDTO.getStepNumber()).append(". ")
					.append(instructionDTO.getStepDescription()).append("\n");
		}
		return stringBuilder.toString();
	}
}
