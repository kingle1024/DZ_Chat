package message.chat;

import java.time.LocalDateTime;

import org.json.JSONObject;

import core.server.MainServer;
import core.service.serviceimpl.chat.ChatService;
import log.Log;
import log.NeedLog;
import property.Property;

public abstract class Message implements NeedLog {
	protected ChatService chatService;
	protected ChatRoom chatRoom;
	private final LocalDateTime time;
	protected String message;
	private JSONObject json = new JSONObject();
	
	public Message(String message) {
		this.message = message;
		this.time = LocalDateTime.now();
	}

	public void setChatRoom(String chatRoomName) {
		if (!MainServer.chatRoomMap.containsKey(chatRoomName))
			throw new IllegalArgumentException();
		chatRoom = MainServer.chatRoomMap.get(chatRoomName);
	}

	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}

	public Log toLog() {
		return new Log(chatRoom.getChatRoomName() + "/" + Property.server().get("CHAT_LOG_FILE"), message); // 폴더이름
	}
	
	public abstract void push();
	
	public JSONObject getJSON() {
		json.put("message", message);
		return json;
	}
}
