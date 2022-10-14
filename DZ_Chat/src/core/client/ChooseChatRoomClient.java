package core.client;

import java.io.*;
import java.util.Scanner;

import core.mapper.Command;

public class ChooseChatRoomClient extends Client {
	private String chatRoomName;
	@Override
	public void receive() throws IOException, ClassNotFoundException {
		try {
			Boolean correctChatName = (boolean) is.readObject();
			if (correctChatName) {
				unconnect();
				new ChatClient(chatRoomName).run();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void send(Object obj) throws IOException {
		os.writeObject(new Command("ChooseChatRoomService"));
		os.writeObject((String) obj);
		os.flush();
	}

	@Override
	public void run() {
		try {
			Scanner scanner = new Scanner(System.in);
			chatRoomName = scanner.nextLine();
			connect();
			send(chatRoomName);
			receive();
			unconnect();
		} catch (IOException | ClassNotFoundException e) {
			
		} 
	}

}
