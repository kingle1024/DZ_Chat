package client.chat;

import java.io.*;

import org.json.JSONObject;

import client.Client;

public class GetChatRoomListClient extends Client {

	@Override
	public JSONObject run() {
		System.out.println("채팅방 목록");
		try {
			connect("chat.GetChatRoomListService");
			JSONObject chatRoomList = receive();
			unconnect();
			return chatRoomList;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
