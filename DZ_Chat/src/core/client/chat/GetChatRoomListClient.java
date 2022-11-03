package core.client.chat;
import java.io.*;

import org.json.JSONObject;

import core.client.Client;
import core.client.mapper.RequestType;

public class GetChatRoomListClient extends Client {
//	public void viewList() throws IOException, ClassNotFoundException {
//		JSONObject chatRoomList = receive();
////		for (int i = 1; i <= num; i++) {
////			System.out.println(i + ": ");
////		}
//	}
	
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
