package core.client;

import java.io.*;
import java.util.*;

import core.mapper.ServiceResolver;

public class MakeChatRoomClient extends ObjectStreamClient {
	private String chatRoomName;
	public MakeChatRoomClient(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}

	@Override
	public void run() {
		try {
			connect(new ServiceResolver("MakeChatRoomService"));
			send(chatRoomName);
			unconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
