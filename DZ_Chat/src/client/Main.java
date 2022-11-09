package client;

import member.Member;

import java.util.*;

import client.chat.FileSendClient;
import client.view.View;
import client.view.ViewMap;

public class Main {
	private static Member me;
	private static Scanner scanner;

	public static void main(String[] args) {
//		scanner = new Scanner(System.in);
//		System.out.println("클라이언트 시작");
//		View view = ViewMap.getView("Main");
//		try {
//			while (true) {
//				System.out.println(view.getViewName());
//				view = view.nextView();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			scanner.close();
//		}
		FileSendClient fileSendClient = new FileSendClient();
		fileSendClient.run();
	}

	public static Member getMe() {
		return me;
	}

	public static void setMe(Member member) {
		me = member;
	}

	public static Scanner getScanner() {
		return scanner;
	}
}