package application.data.persistance.repositories.interfaces;


import application.data.persistance.entities.Recipe;

import java.util.List;

public interface RecipesRepository {

	Recipe findByID(Long id);

	List<Recipe> findByTitle(String title);

	List<Recipe> findByTitleLikeWithRecordLimit (String value, int recordLimit);

	void save(Recipe recipe);

	void update(Recipe recipe);

	void delete(Recipe recipe);

}
