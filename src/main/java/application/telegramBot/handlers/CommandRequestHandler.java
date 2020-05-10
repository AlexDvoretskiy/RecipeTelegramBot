package application.telegramBot.handlers;


import application.telegramBot.commands.Command;
import application.telegramBot.reply.CommandReply;
import application.telegramBot.reply.Reply;


public class CommandRequestHandler extends AbstractHandler{
	private static final String START_COMMAND_MESSAGE = "Привет! \n" +
	                                                    "Я - телеграмм бот рецептов \n" +
	                                                    "С удовольствием стану Вашим помощником на кухне! \n" +
	                                                    "Просто напишите мне название блюда или ингредиенты";

	private static final String HELP_COMMAND_MESSAGE = "Чтобы найти рецепт - просто отправьте мне его название " +
	                                                   "или ингедиенты из которых оно состоит";


	public CommandRequestHandler(Command command) {
		super(command);
	}

	@Override
	public Reply handleRequest() {
		switch (command) {
			case START:
				return new CommandReply(START_COMMAND_MESSAGE);
			case HELP:
				return new CommandReply(HELP_COMMAND_MESSAGE);
			default:
				throw new IllegalStateException("Unexpected value: " + command.name());
		}
	}
}
