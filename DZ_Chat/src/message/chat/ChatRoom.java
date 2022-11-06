package message.chat;

import java.io.Serializable;
import java.util.*;

import core.server.MainServer;
import core.service.serviceimpl.chat.ChatService;
import log.Log;
import log.LogQueue;
import property.Property;

public class ChatRoom implements Serializable {
	private static final long serialVersionUID = 1823559605769244050L;
	private static final LogQueue logQueue = LogQueue.getInstance();
	private final String chatRoomName;
	private final List<ChatService> chatServices;
	
	public ChatRoom(String chatRoomName) {
		this.chatRoomName = chatRoomName;
		this.chatServices = new ArrayList<>();
	}
	
	public void entrance(ChatService chatService) {
		chatServices.add(chatService);
		logQueue.add(new Log(chatRoomName + "/" + Property.server().get("CHAT_LOG_FILE"), "Entrance:" + chatService.getMe()));
		new SystemMessage(this, chatService.nickname() + "님이 입장하셨습니다. 인원 수: " + size()).push();
	}
	
	public void exit(ChatService chatService) {
		chatServices.remove(chatService);
		logQueue.add(new Log(chatRoomName + "/" + Property.server().get("CHAT_LOG_FILE"), "Exit:" + chatService.getMe()));
		new SystemMessage(this, chatService.nickname() + "님이 퇴장하셨습니다. 남은 인원 수: " + size()).push();
		if (size() == 0) {
			MainServer.chatRoomMap.remove(chatRoomName);
			logQueue.add(new Log(chatRoomName + "/" + Property.server().get("CHAT_LOG_FILE"), "RemoveChatRoom:" + chatRoomName));
		}
	}
	
	public List<ChatService> getChatServices() {
		return chatServices;
	}
	
	public int size() {
		return chatServices.size();
	}
	
	public String getChatRoomName() {
		return chatRoomName;
	}
}
