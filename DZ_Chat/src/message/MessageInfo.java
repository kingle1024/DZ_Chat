package message;

import java.time.LocalDateTime;

import org.json.JSONObject;

import log.Log;
import property.ServerProperties;
import server.service.serviceimpl.chat.ChatService;

public class MessageInfo {
	private ChatService chatService;
	private final LocalDateTime time;
	private String message;
	private JSONObject json = new JSONObject();
	
	public MessageInfo(String message) {
		this.message = message;
		this.time = LocalDateTime.now();
	}
	
	public void setChatService(ChatService chatService) {
		this.chatService = chatService;
	}
	
//	public Log toLog() {
//		return new Log(chatRoom.getChatRoomName() + "/" + Property.server().get("CHAT_LOG_FILE"), message); // 폴더이름
//	}
}
