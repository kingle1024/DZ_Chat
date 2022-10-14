package message.chat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;

import core.server.Server;

public abstract class Message implements Serializable {
	private static final long serialVersionUID = -2580100950897989232L;
	protected ChatRoom chatRoom;
	private final LocalDateTime time;
	public Message() {
		this.time = LocalDateTime.now();
	}
	public abstract void send(OutputStream os) throws IOException;
	public abstract void push();
	public void setChatRoom(String chatRoomName) {
		if (!Server.chatRoomMap.containsKey(chatRoomName)) throw new IllegalArgumentException();
		chatRoom = Server.chatRoomMap.get(chatRoomName);
	}
}
