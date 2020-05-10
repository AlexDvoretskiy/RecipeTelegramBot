package application.telegramBot.commands;


public class CommandParser {

	public static Command parseRequest(String request) {
		if (request.trim().equals(Command.START.getDescription())) {
			return Command.START;
		} else if (request.trim().equals(Command.HELP.getDescription())) {
			return Command.HELP;
		} else {
			Command.REQUEST.setDescription(request);
			return Command.REQUEST;
		}
	}
}
