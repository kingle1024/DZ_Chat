package message.chat;

import java.io.*;
import java.time.LocalDateTime;

import core.server.MainServer;

public abstract class Message implements Serializable {
	private static final long serialVersionUID = -2580100950897989232L;
	protected ChatRoom chatRoom;
	private final LocalDateTime time;
	public Message() {
		this.time = LocalDateTime.now();
	}
	public abstract void send(ObjectOutputStream os) throws IOException;
	public abstract void push();
	public void setChatRoom(String chatRoomName) {
		if (!MainServer.chatRoomMap.containsKey(chatRoomName)) throw new IllegalArgumentException();
		chatRoom = MainServer.chatRoomMap.get(chatRoomName);
	}
	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}
}
