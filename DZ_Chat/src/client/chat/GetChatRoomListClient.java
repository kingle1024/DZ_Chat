package client.chat;
import java.io.*;

import org.json.JSONObject;

import client.Client;
import client.mapper.RequestType;

public class GetChatRoomListClient extends Client {

	
	@Override
	public JSONObject run() {
		System.out.println("채팅방 목록");
		try {
			connect(new RequestType("chat.GetChatRoomListService"));
			JSONObject chatRoomList = receive();
			unconnect();
			return chatRoomList;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}