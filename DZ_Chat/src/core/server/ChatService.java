package core.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import member.Member;
import message.chat.ChatRoom;
import message.chat.Message;

public class ChatService extends Service {
	private final Member me;
	private final ChatRoom chatRoom;
	public ChatService(Server server, Socket socket, ChatRoom chatRoom, Member me) {
		super(server, socket);
		this.chatRoom = chatRoom;
		this.me = me;
	}

	@Override
	public void request() {
		Server.threadPool.execute(() -> {
			try {
				while (true) {
					ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(super.socket.getInputStream()));
					Message message = (Message) ois.readObject();
					message.push();
					System.out.println("[Server]" + message);
				}
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (ClassNotFoundException cfe) {
				cfe.printStackTrace();
			}
		});
	}
	
	public Member getMe() {
		return me;
	}
}
