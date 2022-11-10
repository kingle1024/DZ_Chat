package server.service.serviceimpl.chat;

import java.io.IOException;

import org.json.JSONObject;

import server.map.ChatRoomMap;
import server.service.Service;

public class HasChatRoomService extends Service {
	@Override
	public void request() throws IOException {
		System.out.println("HasChatRoomService");
		String chatRoomName = receive().getString("chatRoomName");
		send(new JSONObject().put("result", ChatRoomMap.containsKey(chatRoomName)));
	}
}
