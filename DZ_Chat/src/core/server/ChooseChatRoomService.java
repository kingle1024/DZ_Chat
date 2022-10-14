package core.server;

import java.io.*;
import java.net.Socket;

public class ChooseChatRoomService extends Service<ObjectInputStream, OutputStream> {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	public ChooseChatRoomService(ObjectInputStream is, ObjectOutputStream os, Object[] obj) throws IOException {
		super(is, os);
		ois = is;
		oos = os;
	}

	@Override
	public void request() throws IOException {
		System.out.println("Choose Chat Room");
		String chatRoomName;
		try {
			chatRoomName = (String) is.readObject();
			if (!Server.chatRoomMap.containsKey(chatRoomName)) {
				oos.writeObject(Boolean.valueOf(false));
				os.flush();
				return;
			} else {
				oos.writeObject(Boolean.valueOf(true));
			}
			System.out.println("CHOOSE SUCCESS");
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
