package core.server;

import java.io.*;
import java.net.Socket;

public class GetChatRoomListService extends Service<ObjectInputStream, ObjectOutputStream> {
	public GetChatRoomListService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		super(is, os);
	}

	@Override
	public void request() throws IOException {
		System.out.println("Get ChatRoom List");
		os.writeObject(Integer.valueOf(Server.chatRoomMap.size()));
		Server.chatRoomMap.keySet().stream().forEach(t -> {
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
