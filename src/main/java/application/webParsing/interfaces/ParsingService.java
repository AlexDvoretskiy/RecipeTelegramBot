package application.webParsing.interfaces;


import application.data.persistance.dto.RecipeDto;

import java.util.List;

public interface ParsingService {

	List<RecipeDto> getRecipesFromWebResources(String userRequest);

}
