package core.server;

import java.io.IOException;
import java.util.Scanner;

import log.LogConsumer;
import member.Member;
import member.MemberDao;
import member.MemberMap;
import message.chat.ChatRoom;
import message.ftp.FtpServer;
import property.ServerProperties;

public class Main {
	private static final MemberDao memberDao = MemberDao.getInstance();

	public static void main(String[] args) {
		// Mock ChatRoom
		for (int i = 0; i < 5; i++) {
			MainServer.chatRoomMap.put("TEST ROOM" + i, new ChatRoom("TEST ROOM" + i));
		}
		
		try {
			memberDao.readContent();
			System.out.println("memberMap size: " + MemberMap.size());
			for (Member m : MemberMap.values()) {
				System.out.println(m);
			}
			Server server = new MainServer(Integer.parseInt(ServerProperties.getServerPort()));
			server.start();

			FtpServer ftpServer = new FtpServer(Integer.parseInt(ServerProperties.getFTPPort()));
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

		} catch (IOException e) {
			System.out.println("IOException" + e);
		} catch (Exception e) {
			System.out.println("Exception" + e);
		}
	}

	public static void exit() {
		System.out.println("exit call");
		// log
		memberDao.writeContent();
		System.out.println("SAVE COMMAND");
	}
}