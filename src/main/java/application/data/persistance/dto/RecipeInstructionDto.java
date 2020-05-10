package application.data.persistance.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipeInstructionDto {
	private String stepImage;
	private int stepNumber;
	private String stepDescription;

	public RecipeInstructionDto(String stepImage, int stepNumber, String stepDescription) {
		this.stepImage = stepImage;
		this.stepNumber = stepNumber;
		this.stepDescription = stepDescription;
	}
}
