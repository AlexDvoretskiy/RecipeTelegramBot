package application.webParsing;


import application.data.persistance.dto.RecipeIngredientDto;
import application.data.persistance.dto.RecipeInstructionDto;
import application.data.persistance.dto.RecipeTagDto;
import application.telegramBot.properties.BotConfiguration;
import application.webParsing.parsingConfiguration.WebResource;
import application.webParsing.parsingUtils.StringParserUtils;
import application.webParsing.parsingUtils.JsonIngredientParser;

import lombok.Data;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Data
public class ParsedWebPage {
	private static final Logger log = LogManager.getLogger(ParsedWebPage.class);
	private static final String IMAGE_TAG = "img";
	private static final String IMAGE_SRC_ATTRIBUTE = "src";
	private static final String IMAGE_FOLDER_PATH = BotConfiguration.getProjectDirectory() + "images/";

	private String pageURL;

	private String title;
	private String image;
	private String cookingTime;
	private String portionAmount;
	private String description;
	private String advice;

	private List<RecipeTagDto> tagPojos;
	private List<RecipeInstructionDto> instructionPojos;
	private List<RecipeIngredientDto> ingredientPojos;


	public ParsedWebPage(String title, String pageURL, WebResource.ItemPage itemPage) {
		this.title = title;
		this.pageURL = pageURL;

		Document doc = null;
		try {
			doc = Jsoup.connect(pageURL).get();
		} catch (Exception e) {
			log.error(String.format("Ошибка при получении объекта html страницы [%s]", pageURL), e);
		}

		if (doc != null) {
			cookingTime = parseElementByClass(doc, itemPage.getCookingTimeClass(), true);
			portionAmount = parseElementByClass(doc, itemPage.getPortionAmountClass(), false);
			description = parseElementByClass(doc, itemPage.getDescriptionClass(), false);
			advice = parseElementByClass(doc, itemPage.getAdviceClass(), false);
			ingredientPojos = parseRecipeIngredients(doc, itemPage.getIngredientsClass());
			instructionPojos = parseRecipeInstructions(doc, itemPage.getInstructionsClass());
			image = saveImageAndReference(doc, itemPage.getImageClass());

			tagPojos = new ArrayList<>();
		}
	}

	private String parseElementByClass(Document doc, String elementClassParam, boolean deleteLastWord) {
		String[] elementsClass = elementClassParam.split(";");

		String result = null;
		try {
			for (String elementClass : elementsClass) {
				Elements elements = doc.getElementsByClass(elementClass);

				if (elements.size() > 0) {
					result = elements.first().text();

					if (deleteLastWord) {
						result = StringParserUtils.deleteLastWord(result);
					}
					if (StringUtils.isEmpty(result)) {
						log.info(String.format("Параметр [%s] не был получен при парсинге html страницы [%s]", elementClassParam, pageURL));
					}
					break;
				}
			}
		} catch (Exception e) {
			log.warn(String.format("Ошибка получения параметра [%s] при парсинге html страницы [%s]", elementClassParam, pageURL));
		}
		return result;
	}

	private List<RecipeIngredientDto> parseRecipeIngredients(Document doc, String elementClassParam) {
		List<RecipeIngredientDto> recipeIngredients = new ArrayList<>();

		try {
			Elements elements = doc.getElementsByClass(elementClassParam);
			Set<String> jsonStrings = StringParserUtils.getJsonString(elements.first().html());

			for (String jsonString : jsonStrings) {
				JsonIngredientParser jsonParser = new JsonIngredientParser(jsonString);

				if (jsonParser.isNotEmpty()) {
					RecipeIngredientDto recipeIngredient = new RecipeIngredientDto(jsonParser.getName(), jsonParser.getAmount());
					recipeIngredients.add(recipeIngredient);
				}
			}
		} catch (Exception e) {
			log.error(String.format("Ошибка получения параметра [%s] при парсинге html страницы [%s]", elementClassParam, pageURL), e);
		}
		return recipeIngredients;
	}

	private List<RecipeInstructionDto> parseRecipeInstructions(Document doc, String elementClassParam) {
		List<RecipeInstructionDto> instructions = new ArrayList<>();
		String[] elementsClass = elementClassParam.split(";");

		try {
			for (String elementClass : elementsClass) {
				Elements elements = doc.getElementsByClass(elementClass);

				int stepNumber = 1;
				for (Element element : elements) {
					String description = element.text();

					if (StringUtils.isNotEmpty(description)) {
						description = StringParserUtils.deleteNumerationIfExist(description);
						RecipeInstructionDto recipeInstruction = new RecipeInstructionDto(null, stepNumber++, description);
						instructions.add(recipeInstruction);
					}
				}

				if (CollectionUtils.isEmpty(instructions)) {
					log.info(String.format("Параметр [%s] не был получен при парсинге html страницы [%s]", elementClassParam, pageURL));
				} else {
					break;
				}
			}
		} catch (Exception e) {
			log.error(String.format("Ошибка получения параметра [%s] при парсинге html страницы [%s]", elementClassParam, pageURL), e);
		}
		return instructions;
	}

	private String saveImageAndReference(Document doc, String imageClassParam) {
		String fileName = null;
		try {
			Elements elements = doc.getElementsByClass(imageClassParam);
			String url = elements.first().select(IMAGE_TAG).attr(IMAGE_SRC_ATTRIBUTE);

			String formatName = StringParserUtils.getImageFormatNameFromURL(url);
			fileName = StringParserUtils.generateImageName(formatName);
			BufferedImage img = ImageIO.read(new URL(url));

			File file = new File(IMAGE_FOLDER_PATH + fileName);
			ImageIO.write(img, formatName, file);
		} catch (Exception e) {
			log.error(String.format("Ошибка сохранения изображения по параметру [%s] при парсинге html страницы [%s]", imageClassParam, pageURL), e);
			return null;
		}
		return fileName;
	}

	public boolean areMainFieldsNotEmpty() {
		return StringUtils.isNotEmpty(title)
		       && StringUtils.isNotEmpty(cookingTime)
		       && CollectionUtils.isNotEmpty(ingredientPojos)
		       && CollectionUtils.isNotEmpty(instructionPojos);
	}
}
