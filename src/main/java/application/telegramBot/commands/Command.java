package application.telegramBot.commands;


import lombok.Getter;
import lombok.Setter;


public enum Command {
	REQUEST(""),
	START("/start"),
	HELP("/help");

	@Getter
	@Setter
	private String description;

	Command(String description) {
		this.description = description;
	}
}
