package message.chat;

import java.io.*;
import java.time.LocalDateTime;

import core.server.MainServer;
import log.Log;
import log.NeedLog;
import property.Property;

public abstract class Message implements Serializable, NeedLog {
	private static final long serialVersionUID = -2580100950897989232L;
	protected ChatRoom chatRoom;
	private final LocalDateTime time;
	protected String message;
	public Message(String message) {
		this.message = message;
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
	
	public Log toLog() {
		return new Log(chatRoom.getRoomName() + "/" + Property.server().get("CHAT_LOG_FILE"), message); //폴더이름
	}
}
