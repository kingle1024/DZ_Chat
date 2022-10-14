package core.client;

public class Main {
	public static void main(String[] args) {
//		Client client = new ChatClient();
		Client client = new ChatRoomListClient();
		client.run();
	}
}
