package application.data.persistance.entities;

import application.data.persistance.tables.RecipesTableDesc;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@Table(name = RecipesTableDesc.systemName)
public class Recipe {

	@Id
	@Column(name = RecipesTableDesc.idField)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = RecipesTableDesc.titleField)
	private String title;

	@Column(name = RecipesTableDesc.imageField)
	private String image;

	@Column(name = RecipesTableDesc.cookingTimeField)
	private String cookingTime;

	@Column(name = RecipesTableDesc.portionAmountField)
	private String portionAmount;

	@Column(name = RecipesTableDesc.recipeDescriptionField)
	private String description;

	@Column(name = RecipesTableDesc.recipeAdviceField)
	private String advice;

	@OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	private List<RecipeTag> recipeTags;

	@OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	private List<RecipeInstruction> recipeInstructions;

	@OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private List<RecipeIngredient> recipeIngredients;

	@Builder
	public Recipe(String title, String image, String cookingTime, String portionAmount, String description, String advice) {
		this.title = title;
		this.image = image;
		this.cookingTime = cookingTime;
		this.portionAmount = portionAmount;
		this.description = description;
		this.advice = advice;
	}
}
