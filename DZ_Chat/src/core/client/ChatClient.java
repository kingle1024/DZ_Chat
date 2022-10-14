package core.client;

import java.io.*;
import java.util.Scanner;

import core.mapper.Command;
import member.Member;
import message.chat.ChatMessage;
import message.chat.Message;

public class ChatClient extends Client {
	private Member member;
	private String chatRoomName;
	
	public ChatClient(String chatRoomName) {
		this.chatRoomName = chatRoomName;
		System.out.println("ChatClient" + chatRoomName);
	}
	
	@Override
	public void send(Object obj) throws IOException {
		os.writeObject(obj);
		os.flush();
	}
	
	@Override
	public void receive() {
		Thread thread = new Thread(() -> {
			try {
				while (true) {
					Message message = (Message) is.readObject();
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
			// Mock
			member = new Member("id", "pw", "name", 10);
			
			Scanner scanner = new Scanner(System.in);
			connect();
			send(new Command("ChatService", chatRoomName, member));
			receive();
			while (scanner.hasNext()) {
				String inputStr = scanner.nextLine();
				if ("q".equals(inputStr.toLowerCase()))
					break;

				Message message = new ChatMessage(this.chatRoomName, member, inputStr);
//				Message message = new FileMessage(chatRoome, me, filePath(inputStr));
				send(message);
			}
			scanner.close();
			unconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("채팅 종료");
	}
}
