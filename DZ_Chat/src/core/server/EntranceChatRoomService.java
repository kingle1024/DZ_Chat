package core.server;

import java.net.Socket;

import message.chat.ChatRoom;

public class EntranceChatRoomService extends Service {
	private final ChatRoom chatRoom;
	public EntranceChatRoomService(Server server, Socket socket, String chatRoomName) {
		super(server, socket);
		if (!Server.chatRoomMap.containsKey(chatRoomName)) throw new IllegalArgumentException();
		chatRoom = Server.chatRoomMap.get(chatRoomName);
	}

	@Override
	public void request() {
		System.out.println("Entrance ChatRoom");
		chatRoom.entrance(new ChatService(super.server, super.socket, chatRoom, super.me));
		System.out.println("Total: " + chatRoom.size());
	}
}
