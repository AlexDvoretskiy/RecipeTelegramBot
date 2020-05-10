package application.webParsing;


import application.data.persistance.dto.RecipeDto;
import application.webParsing.interfaces.ParsingService;
import application.webParsing.parsingConfiguration.WebParsingConfiguration;
import application.webParsing.parsingConfiguration.WebResource;
import application.utils.QueryParamsParser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WebParsingService implements ParsingService {
	private static final Logger log = LogManager.getLogger(WebParsingService.class);

	private static final String URL_TAG = "a";
	private static final String URL_ATTRIBUTE = "href";
	private static final String PARAMS = ":params";

	private final List<WebResource> webResources;

	private int recordLimit;
	private int recordCounter;


	public WebParsingService(int recordLimit) {
		this.recordLimit = recordLimit;
		webResources = WebParsingConfiguration.getInstance().getWebResourcesList();
	}

	@Override
	public List<RecipeDto> getRecipesFromWebResources(String userRequest) {
		List<RecipeDto> recipes = new ArrayList<>();

		if (CollectionUtils.isEmpty(webResources)) {
			String errorMsg = "Конфигурации веб-ресурса для формирования запроса отсутствуют";
			log.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}

		for (WebResource webResource : webResources) {
			List<Map<String, String>> webResourcesItems = getWebResourcesWithItemsAndUrls(webResource, userRequest);

			if (CollectionUtils.isEmpty(webResourcesItems)) {
				log.info(String.format("По запросу [%s] не найдено ни одного результата", userRequest));
				return recipes;
			}

			int i = 0;
			for (Map<String, String> webResourceItems : webResourcesItems) {
				for (String title : webResourceItems.keySet()) {
					ParsedWebPage parsedWebPage = new ParsedWebPage(title, webResourceItems.get(title), webResource.getItemPage());

					if(parsedWebPage.areMainFieldsNotEmpty()) {
						RecipeDto recipe = new RecipeDto(parsedWebPage);
						recipes.add(recipe);
					}
					i++;
				}
				if (recordCounter == recordLimit) {
					log.info(String.format("Сервис парсинга веб страниц обратал %d ресурсов", i));
					return recipes;
				}
			}
		}

		return recipes;
	}

	private List<Map<String, String>> getWebResourcesWithItemsAndUrls(WebResource webResource, String userRequest) {
		List<Map<String, String>> webResourcesItems = new ArrayList<>();

		String preparedRequestURL = getPreparedRequestURL(userRequest, webResource.getRequestURL());

		try {
			Document doc = Jsoup.connect(preparedRequestURL).get();
			Elements itemElements = doc.getElementsByClass(webResource.getItemClass());

			Document itemDoc = Jsoup.parseBodyFragment(itemElements.html());
			Elements elements = itemDoc.getElementsByTag(URL_TAG);

			webResourcesItems.add(fillWebResourceItemsAndUrls(webResource, elements));
		} catch (IOException e) {
			log.error(e);
		}

		return webResourcesItems;
	}

	private Map<String, String> fillWebResourceItemsAndUrls(WebResource webResource, Elements elements) {
		Map<String, String> itemsAndUrls = new HashMap<>();

		for (Element element : elements) {
			String itemTitle = element.text();

			if (StringUtils.isNotEmpty(itemTitle)) {
				String itemURL = element.attr(URL_ATTRIBUTE);

				if (StringUtils.isNotEmpty(itemURL) && recordCounter < recordLimit) {
					itemsAndUrls.put(itemTitle, webResource.getMainURL() + itemURL);
					recordCounter ++;
				} else {
					break;
				}
			}
		}
		return itemsAndUrls;
	}

	private String getPreparedRequestURL(String userRequest, String requestURL) {
		if (StringUtils.isEmpty(userRequest) || StringUtils.isEmpty(requestURL)) {
			log.error(String.format("Некорректно заданы параметры для формирования веб-запроса: userRequest = %s , requestURL = %s", userRequest, requestURL));
			return StringUtils.EMPTY;
		}

		String preparedUserRequest = QueryParamsParser.getPreparedParamsForWebRequest(userRequest);
		return StringUtils.replace(requestURL, PARAMS, preparedUserRequest);
	}
}
