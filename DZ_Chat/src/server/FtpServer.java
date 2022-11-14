package server;

import dto.chat.ChatInfo;
import message.ftp.FileCommon;
import org.json.JSONObject;
import property.ServerProperties;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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
				System.out.println("FTP server is listening... (Port: " + serverInfo.getPort() + ")");
				try {
					Socket socket = serverSocket.accept();
					JSONObject response = reponseMessage(socket);
					ChatInfo chatInfo = getChatInfo(response);

					String command = chatInfo.getCommand().toLowerCase();
					String fileName = new File(chatInfo.getFilePath()).getName();
					String chatRoomName = chatInfo.getRoomName();

					if (command.startsWith("#filesend")) fileSend(socket, chatInfo);
					else if(command.startsWith("#filezip")) fileZip(socket, chatInfo);
					else new FileCommon().fileSend("resources/room/" + chatRoomName + "/" + fileName, socket);

					System.out.println("서버에서 파일 전송 완료");
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} catch (IOException e) {
					System.out.println("FtpServer > command > "+e);
					throw new RuntimeException(e);
				}
			}
		});
	}

	private static void fileZip(Socket socket, ChatInfo chatInfo) {
		System.out.println("chatInfo.getFilePath():"+ chatInfo.getFilePath());
		new FtpService().createZipfile(chatInfo, socket);
	}

	private static void fileSend(Socket socket, ChatInfo chatInfo) {
		System.out.println("FileTransferReceiver > startServer() > #fileSend > " + chatInfo.getFilePath());
		new FtpService().fileSend(chatInfo, socket);
	}

	private ChatInfo getChatInfo(JSONObject response) {
		String chatInfoStr = response.getString("chatInfo");
		JSONObject chatInfoJSON = new JSONObject(chatInfoStr);
		String command = chatInfoJSON.getString("command");
		String filePath = chatInfoJSON.getString("filePath");
		String roomName = chatInfoJSON.getString("roomName");
		return new ChatInfo(command, filePath, roomName);
	}

	public JSONObject reponseMessage(Socket socket) throws IOException {
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		int length = dis.readInt();
		int pos = 0;
		byte[] recvData = new byte[length];
		do{
			int len = dis.read(recvData, pos, length - pos);
			pos += len;
		}while(length != pos);

		String responseJson = new String(recvData, StandardCharsets.UTF_8);

		return new JSONObject(responseJson);
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
