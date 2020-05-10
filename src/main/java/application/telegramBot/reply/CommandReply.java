package application.telegramBot.reply;


import lombok.Data;
import org.apache.commons.lang3.StringUtils;


@Data
public class CommandReply extends Reply {
	private String description;


	public CommandReply(String description) {
		this.description = description;
	}

	@Override
	public boolean isEmpty() {
		return StringUtils.isEmpty(description);
	}
}
