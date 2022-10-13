package message.chat;

import java.time.LocalDateTime;

import member.Member;

public class ChatMessage extends Message {
	private ChatRoom chatRoom;
	private Member sender;
	private String message;
	private LocalDateTime time;
	
	public ChatMessage(Member sender, String message) {
		this.sender = sender;
		this.message = message;
	}
	
	@Override
	public void send() {
		// TODO Auto-generated method stub
		
	}

}
