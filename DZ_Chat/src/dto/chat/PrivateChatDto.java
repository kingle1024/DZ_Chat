package dto.chat;

import dto.Transfer;
import member.Member;

public class PrivateChatDto {
	private String type;
	private String message;
	private String sender;
	private String to;
	
	public PrivateChatDto(String type, String message, Member sender, String to) {
		this.type = type;
		this.message = message;
		this.sender = Transfer.toJSON(sender).toString();
		this.to = to;
	}
}
