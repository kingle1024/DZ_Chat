package server;

import property.ServerProperties;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import message.ftp.FtpService;

// 받는곳(Server)
public class FtpServer implements Server {
	private ServerInfo serverInfo;
	private static ServerSocket serverSocket;
	public static final ExecutorService threadPool =
			Executors.newFixedThreadPool(
					Integer.parseInt(ServerProperties.getThreadPool()));

	public FtpServer(String port) {
		serverInfo = new ServerInfo("FTP 서버", port);
	}

	@Override
	public void start() {
		try {
			serverSocket = new ServerSocket(serverInfo.getPort());
			serverInfo.printStartMessage();
		} catch (IOException e) {
			System.out.println("FtpServer > start() > Exception");
			throw new RuntimeException(e);
		}

		threadPool.execute(() -> {
			while (true) {
				System.out.println("FTP server is listening... (Port: " + serverInfo.getPort() + ")");
				command();
			}
		});
	}
	public void command(){ // 이름 변경 및 분리 필요?
		try {
			Socket socket = serverSocket.accept();
			FtpService ftpService = new FtpService();
			String chat = ftpService.clientMessageReceive(socket);
			if (chat.startsWith("#fileSend")) {
				System.out.println("FileTransferReceiver > startServer() > #fileSend > " + chat);
				ftpService.fileSend(chat, socket);
			}
		} catch (IOException e) {
			System.out.println("FtpServer > command > "+e);
			throw new RuntimeException(e);
		}
	}
	@Override
	public void stop() throws IOException {
		try {
			// ServerSocket을 닫고 Port 언바인딩
			serverSocket.close();
			System.out.println("[FTP 서버] 종료");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
