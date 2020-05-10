package application.data.persistance.entities;

import application.data.persistance.tables.RecipeInstructionsTableDesc;
import application.data.persistance.tables.RecipesTableDesc;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Data
@Entity
@NoArgsConstructor
@Table(name = RecipeInstructionsTableDesc.systemName)
public class RecipeInstruction {

	@Id
	@Column(name = RecipeInstructionsTableDesc.idField)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = RecipeInstructionsTableDesc.stepImageField)
	private String stepImage;

	@Column(name = RecipeInstructionsTableDesc.stepNumberField)
	private int stepNumber;

	@Column(name = RecipeInstructionsTableDesc.stepDescriptionField)
	private String stepDescription;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = RecipesTableDesc.idField)
	private Recipe recipe;

	@Builder
	public RecipeInstruction(String stepImage, int stepNumber, String stepDescription, Recipe recipe) {
		this.stepImage = stepImage;
		this.stepNumber = stepNumber;
		this.stepDescription = stepDescription;
		this.recipe = recipe;
	}
}
