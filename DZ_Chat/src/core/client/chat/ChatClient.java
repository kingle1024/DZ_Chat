package core.client.chat;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

import core.client.ObjectStreamClient;
import core.mapper.ServiceResolver;
import member.Member;
import message.chat.*;

public class ChatClient extends ObjectStreamClient {
	private Member me;
	private String chatRoomName;
	private boolean sendExit = false;
	ThreadGroup threadGroup;

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
				e.printStackTrace();
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
			sendExit = true;
			unconnect();
		} else if (chat.startsWith("#file")) {
			String[] message = chat.split(" ");
			String fileName = message[1];
			boolean result = fileMessage(chat);

			if(!result){
				return new PrivateChatMessage(this.chatRoomName, me, fileName + " 파일 전송 취소", "privateMan");
			}else{
				return new ChatMessage(this.chatRoomName, me, fileName + " 파일 전송");
			}
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
	public boolean fileMessage(String chat) throws IOException {
		if(threadGroup == null){
			threadGroup = new ThreadGroup(me.getUserId()+chatRoomName);
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("chat", chat);
		map.put("chatRoomName", this.chatRoomName);
		map.put("threadGroup", threadGroup);

		FileMessage fileMessage = new FileMessage();
		return fileMessage.run(map);
	}
	
	public boolean getSendExit() {
		return sendExit;
	}
}
