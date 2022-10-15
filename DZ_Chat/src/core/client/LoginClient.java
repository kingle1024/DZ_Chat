package core.client;

import java.io.*;
import java.util.Scanner;

import core.mapper.Command;

public class LoginClient extends ObjectStreamClient {
	private Boolean loginSuccess = false;
	@Override
	public void receive() throws IOException, ClassNotFoundException {
		loginSuccess = (Boolean) is.readObject();
	}

	@Override
	public void run() throws IOException {
		os = new ObjectOutputStream(new BufferedOutputStream(os));
		is = new ObjectInputStream(new BufferedInputStream(is));
		try {
			Scanner scanner = new Scanner(System.in);
			while (true) {
				String id = scanner.nextLine();
				String pw = scanner.nextLine();
				
				connect(new Command("LoginService"));
				os = new ObjectOutputStream(new BufferedOutputStream(super.os));
				is = new ObjectInputStream(new BufferedInputStream(super.is));
				send(id);
				send(pw);
				receive();	
				if (loginSuccess) {
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
