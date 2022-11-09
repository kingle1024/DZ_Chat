package server.service.serviceimpl.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.json.JSONObject;

import server.MainServer;
import server.service.Service;

public class HasChatRoomService extends Service {
	@Override
	public void request() throws IOException {
		System.out.println("HasChatRoomService");
		String chatRoomName = receive().getString("chatRoomName");
		send(new JSONObject().put("result", MainServer.chatRoomMap.containsKey(chatRoomName)));
	}
}
