package core.server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

import member.Member;
import message.chat.Message;

public class Server {
	static final SynchronousQueue<Task> taskAlramQue = new SynchronousQueue<>();
	static final Map<Member, Queue<Message>> taskMap = Collections.synchronizedMap(new HashMap<>());
	static final ExecutorService threadPool = Executors.newFixedThreadPool(16);
	static DatagramSocket datagramSocket;

	private static final int PORT_NUMBER = 50_001;
	private ServerSocket serverSocket;

	public void start() throws IOException {
		serverSocket = new ServerSocket(PORT_NUMBER);
		System.out.println("[서버] 시작");
		
		threadPool.execute(() -> {
			try {
				Socket socket = serverSocket.accept();
				SocketClient sc = new SocketClient(this, socket);
				
			} catch (IOException e) { } 
		});
	}
	public void stop() throws IOException {
		serverSocket.close();
		// TODO close all socket client
		threadPool.shutdown();
		System.out.println("[서버] 종료");
	}
	public static void main(String[] args) {
		try {
			datagramSocket = new DatagramSocket();
			Server server = new Server();
			server.start();
			
			Scanner scanner = new Scanner(System.in);
			while (!"q".equals(scanner.nextLine().toLowerCase()));
			scanner.close();
			
			server.stop();
		} catch (IOException e) { }

	}

}
