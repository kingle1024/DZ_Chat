package server.service.serviceimpl.chat;

import java.io.*;

import log.Log;
import log.LogQueue;
import message.chat.ChatRoom;
import property.ServerProperties;
import server.map.ChatRoomMap;
import server.service.Service;

public class MakeChatRoomService extends Service {
	private String chatRoomName;
	
	@Override
	public void request() throws IOException {
		this.chatRoomName = receive().getString("chatRoomName");
		ChatRoomMap.put(chatRoomName, new ChatRoom(chatRoomName));
		LogQueue.add(toLog());
	}

	public Log toLog() {
		String logMessage = "MakeChatRoom : " + this.chatRoomName;
		return new Log(ServerProperties.getChatLogFile(), logMessage);
	}
}
