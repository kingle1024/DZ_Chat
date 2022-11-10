package dto;

import member.Member;

public class ChatDto {
	private String type;
	private String message;
	private String sender;
	
	public ChatDto(String type, String message, Member sender) {
		this.type = type;
		this.message = message;
		this.sender = Transfer.toJSON(sender).toString();
	}
}
