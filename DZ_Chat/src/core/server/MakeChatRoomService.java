package core.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MakeChatRoomService extends Service {
	public MakeChatRoomService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		super(is, os);
	}
	@Override
	public void request() throws IOException {
		try {
			String chatRoomName = (String) is.readObject();
			if (Server.chatRoomMap.containsKey(chatRoomName)) {
				os.writeObject(Boolean.valueOf(false));
			} else {
				os.writeObject(Boolean.valueOf(true));
			}
		} catch (ClassNotFoundException e) {
		}
	}
}
