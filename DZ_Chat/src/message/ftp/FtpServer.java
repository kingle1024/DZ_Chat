package message.ftp;

import core.server.Server;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 받는곳(Server)
public class FtpServer extends Server {
	private static ServerSocket serverSocket;
	public static final ExecutorService threadPool = Executors.newFixedThreadPool(16);

	public FtpServer(int port) throws UnknownHostException {
		super(port);
	}

	@Override
	public void start() throws IOException {
		serverSocket = new ServerSocket(PORT_NUMBER);
		System.out.println("[FTP 서버] 시작 " + HOST + ":" + PORT_NUMBER);
		threadPool.execute(() -> {
			try {
				while (true) {
					Socket socket = serverSocket.accept(); // 새로운 연결 소켓 생성 및 accept대기

					FtpService ftpService = new FtpService();
					System.out.println("FTP server is listening... (Port: " + PORT_NUMBER + ")");

					// 전달한 사용자 정보 표시
					showClientInfo(socket);
					// Client 메시지 확인
					String clientMessage = ftpService.clientMessageReceive(socket);

					// 파일 전송
					if (clientMessage.startsWith("#fileSend")) {
						System.out.println("FileTransferReceiver > startServer() > #fileSend");
						System.out.println("FtpServer가 받은 메시지 : " + clientMessage);
						ftpService.fileSend(clientMessage, socket);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
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

	public static void showClientInfo(Socket socket) {
		InetSocketAddress isaClient = 
				(InetSocketAddress) socket.getRemoteSocketAddress();
		System.out.println(
				"A client(" + isaClient.getAddress().getHostAddress() + 
				" is connected. (Port: " + isaClient.getPort() + ")");
	}
}
