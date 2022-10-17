package core.client.chat;

import java.io.*;
import java.util.Scanner;

import core.client.ObjectStreamClient;
import core.mapper.ServiceResolver;
import member.Member;
import message.chat.*;
import message.ftp.FtpClient;

public class ChatClient extends ObjectStreamClient {
	private Member me;
	private String chatRoomName;

	public ChatClient(String chatRoomName, Member me) {
		this.chatRoomName = chatRoomName;
		this.me = me;
		System.out.println("채팅방: " + chatRoomName);
	}

	public void listening() {
		Thread thread = new Thread(() -> {
			try {
				while (true) {
					String message = (String) is.readObject();
					System.out.println(message);
				}
			} catch (IOException | ClassNotFoundException e) {

			}
		});
		thread.start();
		System.out.println("채팅 메세지 수신 준비 완료");
	}

	private Message chatTypeResolve(String chat) throws IOException {
		if (chat.startsWith("@")) {
			int idx = chat.indexOf(' ');
			String to = chat.substring(1, idx);
			String msg = chat.substring(idx + 1, chat.length());
			return new PrivateChatMessage(chatRoomName, me, msg, to);
		} else if (chat.startsWith("#exit")) {
			unconnect();
		} else if (chat.startsWith("#file")) {
			Thread thread = new Thread() {
				@Override
				public void run() {
					FtpClient ftpClient = new FtpClient();
					ftpClient.run(chat, chatRoomName);
				}
			};
			thread.start();
			String message[] = chat.split(" ");
			return new ChatMessage(this.chatRoomName, me, message[1] + " 파일이 전송되었습니다.");
		} else if (chat.startsWith("#dir")) {
			return new DirMessage(this.chatRoomName, me, chat);
		} else {
			return new ChatMessage(this.chatRoomName, me, chat);
		}
		return null;
	}

	public void run() {
		try {
			System.out.println("채팅 시작");

			// Mock
			Scanner scanner = new Scanner(System.in);

			connect(new ServiceResolver("chat.ChatService", chatRoomName, me));
			System.out.println("채팅방 입장");
			listening();
			while (scanner.hasNext()) {
				String chat = scanner.nextLine();
				if ("q".equals(chat.toLowerCase()))
					break;
				Message message = chatTypeResolve(chat);
				send(message);
			}
			unconnect();

		} catch (IOException e) {

		}
		System.out.println("채팅 종료");
	}
}
