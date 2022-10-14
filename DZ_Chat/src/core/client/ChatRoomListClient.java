package core.client;
import java.util.*;
import java.io.*;

import message.chat.ChatRoom;
import message.chat.Message;

public class ChatRoomListClient extends Client {

	@Override
	public void receive() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(is);
		Integer num = (Integer) ois.readObject();
		for (int i = 1; i <= num; i++) {
			System.out.println(i + ": " + ((String) ois.readObject()));
		}
	}

	@Override
	public void send(Message message) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		System.out.println("채팅방 목록");
		Client client = new ChatRoomListClient();
		try {
			client.connect();
			client.receive();	
			
		} catch (IOException e) {
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
