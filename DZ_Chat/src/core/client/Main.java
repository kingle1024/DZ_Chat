package core.client;

import java.io.*;

public class Main {
	public static void main(String[] args) {
		System.out.println("클라이언트 시작");
		ObjectStreamClient client = new ChatClient("TEST ROOM1");
		try {
			client.run();	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
