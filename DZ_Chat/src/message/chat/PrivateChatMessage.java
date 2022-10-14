package message.chat;

import java.io.IOException;
import java.io.OutputStream;

import member.Member;

public class PrivateChatMessage extends Message {
	private static final long serialVersionUID = -286765041005171349L;
	
	private final Member sender;
	private final String message;
	private final String chatRoomName;
	private ChatRoom chatRoom;
	public PrivateChatMessage(String chatRoomName, Member sender, String message, String to) {
		this.chatRoomName =chatRoomName;
		this.sender = sender;
		this.message = message;
	}
	@Override
	public void send(OutputStream os) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void push() {
		// TODO Auto-generated method stub
		
	}
}
