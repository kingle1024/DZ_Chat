package server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import member.Member;
import message.chat.ChatRoom;
import server.service.ServiceMap;
import server.service.serviceimpl.chat.FileSendService;

public class MainServer implements Server {
	public static final Map<String, ChatRoom> chatRoomMap = Collections.synchronizedMap(new HashMap<>());
	public static final Map<String, Member> memberMap = Collections.synchronizedMap(new TreeMap<>());
	public static final ExecutorService threadPool = Executors.newFixedThreadPool(16);

	private ServerInfo serverInfo;
	private ServerSocket serverSocket;	

	public MainServer(String port) {
		serverInfo = new ServerInfo("Main 서버", port);
	}
	
	@Override
	public void start() throws IOException {
		serverSocket = new ServerSocket(serverInfo.getPort());
		serverInfo.printStartMessage();
		threadPool.execute(() -> {
			try {
				while (true) {
					Socket socket = serverSocket.accept();
					System.out.println("New Socket Accept");
//					ServiceMap.getService("MapperService", socket).request();
					InputStream is = socket.getInputStream();
					OutputStream os = socket.getOutputStream();
					FileSendService fileSendService = new FileSendService(is, os);
					fileSendService.run();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void stop() throws IOException {
		serverSocket.close();
		threadPool.shutdown();
		System.out.println("[서버] 종료");
	}
}