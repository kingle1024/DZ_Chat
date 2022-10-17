package core.service.chat;

import java.io.*;
import java.util.Objects;

import core.server.MainServer;
import core.service.ObjectStreamService;
import log.LogQueue;
import member.Member;
import message.chat.ChatRoom;
import message.chat.Message;
import message.chat.SystemMessage;

public class ChatService extends ObjectStreamService  {
	private final Member me;
	private final String chatRoomName;
	private final ChatRoom chatRoom;
//	private NeedLog needLog = new NeedLog();
	private LogQueue logQueue = LogQueue.getInstance();
	public ChatService(ObjectInputStream is, ObjectOutputStream os, Object... args) throws IOException {
		super(is, os);
		this.chatRoomName = (String) args[0];
		this.me = (Member) args[1];
		this.chatRoom = MainServer.chatRoomMap.get(chatRoomName);
	}

	@Override
	public void request() throws IOException {
		System.out.println("Chat Service");
		chatRoom.entrance(this);
		MainServer.threadPool.execute(() -> {
			try {
				while (true) {
					Message message = (Message) is.readObject();
					message.setChatRoom(chatRoom);
					message.push();
					System.out.println("[Server]" + message);
					logQueue.add(message); //LogQueue
				}
			} catch (IOException e) {
				chatRoom.getChatServiceList().remove(this);
				new SystemMessage(chatRoom, me.nickname() + "님이 퇴장하셨습니다. 남은 인원 수: " + chatRoom.size()).push();
				if (chatRoom.size() == 0) {
					MainServer.chatRoomMap.remove(chatRoomName);
				}
			} catch (ClassNotFoundException cfe) {
				cfe.printStackTrace();
			}
		});
	}

	public Member getMe() {
		return me;
	}
	
	public ObjectOutputStream getOs() {
		return os;
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

