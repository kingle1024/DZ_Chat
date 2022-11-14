package server;

import java.io.*;
import java.util.Scanner;

import log.LogConsumer;
import member.MemberDao;
import property.ServerProperties;
import server.map.ChatRoomMap;

/*
 * 실행 전 컴파일러 설정
 * 메서드 파라미터 이름 정보를 가져와야 하기 때문에 컴파일 옵션 -parameters 를 줘야합니다.
 * Eclipse: Window > Preferences > Java > Compiler > Store information about method parameters
 */

public class Main {
	public static void main(String[] args) {
//		try {
//			System.setOut(new PrintStream(System.out, true, "UTF-8"));
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
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