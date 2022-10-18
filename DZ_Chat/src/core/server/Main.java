package core.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import member.Member;
import member.MemberDao;
import member.MemberMap;
import message.chat.ChatRoom;
import message.ftp.FtpServer;

public class Main {
	private static final MemberMap memberMap = MemberMap.getInstance();
	private static final MemberDao memberDao = MemberDao.getInstance();

	public static void main(String[] args) {
//		System.out.println("change");
		// Mock ChatRoom
		for (int i = 0; i < 10; i++) {
			MainServer.chatRoomMap.put("TEST ROOM" + i, new ChatRoom("TEST ROOM" + i));
		}
		try {
			memberDao.readContent();
			System.out.println("server.Main" + memberMap.size());
			for (Member m : memberMap.values()) {
				System.out.println(m);
			}
			Server server = new MainServer(50_001);
			server.start();
//         FtpServer.startServer();

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

//         FtpServer.stopServer();
//			server.stop();

		} catch (IOException e) {
			System.out.println("IOException" + e);
		} catch (Exception e) {
			System.out.println("Exception");
		}
	}

	public static void exit() {
		System.out.println("exit call");
		// log
		memberDao.writeContent();
		System.out.println("SAVE COMMAND");
	}
}