package core.server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import message.chat.ChatRoom;

public class Server {
	public static final Map<String, ChatRoom> chatRoomMap = Collections.synchronizedMap(new HashMap<>()); 
	static final ExecutorService threadPool = Executors.newFixedThreadPool(16);

	private static final int PORT_NUMBER = 50_001;
	private ServerSocket serverSocket;

	public void start() throws IOException {
		serverSocket = new ServerSocket(PORT_NUMBER);
		System.out.println("[서버] 시작");
		threadPool.execute(() -> {
			try {
				while (true) {
					Socket socket = serverSocket.accept();
					System.out.println("Socket Accept");
//					Service service = new EntranceChatRoomService(this, socket, "TEST ROOM");
					Service service = new GetChatRoomListService(this, socket);
					service.request();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public void stop() throws IOException {
		serverSocket.close();
		// TODO close all socket client
		threadPool.shutdown();
		System.out.println("[서버] 종료");
	}

	public static void main(String[] args) {
		// Mock
		for (int i = 0; i < 20; i++) {
			chatRoomMap.put("TEST ROOM" + i, new ChatRoom("TEST ROOM" + i));			
		}

		try {
			Server server = new Server();
			server.start();
			Scanner scanner = new Scanner(System.in);
			while (!"q".equals(scanner.nextLine().toLowerCase()))
				;
			scanner.close();
			server.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
