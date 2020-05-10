package application.webParsing.parsingUtils;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringParserUtils {
	private static final char FRACTION_ONE_QUARTER = '\u00BC';
	private static final char FRACTION_ONE_HALF = '\u00BD';
	private static final char FRACTION_THREE_QUARTERS = '\u00BE';
	private static final String QUOTATION_MARK = "&quot;";


	public static Set<String> getJsonString(String string) {
		Set<String> resultSet = new HashSet<>();
		string = string.replace(QUOTATION_MARK, "\"");
		string = replaceFractionChars(string);

		Pattern pattern = Pattern.compile("\\{.+}", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(string);

		while (matcher.find()) {
			resultSet.add(matcher.group());
		}
		return resultSet;
	}

	public static String replaceFractionChars(String string) {
		return string.replace(String.valueOf(FRACTION_ONE_QUARTER), "0,25")
					 .replace(String.valueOf(FRACTION_ONE_HALF), "0,5")
					 .replace(String.valueOf(FRACTION_THREE_QUARTERS), "0,75");
	}

	public static String deleteNumerationIfExist(String string) {
		final String beginningNumber = "^\\d+\\.+\\s";
		return string.replaceFirst(beginningNumber, "");
	}

	public static String deleteLastWord(String string) {
		return string.substring(0, string.lastIndexOf(" "));
	}

	public static String generateImageName(String formatName) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		sb.append("img")
		  .append(random.nextInt(9) + 1);

		for (int i = 0; i < 11; i++) {
			sb.append(random.nextInt(10));
		}
		sb.append(".").append(formatName);
		return sb.toString();
	}

	public static String getImageFormatNameFromURL(String url) {
		String result = null;
		Pattern pattern = Pattern.compile("\\w+$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(url);

		if (matcher.find()) {
			result =  matcher.group();
		}
		return result;
	}
}
