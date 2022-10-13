package message.chat;

import java.io.Serializable;
import java.util.*;

import core.server.ChatService;
import member.Member;

public class ChatRoom implements Serializable {
	private static final long serialVersionUID = 1823559605769244050L;
	private final String roomName;
	private final List<ChatService> chatServiceList;
	
	public ChatRoom(String roomName) {
		this.roomName = roomName;
		this.chatServiceList = new ArrayList<>();
	}
	
	public void entrance(ChatService chatService) {
		chatServiceList.add(chatService);
	}
	
	public List<ChatService> getChatServiceList() {
		return chatServiceList;
	}
	
	public int size() {
		return chatServiceList.size();
	}
	
	public String getRoomName() {
		return roomName;
	}
}
