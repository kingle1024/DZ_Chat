package core.server;

import java.net.Socket;

import member.Member;
import message.chat.ChatRoom;

public class EntranceChatRoomService extends Service {
	private final ChatRoom chatRoom;
	public EntranceChatRoomService(Server server, Socket socket, String chatRoomName) {
		super(server, socket);
		if (!Server.chatRoomMap.containsKey(chatRoomName)) throw new IllegalArgumentException();
		chatRoom = Server.chatRoomMap.get(chatRoomName);
		// Mock
		int random = (int) (Math.random() * 10);
		super.me = new Member("id" + random, "pw" + random, "name" + random, random);
	}

	@Override
	public void request() {
		ChatService chatService = new ChatService(super.server, super.socket, chatRoom, super.me);
		chatRoom.entrance(chatService);
		chatService.request();
	}
}
