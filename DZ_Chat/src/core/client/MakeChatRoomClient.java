package core.client;

import java.io.IOException;
import java.util.*;

import core.mapper.Command;

public class MakeChatRoomClient extends Client {
	Boolean makeSuccess = false;
	@Override
	public void receive() throws IOException, ClassNotFoundException {
		makeSuccess = (Boolean) is.readObject();
	}

	@Override
	public void send(Object obj) throws IOException {
		os.writeObject(obj);
		os.flush();
	}

	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		try {
			while (true) {
				String chatRoomName = scanner.nextLine();
				connect();
				send(new Command(chatRoomName));
				receive();
				if (makeSuccess) { 
					unconnect();
					new ChatClient(chatRoomName).run();
					break;
				} else {
					System.out.println("Duplicate Chat Room Name.");	
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
