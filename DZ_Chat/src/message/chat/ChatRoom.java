package message.chat;

import java.util.*;

import log.Log;
import log.LogQueue;
import message.MessageFactory;
import property.ServerProperties;
import server.map.ChatRoomMap;
import server.service.serviceimpl.chat.ChatService;

public class ChatRoom {
	private final String chatRoomName;
	private final List<ChatService> chatServices;
	private final String logPath;

	public ChatRoom(String chatRoomName) {
		this.chatRoomName = chatRoomName;
		this.chatServices = new ArrayList<>();
		this.logPath = chatRoomName + "/" + ServerProperties.getChatLogFile();
	}
		

	public void entrance(ChatService chatService) {
		chatServices.add(chatService);
		LogQueue.add(entranceLog(chatService));
		MessageFactory.createSystemMessage(chatService, entranceMessage(chatService)).push();
	}

	public void exit(ChatService chatService) {
		chatServices.remove(chatService);
		LogQueue.add(exitLog(chatService));
		MessageFactory.createSystemMessage(chatService, exitMessage(chatService)).push();
		if (size() == 0) {
			ChatRoomMap.remove(chatRoomName);
			LogQueue.add(removeChatRoomLog(chatService));
		}
		//닫고
	}

	public String entranceMessage(ChatService chatService) {
		return new StringBuilder(chatService.nickname()).append("님이 입장하셨습니다. 남은 인원 수: ").append(size()).toString();
	}

	public String exitMessage(ChatService chatService) {
		return new StringBuilder(chatService.nickname()).append("님이 퇴장하셨습니다. 남은 인원 수: ").append(size()).toString();
	}

	public Log entranceLog(ChatService chatService) {
		return new Log(logPath, "Entrance " + chatRoomName + " " + chatService.getMe().getUserId());
	}

	public Log exitLog(ChatService chatService) {
		return new Log(logPath, "Exit " + chatRoomName + " " + chatService.getMe().getUserId());
	}

	public Log removeChatRoomLog(ChatService chatService) {
		return new Log(logPath, "RemoveChatRoom " + chatRoomName);
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
