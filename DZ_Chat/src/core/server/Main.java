package core.server;

import java.io.IOException;
import java.util.Scanner;

import message.chat.ChatRoom;
import message.ftp.FtpServer;

public class Main {
	public static void main(String[] args) {
		// Mock ChatRoom
		for (int i = 0; i < 20; i++) {
			MainServer.chatRoomMap.put("TEST ROOM" + i, new ChatRoom("TEST ROOM" + i));			
		}

		try {
			Server server = new MainServer(50_001);
			server.start();

			FtpServer.startServer();
			Scanner scanner = new Scanner(System.in);
			while (!"q".equals(scanner.nextLine().toLowerCase()))
				;
			scanner.close();
			FtpServer.stopServer();
			server.stop();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
