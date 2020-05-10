package application.webParsing.parsingConfiguration;

import application.telegramBot.properties.BotConfiguration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;


public class WebParsingConfiguration {
	private static final String CONFIG_FILE_PATH = BotConfiguration.getProjectDirectory() + "config/webResourceConfig.xml";
	private static final String ROOT_TAG = "webResource";

	private static final Logger log = LogManager.getLogger(WebParsingConfiguration.class);

	private static WebParsingConfiguration instance;

	private final List<WebResource> prefilledWebResourceList;


	private WebParsingConfiguration() {
		prefilledWebResourceList = getWebResourcesFromConfig();
	}

	public static WebParsingConfiguration getInstance() {
		WebParsingConfiguration localInstance = instance;
		if (localInstance == null) {
			synchronized (WebParsingConfiguration.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new WebParsingConfiguration();
				}
			}
		}
		return localInstance;
	}

	private List<WebResource> getWebResourcesFromConfig() {
		List<WebResource> webResources = new ArrayList<>();

		try {
			InputStream inputStream = new FileInputStream(new File(CONFIG_FILE_PATH));

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(inputStream);

			Element rootElement = document.getRootElement();
			List<Element> list = rootElement.getChildren(ROOT_TAG);

			for (Element element : list) {
				webResources.add(fillWebResource(element));
			}
		} catch (Exception e) {
			log.error(String.format("Ошибка получения конфигураций из файла %s", CONFIG_FILE_PATH), e);
		}

		return webResources;
	}

	private WebResource fillWebResource(Element node) {
		WebResource webResource = new WebResource();
		Element nestedNode = node.getChild("itemPage");

		if (nestedNode != null) {
			webResource.getItemPage().setTitleClass(nestedNode.getChildText("titleClass"));
			webResource.getItemPage().setDescriptionClass(nestedNode.getChildText("descriptionClass"));
			webResource.getItemPage().setPortionAmountClass(nestedNode.getChildText("portionClass"));
			webResource.getItemPage().setCookingTimeClass(nestedNode.getChildText("cookingTimeClass"));
			webResource.getItemPage().setIngredientsClass(nestedNode.getChildText("ingredientsClass"));
			webResource.getItemPage().setInstructionsClass(nestedNode.getChildText("instructionsClass"));
			webResource.getItemPage().setImageClass(nestedNode.getChildText("imageClass"));
			webResource.getItemPage().setAdviceClass(nestedNode.getChildText("adviceClass"));
		}

		webResource.setMainURL(node.getChildText("mainURL"));
		webResource.setRequestURL(node.getChildText("requestURL"));
		webResource.setItemClass(node.getChildText("itemClass"));

		return webResource;
	}

	public List<WebResource> getWebResourcesList() {
		return new ArrayList<>(prefilledWebResourceList);
	}
}
