package client.chat;

import java.io.IOException;

import org.json.JSONObject;

import client.Client;
import dto.Transfer;
import dto.chat.HasChatRoomDto;

public class HasChatRoomClient extends Client {
	private String chatRoomName;

	public HasChatRoomClient(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}
	
	@Override
	public JSONObject run() {
		try {
			connect("chat.HasChatRoomService");
			send(new JSONObject().put("chatRoomName", chatRoomName));
//			send(Transfer.toJSON(new HasChatRoomDto.Request(chatRoomName)));
			JSONObject response = receive();
			unconnect();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
