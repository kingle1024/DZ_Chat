package core.client;

import java.io.IOException;
import java.util.Scanner;

import core.mapper.Command;

public class LoginClient extends Client {
	private Boolean loginSuccess = false;
	@Override
	public void receive() throws IOException, ClassNotFoundException {
		loginSuccess = (Boolean) is.readObject();
	}

	@Override
	public void send(Object obj) throws IOException {
		os.writeObject(obj);
		os.flush();
	}

	@Override
	public void run() {
		try {
			Scanner scanner = new Scanner(System.in);
			while (true) {
				String id = scanner.nextLine();
				String pw = scanner.nextLine();
				
				connect();
				send(new Command("LoginService"));
				send(id);
				send(pw);
				receive();	
				if (loginSuccess) {
					unconnect();
//					scanner.close(); (X)
//					new ChatClient("asdf").run();
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
