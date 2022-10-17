package core.service.chat;

import java.io.*;
import core.server.MainServer;
import core.service.ObjectStreamService;
import log.Log;
import log.NeedLog;
import message.chat.ChatRoom;

public class MakeChatRoomService extends ObjectStreamService implements NeedLog {
	public MakeChatRoomService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		super(is, os);
	}
	private String chatRoomName;
	@Override
	public void request() throws IOException {
		try {
			String chatRoomName = (String) is.readObject();
			this.chatRoomName = chatRoomName;
			MainServer.chatRoomMap.put(chatRoomName, new ChatRoom(chatRoomName));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Log toLog() {
		String logMessage = "MakeChatRoom : " + this.chatRoomName;
		return new Log("chatlog.txt", logMessage);
	}
}
