package core.service.serviceimpl.chat;

import java.io.*;
import core.server.MainServer;
import core.service.Service;
import log.Log;
import log.LogQueue;
import log.NeedLog;
import message.chat.ChatRoom;
import property.Property;

public class MakeChatRoomService extends Service implements NeedLog {
	private LogQueue logQueue = LogQueue.getInstance();
	private String chatRoomName;
	
	@Override
	public void request() throws IOException {
		this.chatRoomName = receive().getString("chatRoomName");
		MainServer.chatRoomMap.put(chatRoomName, new ChatRoom(chatRoomName));
		logQueue.add(this);
	}

	@Override
	public Log toLog() {
		String logMessage = "MakeChatRoom : " + this.chatRoomName;
		return new Log(Property.server().get("CHAT_LOG_FILE"), logMessage);
	}
}
