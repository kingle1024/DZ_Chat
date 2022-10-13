package message.chat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Message implements Serializable {
	private static final long serialVersionUID = -2580100950897989232L;
	protected final ChatRoom chatRoom;
	private final LocalDateTime time;
	public Message(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
		this.time = LocalDateTime.now();
	}
	public abstract void send(OutputStream os) throws IOException;
	public abstract void sendAll(OutputStream os) throws IOException;
	public abstract void push();
}
