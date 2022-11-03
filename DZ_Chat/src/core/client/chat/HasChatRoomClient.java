package core.client.chat;

import java.io.IOException;

import org.json.JSONObject;

import core.client.ObjectStreamClient;
import core.mapper.ServiceResolver;

public class HasChatRoomClient extends ObjectStreamClient {
	private String chatRoomName;
	private boolean hasChatRoom = false;

	public HasChatRoomClient(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}

	public boolean getHasGetRoom() {
		return hasChatRoom;
	}
	
	@Override
	public JSONObject run() {
		try {
			connect(new ServiceResolver("chat.HasChatRoomService"));
			send(chatRoomName);
			JSONObject response = receive();
			unconnect();
			return response;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
