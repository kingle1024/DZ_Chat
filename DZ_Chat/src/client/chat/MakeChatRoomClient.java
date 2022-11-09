package client.chat;

import java.io.*;

import org.json.JSONObject;

import client.Client;
import client.mapper.RequestType;

public class MakeChatRoomClient extends Client {
	private String chatRoomName;
	public MakeChatRoomClient(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}

	@Override
	public JSONObject run() {
		try {
			connect(new RequestType("chat.MakeChatRoomService"));
			send(new JSONObject().put("chatRoomName", chatRoomName));
			unconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}