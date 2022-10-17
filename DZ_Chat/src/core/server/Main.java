package core.server;

import java.io.IOException;
import java.util.Scanner;

import message.chat.ChatRoom;
import message.ftp.FtpServer;

public class Main {
	public static void main(String[] args) {
		// Mock ChatRoom
		for (int i = 0; i < 10; i++) {
			MainServer.chatRoomMap.put("TEST ROOM" + i, new ChatRoom("TEST ROOM" + i));			
		}

		Scanner scanner = new Scanner(System.in);
		try {
			Server server = new MainServer(50_001);
			server.start();
			FtpServer.startServer();
			
			while (!"q".equals(scanner.nextLine().toLowerCase()))
				;
			FtpServer.stopServer();
			server.stop();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			
		} catch (Exception e) {
			
		}
		scanner.close();
	}
}
