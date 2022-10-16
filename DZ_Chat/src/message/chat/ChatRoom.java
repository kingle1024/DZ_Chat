package message.chat;

import java.io.Serializable;
import java.util.*;

import core.service.ChatService;
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
		new SystemMessage(this, chatService.getMe() + "님이 입장하셨습니다. 인원 수: " + size()).push();
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
