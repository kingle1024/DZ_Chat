package core.server;

import java.io.*;
import java.net.Socket;

public class ChooseChatRoomService extends Service {
	public ChooseChatRoomService(ObjectInputStream is, ObjectOutputStream os, Object[] obj) throws IOException {
		super(is, os);
	}

	@Override
	public void request() throws IOException {
		System.out.println("Choose Chat Room");
		String chatRoomName;
		try {
			chatRoomName = (String) is.readObject();
			if (!Server.chatRoomMap.containsKey(chatRoomName)) {
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
