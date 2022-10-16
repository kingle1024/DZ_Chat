package core.service;

import java.io.*;
import java.net.Socket;

import core.server.MainServer;
import message.chat.ChatRoom;

public class MakeChatRoomService extends ObjectStreamService {
	public MakeChatRoomService(ObjectInputStream is, ObjectOutputStream os, Object...objects) throws IOException {
		super(is, os);
	}
	@Override
	public void request() throws IOException {
		try {
			String chatRoomName = (String) is.readObject();
			MainServer.chatRoomMap.put(chatRoomName, new ChatRoom(chatRoomName));
		} catch (ClassNotFoundException e) {
		}
	}
}
