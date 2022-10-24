package core.service.chat;

import java.io.*;
import core.server.MainServer;
import core.service.ObjectStreamService;
import log.Log;
import log.LogQueue;
import log.NeedLog;
import message.chat.ChatRoom;
import property.Property;

public class MakeChatRoomService extends ObjectStreamService implements NeedLog {
	private LogQueue logQueue = LogQueue.getInstance();
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
			logQueue.add(this);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Log toLog() {
		String logMessage = "MakeChatRoom : " + this.chatRoomName;
		return new Log(Property.server().get("CHAT_LOG_FILE"), logMessage);
	}
}
