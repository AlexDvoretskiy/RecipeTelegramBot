package application.telegramBot.reply;


import lombok.Data;


@Data
public abstract class Reply {
	protected Long chatId;

	public abstract boolean isEmpty();
}

