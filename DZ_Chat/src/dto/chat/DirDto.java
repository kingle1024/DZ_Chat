package dto.chat;

import dto.Transfer;
import member.Member;

public class DirDto {
	private String type;
	private String chat;
	private String chatRoomName;
	private String sender;
	
	public DirDto(String type, String chat, String chatRoomName, Member sender) {
		this.type = type;
		this.chat = chat;
		this.chatRoomName = chatRoomName;
		this.sender = Transfer.toJSON(sender).toString();
	}
}
