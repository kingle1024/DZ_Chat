package server;

import java.io.IOException;
import java.util.Scanner;

import log.LogConsumer;
import member.MemberDao;
import message.chat.ChatRoom;
import property.ServerProperties;
import server.map.ChatRoomMap;

public class Main {
	public static void main(String[] args) {
		try {
			MemberDao.readContent();
			ChatRoomMap.init();
			Server server = new MainServer(ServerProperties.getServerPort());
			server.start();

			Server ftpServer = new FtpServer(ServerProperties.getFTPPort());
			ftpServer.start();
			
			Thread logConsumer = new Thread(new LogConsumer());
			logConsumer.setDaemon(true);
			logConsumer.start();
			
			Scanner scanner = new Scanner(System.in);
			while (true) {
				System.out.println("Wait Scanner read");
				String input = scanner.next();
				System.out.println("INPUT: " + input);
				if ("q".equals(input)) {
					exit();
					break;
				}
			}

			ftpServer.stop();
			server.stop();
			scanner.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void exit() {
		System.out.println("exit call");
		// TODO: LogQueue 남아 있는 것 모두 해소
		MemberDao.writeContent();
		ChatRoomMap.record();
		System.out.println("SAVE COMMAND");
	}
}