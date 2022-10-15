package core.client;

import java.io.*;
import java.util.*;

import core.mapper.Command;

public class MakeChatRoomClient extends ObjectStreamClient {
	private String chatRoomName;
	public MakeChatRoomClient(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}

	@Override
	public void run() {
		try {
			connect(new Command("MakeChatRoomService"));
			send(chatRoomName);
			unconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
