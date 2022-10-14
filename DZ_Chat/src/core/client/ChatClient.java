package core.client;

import java.io.*;
import java.util.Scanner;

import member.Member;
import message.chat.ChatMessage;
import message.chat.Message;

public class ChatClient extends Client {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Member member;

	public void send(Message message) throws IOException {
		oos = new ObjectOutputStream(os);
		oos.writeObject(message);
		oos.flush();
	}
	
	@Override
	public void receive() {
		Thread thread = new Thread(() -> {
			try {
				while (true) {
					ois = new ObjectInputStream(is);
					Message message = (Message) ois.readObject();
					System.out.println(message);
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
		thread.start();
	}

	public void run() {
		System.out.println("채팅 시작");
		try {
			Client client = new ChatClient();
			
			// Mock
			member = new Member("id", "pw", "name", 10);
			String chatRoomName = "TEST ROOM";
			
			Scanner scanner = new Scanner(System.in);
			client.connect();
			client.receive();
			while (true) {
				String inputStr = scanner.nextLine();
				if ("q".equals(inputStr.toLowerCase()))
					break;

				// TODO Message send
				Message message = new ChatMessage(chatRoomName, member, inputStr);
//				Message message = new FileMessage(chatRoome, me, filePath(inputStr));
				client.send(message);
			}
			scanner.close();
			client.unconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("채팅 종료");
	}
}
