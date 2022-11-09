package core.service.serviceimpl.chat;

import java.io.*;
import core.server.MainServer;
import core.service.Service;
import log.Log;
import log.LogQueue;
import message.chat.ChatRoom;
import property.ServerProperties;

public class MakeChatRoomService extends Service {
	private String chatRoomName;
	
	@Override
	public void request() throws IOException {
		this.chatRoomName = receive().getString("chatRoomName");
		MainServer.chatRoomMap.put(chatRoomName, new ChatRoom(chatRoomName));
		LogQueue.add(toLog());
	}

	public Log toLog() {
		String logMessage = "MakeChatRoom " + this.chatRoomName;
		return new Log(ServerProperties.getChatLogFile(), logMessage);
	}
}
