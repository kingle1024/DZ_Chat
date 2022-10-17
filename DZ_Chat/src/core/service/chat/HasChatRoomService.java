package core.service.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import core.server.MainServer;
import core.service.ObjectStreamService;

public class HasChatRoomService extends ObjectStreamService {

	public HasChatRoomService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		super(is, os);
	}

	@Override
	public void request() throws IOException {
		try {
			String chatRoomName = (String) is.readObject();	
			if (MainServer.chatRoomMap.containsKey(chatRoomName)) {
				os.writeObject(Boolean.valueOf(true));
			} else {
				os.writeObject(Boolean.valueOf(false));
			}
		} catch (ClassNotFoundException e) {
			
		}		
	}

}
