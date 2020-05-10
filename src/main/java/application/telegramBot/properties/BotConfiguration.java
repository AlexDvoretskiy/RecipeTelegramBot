package application.telegramBot.properties;

import lombok.Getter;
import lombok.Setter;


public class BotConfiguration {
	static final String PROXY_KEY = "proxy";
	static final String BOT_KEY = "telegramBot";

	static final String PROXY_HOST_KEY = "host";
	static final String PROXY_PORT_KEY = "port";
	static final String PROXY_USER_KEY = "user";
	static final String PROXY_PASSWORD_KEY = "password";

	static final String BOT_USERNAME = "username";
	static final String BOT_TOKEN = "token";

	static final String HANDLER = "handler";
	static final String RECORD_PARSING_LIMIT = "recordParsingLimit";
	static final String PREFERRED_RECORD_OUTPUT_LIMIT = "preferredRecordOutputLimit";

	static final String THREAD_POOL_SIZE = "threadPoolSize";

	static final String PROJECT_DIRECTORY = "projectDirectory";


	@Getter
	@Setter
	private static String proxyHost;
	@Getter
	@Setter
	private static int proxyPort;
	@Getter
	@Setter
	private static String proxyUser;
	@Getter
	@Setter
	private static String proxyPassword;

	@Getter
	@Setter
	private static String botUsername;
	@Getter
	@Setter
	private static String botToken;

	@Getter
	@Setter
	private static int recordParsingLimit;
	@Getter
	@Setter
	private static int preferredRecordOutputLimit;

	@Getter
	@Setter
	private static int threadPoolSize;

	@Getter
	@Setter
	private static String projectDirectory;
}
