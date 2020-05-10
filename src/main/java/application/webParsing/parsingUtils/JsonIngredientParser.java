package application.webParsing.parsingUtils;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JsonIngredientParser {
	private static final Logger log = LogManager.getLogger(JsonIngredientParser.class);

	private String name;
	private String amount;

	public JsonIngredientParser(String json) {
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);

			name = (String) jsonObject.get("name");
			amount = (String) jsonObject.get("amount");
		} catch (ParseException e) {
			log.error(String.format("Ошибка при парсинге строки [%s]", json), e);
		}
	}

	public boolean isNotEmpty() {
		return StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(amount);
	}

	public String getName() {
		return name;
	}

	public String getAmount() {
		return amount;
	}
}
