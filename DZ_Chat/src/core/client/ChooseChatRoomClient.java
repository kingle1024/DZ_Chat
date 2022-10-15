package core.client;

import java.io.*;
import java.util.Scanner;

import core.mapper.Command;

public class ChooseChatRoomClient extends ObjectStreamClient {
	private String chatRoomName;

	@Override
	public void run() throws IOException {

		try {
			Scanner scanner = new Scanner(System.in);
			chatRoomName = scanner.nextLine();
			connect(new Command("ChooseChatRoomService"));
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
