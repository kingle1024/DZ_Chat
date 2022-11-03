package core.client.chat;
import java.io.*;

import org.json.JSONObject;

import core.client.ObjectStreamClient;
import core.mapper.ServiceResolver;

public class GetChatRoomListClient extends ObjectStreamClient {
	public void viewList() throws IOException, ClassNotFoundException {
		JSONObject chatRoomList = receive();
//		for (int i = 1; i <= num; i++) {
//			System.out.println(i + ": ");
//		}
	}
	
	@Override
	public JSONObject run() {
		System.out.println("채팅방 목록");
		try {
			connect(new ServiceResolver("chat.GetChatRoomListService"));
			viewList();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
