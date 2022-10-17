package core.client.chat;

import java.io.*;
import java.util.Scanner;

import core.client.ObjectStreamClient;
import core.mapper.ServiceResolver;

public class ChooseChatRoomClient extends ObjectStreamClient {
	private String chatRoomName;

	@Override
	public void run() {
		try {
			Scanner scanner = new Scanner(System.in);
			chatRoomName = scanner.nextLine();
			connect(new ServiceResolver("chat.ChooseChatRoomService"));
			send(chatRoomName);
			if ((Boolean) receive()) {
				unconnect();
				new ChatClient(chatRoomName).run();
			}			
			unconnect();
		} catch (IOException | ClassNotFoundException e) {
			
		} 
	}

}
