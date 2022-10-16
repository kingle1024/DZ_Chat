package core.client;

import java.io.*;
import java.util.Scanner;

import core.mapper.ServiceResolver;

public class LoginClient extends ObjectStreamClient {
	@Override
	public void run() {
		try {
			Scanner scanner = new Scanner(System.in);
			while (true) {
				String id = scanner.nextLine();
				String pw = scanner.nextLine();
				connect(new ServiceResolver("LoginService"));
				send(id);
				send(pw);
				if ((Boolean) receive()) {
					unconnect();
				} else {
					System.out.println("틀렸다.");
					unconnect();
				}
			}
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
