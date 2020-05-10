package application.utils;


import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class QueryParamsParser {
	private static final char PERCENT_CHAR = '\u0025';
	private static final char PLUS_CHAR = '\u002B';

	public static String getPreparedParamsForDBRequestLike(String query) {
		StringBuilder queryBuilder = new StringBuilder();
		Set<String> queryWords = getQueryWords(query);

		if (!queryWords.isEmpty()) {
			queryBuilder.append(PERCENT_CHAR);

			for (String queryWord : queryWords) {
				queryBuilder.append(queryWord).append(PERCENT_CHAR);
			}
		}
		return queryBuilder.toString();
	}

	public static String getPreparedParamsForWebRequest(String query) {
		StringBuilder queryBuilder = new StringBuilder();
		Set<String> queryWords = getQueryWords(query);

		if (!queryWords.isEmpty()) {
			for (String queryWord : queryWords) {
				queryBuilder.append(queryWord).append(PLUS_CHAR);
			}
		}
		queryBuilder.deleteCharAt(queryBuilder.length()-1);
		return queryBuilder.toString();
	}

	private static Set<String> getQueryWords(String query) {
		Pattern pattern = Pattern.compile("\\w+", Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(query);
		Set<String> queryWords = new HashSet<>();

		while (matcher.find()) {
			queryWords.add(matcher.group().toLowerCase());
		}
		return queryWords;
	}
}
