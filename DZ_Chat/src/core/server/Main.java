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
//		System.out.println("server.Main.main");
		
		Thread memberSave = new Thread(() -> {
			while (true) {
				if (Thread.currentThread().isInterrupted()) return;
				save();
			}
		});
		
		
		// Mock ChatRoom
		for (int i = 0; i < 10; i++) {
			MainServer.chatRoomMap.put("TEST ROOM" + i, new ChatRoom("TEST ROOM" + i));
		}
		try {
			System.out.println("readContent");
			memberDao.readContent();
			System.out.println("server.Main" + memberMap.size());
			for (Member m : memberMap.values()) {
				System.out.println(m);
			}
			Server server = new MainServer(50_001);
			server.start();
//         FtpServer.startServer();

			while (true) {
				InputStreamReader is = new InputStreamReader(System.in);
				char input = (char) is.read();
				System.out.println("INPUT: " + input);
				if ('q' == input)
					break;
				if ('s' == input)
					memberSave.start();
			}

//         FtpServer.stopServer();
			server.stop();
			memberSave.interrupt();

		} catch (IOException e) {
			System.out.println("IOException" + e);
		} catch (Exception e) {
			System.out.println("Exception");
		}
	}

	public static void save() {
		// log
		memberDao.writeContent();
		System.out.println("SAVE COMMAND");
	}
}