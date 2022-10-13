package message.chat;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

public abstract class Message {
	private ChatRoom chatRoom;
	private final LocalDateTime time;
	public Message(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
		this.time = LocalDateTime.now();
	}
	public abstract void send(OutputStream os) throws IOException;
}
