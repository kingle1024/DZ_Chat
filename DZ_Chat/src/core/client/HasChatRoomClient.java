package core.client;

import java.io.IOException;

import core.mapper.Command;

public class HasChatRoomClient extends ObjectStreamClient {
	private String chatRoomName;
	private boolean hasChatRoomClient = false;

	public HasChatRoomClient(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}

	public boolean getHasGetRoomClient() {
		return hasChatRoomClient;
	}
	
	@Override
	public void run() {
		try {
			connect(new Command("HasChatRoomService"));
			send(chatRoomName);
			hasChatRoomClient =(Boolean) receive();
			unconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
