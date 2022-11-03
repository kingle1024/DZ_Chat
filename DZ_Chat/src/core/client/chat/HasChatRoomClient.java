package core.client.chat;

import java.io.IOException;

import org.json.JSONObject;

import core.client.Client;
import core.client.mapper.RequestType;

public class HasChatRoomClient extends Client {
	private String chatRoomName;

	public HasChatRoomClient(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}
	
	@Override
	public JSONObject run() {
		try {
			connect(new RequestType("chat.HasChatRoomService"));
			send("chatRoomName", chatRoomName);
			JSONObject response = receive();
			unconnect();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
