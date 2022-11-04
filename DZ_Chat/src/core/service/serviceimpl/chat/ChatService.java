package core.service.serviceimpl.chat;

import java.io.*;
import java.util.Objects;

import org.json.JSONObject;

import core.server.MainServer;
import core.service.Service;
import log.LogQueue;
import member.Member;
import message.chat.ChatRoom;
import message.chat.Message;

public class ChatService extends Service {
	private final Member me;
	private final String chatRoomName;
	private final ChatRoom chatRoom;
	private LogQueue logQueue = LogQueue.getInstance();
	public ChatService(Object... args) throws IOException {
		this.chatRoomName = (String) args[0];
		this.me = (Member) args[1];
		this.chatRoom = MainServer.chatRoomMap.get(chatRoomName);
	}

	@Override
	public void request() {
		System.out.println("Chat Service");
		chatRoom.entrance(this);
		MainServer.threadPool.execute(() -> {
			try {
				while (true) {
					JSONObject messageJSON = receive();
					Message message = null;
					message.setChatRoom(chatRoom);
					message.push();
					System.out.println("[Server]" + message);
					logQueue.add(message);
				}
			} catch (Exception e) {
				chatRoom.exit(this);
				System.out.println("ChatService > IOException > "+e);
			}
		});
	}
	
	public Member getMe() {
		return me;
	}
	
	public String nickname() {
		return me.nickname();
	}
	
	
	public boolean equalsUser(String id) {
		return me.getUserId().equals(id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(chatRoomName, me);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatService other = (ChatService) obj;
		return Objects.equals(chatRoomName, other.chatRoomName) && Objects.equals(me, other.me);
	}



}

