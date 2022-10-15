package core.client;
import java.io.*;

import core.mapper.Command;
import message.chat.Message;

public class GetChatRoomListClient extends ObjectStreamClient {
	public void viewList() throws IOException, ClassNotFoundException {
		Integer num = (Integer) receive();
		for (int i = 1; i <= num; i++) {
			System.out.println(i + ": " + ((String) receive()));
		}
	}
	
	@Override
	public void run() {
		System.out.println("채팅방 목록");
		try {
			connect(new Command("GetChatRoomList"));
			viewList();			
		} catch (IOException e) {
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
