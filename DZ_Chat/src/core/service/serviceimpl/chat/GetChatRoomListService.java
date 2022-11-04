package core.service.serviceimpl.chat;

import java.io.*;
import java.net.Socket;

import org.json.JSONObject;

import core.server.MainServer;
import core.service.Service;

public class GetChatRoomListService extends Service {
	public GetChatRoomListService() throws IOException {
		
	}

	@Override
	public void request() throws IOException {
		System.out.println("Get ChatRoom List");
		JSONObject chatRoomListJSON = new JSONObject();
		chatRoomListJSON.put("size", MainServer.chatRoomMap.size());
		chatRoomListJSON.put("chatRoomList", ""); // TODO
		send(chatRoomListJSON);
	}
}
