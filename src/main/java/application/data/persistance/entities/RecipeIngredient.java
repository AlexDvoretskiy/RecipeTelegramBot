package application.data.persistance.entities;


import application.data.persistance.tables.IngredientTableDesc;
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
@Table(name = IngredientTableDesc.systemName)
public class RecipeIngredient {

	@Id
	@Column(name = IngredientTableDesc.idField)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = IngredientTableDesc.ingredientDescriptionField)
	private String description;

	@Column(name = IngredientTableDesc.ingredientAmountField)
	private String amount;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = RecipesTableDesc.idField)
	private Recipe recipe;

	@Builder
	public RecipeIngredient(String description, String amount, Recipe recipe) {
		this.description = description;
		this.amount = amount;
		this.recipe = recipe;
	}
}
