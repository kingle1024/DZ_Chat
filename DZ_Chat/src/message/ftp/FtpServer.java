package message.ftp;

import core.server.Server;
import property.Property;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 받는곳(Server)
public class FtpServer extends Server {
	private static ServerSocket serverSocket;
	public static final ExecutorService threadPool =
			Executors.newFixedThreadPool(
					Integer.parseInt(Property.server().get("THREAD_POOL")));

	public FtpServer(int port) throws UnknownHostException {
		super(port);
	}

	@Override
	public void start() {
		try {
			serverSocket = new ServerSocket(PORT_NUMBER);
			System.out.println("[FTP 서버] 시작 " + HOST + ":" + PORT_NUMBER);
		} catch (IOException e) {
			System.out.println("FtpServer > start() > Exception");
			throw new RuntimeException(e);
		}

		threadPool.execute(() -> {
			while (true) {
				System.out.println("FTP server is listening... (Port: " + PORT_NUMBER + ")");
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
