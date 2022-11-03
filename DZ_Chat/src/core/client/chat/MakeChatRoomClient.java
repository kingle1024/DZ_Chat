package core.client.chat;

import java.io.*;
import java.util.*;

import org.json.JSONObject;

import core.client.ObjectStreamClient;
import core.mapper.ServiceResolver;

public class MakeChatRoomClient extends ObjectStreamClient {
	private String chatRoomName;
	public MakeChatRoomClient(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}

	@Override
	public JSONObject run() {
		try {
			connect(new ServiceResolver("chat.MakeChatRoomService"));
			send(chatRoomName);
			unconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
