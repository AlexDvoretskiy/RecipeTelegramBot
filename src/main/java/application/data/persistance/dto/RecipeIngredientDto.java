package application.data.persistance.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipeIngredientDto {
	private String description;
	private String amount;

	public RecipeIngredientDto(String description, String amount) {
		this.description = description;
		this.amount = amount;
	}
}
