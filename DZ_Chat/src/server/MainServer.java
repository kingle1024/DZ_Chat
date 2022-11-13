package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import server.service.ServiceMap;

public class MainServer implements Server {
	public static final ExecutorService threadPool = Executors.newFixedThreadPool(16);
	private ServerInfo serverInfo;
	private ServerSocket serverSocket;	

	public MainServer(int port) {
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
					ServiceMap.getService("MapperService", socket).request();
				}
			} catch (IOException e) {
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