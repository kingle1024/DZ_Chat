package core.client;

import java.io.*;
import java.util.Scanner;

import core.mapper.Command;
import member.Member;
import message.chat.ChatMessage;
import message.chat.Message;

public class ChatClient extends ObjectStreamClient {
	private Member member;
	private String chatRoomName;
	
	public ChatClient(String chatRoomName) {
		this.chatRoomName = chatRoomName;
		System.out.println("채팅방: " + chatRoomName);
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
		System.out.println("채팅 메세지 수신 준비 완료");
	}

	public void run() {
		try {
			System.out.println("채팅 시작");
			
			// Mock
			member = new Member("id", "pw", "name", "2022-10-14");
			Scanner scanner = new Scanner(System.in);

			connect(new Command("ChatService", chatRoomName, member));
			System.out.println("채팅방 입장");
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
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("채팅 종료");
	}
}
