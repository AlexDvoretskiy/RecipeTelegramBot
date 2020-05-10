package application.data.persistance.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RecipeTagDto {
	private String description;

	public RecipeTagDto(String description) {
		this.description = description;
	}
}
