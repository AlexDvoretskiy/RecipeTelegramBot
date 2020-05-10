package application.data.persistance.entities;


import application.data.persistance.tables.RecipeTagTableDesc;
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
@Table(name = RecipeTagTableDesc.systemName)
public class RecipeTag {

	@Id
	@Column(name = RecipeTagTableDesc.idField)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = RecipeTagTableDesc.tagDescriptionField)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = RecipesTableDesc.idField)
	private Recipe recipe;

	@Builder
	public RecipeTag(String description, Recipe recipe) {
		this.description = description;
		this.recipe = recipe;
	}
}
