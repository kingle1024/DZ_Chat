package server;

import property.ClientProperties;
import property.ServerProperties;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import message.ftp.FtpService;

// 받는곳(Server)
public class FtpServer implements Server {
	final private ServerInfo serverInfo;
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
			System.out.println("chat:"+chat);
			String[] message = chat.split(" ");
			File commandFileAndPath = new File(message[1]);
			String fileName = commandFileAndPath.getName();
			String roomName = message[2];

			if (chat.startsWith("#fileSend")) {
				System.out.println("FileTransferReceiver > startServer() > #fileSend > " + chat);
				ftpService.fileSend(chat, socket);
			}else {
				File originFileTarget = new File("resources/"+roomName+"/"+fileName);
				System.out.println("origin:"+originFileTarget.getAbsolutePath());
				InputStream fis = new FileInputStream(originFileTarget);

				byte[] buffer = new byte[Integer.parseInt(ClientProperties.getDefaultBufferSize())];

				// 앞으로 저장할 파일
				OutputStream os = socket.getOutputStream();
				int readBytes;
				long totalReadBytes = 0;
				int cnt = 0;
				int loopTime = 2;
				long fileSize = originFileTarget.length();

				while ((readBytes = fis.read(buffer)) > 0) {
					os.write(buffer, 0, readBytes); // 실질적으로 보내는 부분
					totalReadBytes += readBytes;
					if (cnt % loopTime == 0) {
						System.out.println("sendFile In progress: " + totalReadBytes + "/" + fileSize + " Byte(s) ("
								+ (totalReadBytes * 100 / fileSize) + " %)");
					}
					cnt++;
				}
				System.out.println("서버에서 파일 전송 완료");
				os.close();
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
