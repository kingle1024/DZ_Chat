package core.server;

import java.io.*;
import java.net.Socket;

import member.Member;
import message.chat.ChatRoom;
import message.chat.Message;

public class ChatService extends Service {
	private final Member me;
	private final ChatRoom chatRoom;
	public ChatService(ObjectInputStream is, ObjectOutputStream os, Object... args) throws IOException {
		super(is, os);
		String chatRoomName = (String) args[0];
		this.chatRoom = Server.chatRoomMap.get(chatRoomName);
		this.me = (Member) args[1];
		System.out.println("ChatService: " + chatRoom);
	}

	@Override
	public void request() {
		System.out.println("Chat Service");
		chatRoom.entrance(this);
		Server.threadPool.execute(() -> {
			try {
				while (true) {
					Message message = (Message) is.readObject();
					message.setChatRoom(chatRoom);
					message.push();
					System.out.println("[Server]" + message);
				}
			} catch (IOException e) {
				e.printStackTrace();
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
	
	public boolean equalsUser(String name) {
		return me.getName().equals(name);
	}
}
