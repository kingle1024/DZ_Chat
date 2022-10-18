package core.client.chat;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import core.client.ObjectStreamClient;
import core.mapper.ServiceResolver;
import member.Member;
import message.chat.*;

public class ChatClient extends ObjectStreamClient {
	private Member me;
	private String chatRoomName;
	private boolean sendExit = false;
	private boolean isConnected = false;
	private static final Object monitor = new Object();
	private ConcurrentLinkedQueue<String> ccque = new ConcurrentLinkedQueue<>();
	ThreadGroup threadGroup;

	public ChatClient(String chatRoomName, Member me) {
		this.chatRoomName = chatRoomName;
		this.me = me;
		System.out.println("채팅방: " + chatRoomName);
	}

	public void listening() {
		Thread thread = new Thread(() -> {
			while (true) {
				String message;
				try {
					message = (String) is.readObject();
					System.out.println(message);
				} catch (ClassNotFoundException | IOException e) {
				}
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
			if(message.length == 1){
				message = new String[3];
				message[1] = "temp";
				message[2] = "temp2";
			}
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
//		Thread messageConsumer = new Thread(() -> {
//			while (true) {
//				if (ccque.isEmpty())
//					try {
//						synchronized (monitor) {
//							monitor.wait();	
//						}
//					} catch (InterruptedException e1) {
//					}
//				System.out.println("ccque.size()" + ccque.size());
//				synchronized (monitor) {
//					while (!ccque.isEmpty() && isConnected) {
//						try {
//							send(chatTypeResolve(ccque.poll()));
//							System.out.println("ccque 뻄");
//						} catch (IOException e) {
//						}
//					}
//					monitor.notify();
//				}
//			}
//		});
//		messageConsumer.start();
		System.out.println("채팅 시작");
		Scanner scanner = new Scanner(System.in);
		while (true) {
			try {
				System.out.println("채팅방 입장");
				while (true) {
					connect(new ServiceResolver("chat.ChatService", chatRoomName, me));
					isConnected = true;
					listening();
					while (scanner.hasNext()) {
						String chat = scanner.nextLine();
//						synchronized (monitor) {
//							ccque.add(chat);
//							monitor.notify();
//						}
						send(chatTypeResolve(chat));
					}
				}
			} catch (IOException e) {
				if (sendExit) {
					System.out.println("채팅 종료");
					return;
				} else {
					System.out.println("서버와 재접속");
					isConnected = false;
				}
			}
		}
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