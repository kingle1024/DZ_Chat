package message.chat;

import java.io.*;

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
		ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(os));
//		oos.writeObject(message);
//		oos.flush();
	}

	@Override
	public void push() {
		
	}
}
