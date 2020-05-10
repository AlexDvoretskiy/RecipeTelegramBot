package application.telegramBot.handlers;

import application.telegramBot.commands.Command;
import application.telegramBot.reply.Reply;
import lombok.Data;


@Data
public abstract class AbstractHandler {
	protected Command command;

	public AbstractHandler(Command command) {
		this.command = command;
	}

	public abstract Reply handleRequest();
}
