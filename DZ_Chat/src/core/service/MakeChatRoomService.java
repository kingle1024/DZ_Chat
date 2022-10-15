package core.service;

import java.io.*;
import java.net.Socket;

import core.server.MainServer;

public class MakeChatRoomService extends ObjectStreamService {
	public MakeChatRoomService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		super(is, os);
	}
	@Override
	public void request() throws IOException {
		try {
			String chatRoomName = (String) is.readObject();
			if (MainServer.chatRoomMap.containsKey(chatRoomName)) {
				os.writeObject(Boolean.valueOf(false));
			} else {
				os.writeObject(Boolean.valueOf(true));
			}
		} catch (ClassNotFoundException e) {
		}
	}
}
