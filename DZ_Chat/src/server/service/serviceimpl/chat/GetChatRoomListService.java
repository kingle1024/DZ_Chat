package server.service.serviceimpl.chat;

import java.io.*;

import org.json.JSONArray;
import org.json.JSONObject;

import server.map.ChatRoomMap;
import server.service.Service;

public class GetChatRoomListService extends Service {

	@Override
	public void request() throws IOException {
		System.out.println("Get ChatRoom List");
		JSONObject chatRoomListJSON = new JSONObject();
		chatRoomListJSON.put("size", ChatRoomMap.size());
		chatRoomListJSON.put("chatRoomList", new JSONArray(ChatRoomMap.keySet()));
		send(chatRoomListJSON);
	}
}
