package server;

import message.ftp.FileCommon;
import org.json.JSONObject;

import dto.chat.ChatInfo;
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
					ServerProperties.getThreadPool());

	public FtpServer(int port) {
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
				try {
					System.out.println("FTP server is listening... (Port: " + serverInfo.getPort() + ")");
					command();
				} catch (IOException e) {
					break;
				}
			}
		});
	}
	public void command() throws IOException { // 이름 변경 및 분리 필요?	
		Socket socket = serverSocket.accept();
		FtpService ftpService = new FtpService();
		JSONObject response = ftpService.reponseMessage(socket);
		ChatInfo chatInfo = getChatInfo(response);

		String command = chatInfo.getCommand();
		String fileName = new File(chatInfo.getFilePath()).getName();
		String chatRoomName = chatInfo.getRoomName();

		JSONObject request = new JSONObject();
		request.put("fileName", fileName);
		request.put("chatRoomName", chatRoomName);

		if (command.startsWith("#fileSend")) {
			System.out.println("FileTransferReceiver > startServer() > #fileSend > " + fileName);
			ftpService.fileSend(request, socket);
		} else {
			try {
				new FileCommon().fileSave("resources/" + chatRoomName + "/" + fileName, socket);
				System.out.println("서버에서 파일 전송 완료");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private ChatInfo getChatInfo(JSONObject response) {
		String chatInfoStr = response.getString("chatInfo");
		JSONObject chatInfo = new JSONObject(chatInfoStr);
		String command = chatInfo.getString("command");
		String filePath = chatInfo.getString("filePath");
		String roomName = chatInfo.getString("roomName");
		return new ChatInfo(command, filePath, roomName);
	}

	@Override
	public void stop() {
		try {
			// ServerSocket을 닫고 Port 언바인딩
			serverSocket.close();
			System.out.println("[FTP 서버] 종료");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
