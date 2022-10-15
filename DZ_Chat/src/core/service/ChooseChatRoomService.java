package core.service;

import java.io.*;
import java.net.Socket;

import core.server.MainServer;

public class ChooseChatRoomService extends ObjectStreamService {
	public ChooseChatRoomService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		super(is, os);
		this.os = new ObjectOutputStream(new BufferedOutputStream(os));
		this.is = new ObjectInputStream(new BufferedInputStream(is));
	}

	@Override
	public void request() throws IOException {
		System.out.println("Choose Chat Room");
		String chatRoomName;
		try {
			chatRoomName = (String) is.readObject();
			if (!MainServer.chatRoomMap.containsKey(chatRoomName)) {
				os.writeObject(Boolean.valueOf(false));
				os.flush();
				return;
			} else {
				os.writeObject(Boolean.valueOf(true));
			}
			System.out.println("CHOOSE SUCCESS");
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
