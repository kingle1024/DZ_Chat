package message.chat;

import java.time.LocalDateTime;

import member.Member;

public class ChatMessage extends Message {
	private final Member sender;
	private final String message;
	
	public ChatMessage(ChatRoom chatRoom, Member sender, String message) {
		super(chatRoom);
		this.sender = sender;
		this.message = message;
	}
	
	@Override
	public void send() {
		// TODO Auto-generated method stub
		
	}

}
