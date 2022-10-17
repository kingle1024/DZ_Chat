package core.service.chat;

import java.io.*;
import java.net.Socket;

import core.server.MainServer;
import core.service.ObjectStreamService;

public class GetChatRoomListService extends ObjectStreamService {
	public GetChatRoomListService(ObjectInputStream is, ObjectOutputStream os, Object... obj) throws IOException {
		super(is, os);
	}

	@Override
	public void request() throws IOException {
		System.out.println("Get ChatRoom List");
		os.writeObject(Integer.valueOf(MainServer.chatRoomMap.size()));
		MainServer.chatRoomMap.keySet().stream().forEach(t -> {
			try {
				os.writeObject(t);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		os.flush();
		os.close();
	}

}
