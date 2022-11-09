package client.chat;

import java.io.IOException;

import org.json.JSONObject;

import client.Client;
import client.mapper.RequestType;

public class HasChatRoomClient extends Client {
	private String chatRoomName;

	public HasChatRoomClient(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}
	
	@Override
	public JSONObject run() {
		try {
			JSONObject json = new JSONObject();
			json.put("chatRoomName", chatRoomName);
			connect(new RequestType("chat.HasChatRoomService"));
			send(json);
			JSONObject response = receive();
			unconnect();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
