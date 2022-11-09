package server.service.serviceimpl.chat;

import java.io.*;

import org.json.JSONArray;
import org.json.JSONObject;

import server.MainServer;
import server.service.Service;

public class GetChatRoomListService extends Service {

	@Override
	public void request() throws IOException {
		System.out.println("Get ChatRoom List");
		JSONObject chatRoomListJSON = new JSONObject();
		chatRoomListJSON.put("size", MainServer.chatRoomMap.size());
		chatRoomListJSON.put("chatRoomList", new JSONArray(MainServer.chatRoomMap.keySet()));
		send(chatRoomListJSON);
	}
}
