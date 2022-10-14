package core.server;

import java.io.*;
import java.net.Socket;

import member.Member;
import message.chat.ChatRoom;
import message.chat.Message;

public class ChatService extends Service {
	private final Member me;
	private final ChatRoom chatRoom;
	private final InputStream is;
	private final OutputStream os;
	public ChatService(Server server, Socket socket, ChatRoom chatRoom, Member me) throws IOException {
		super(server, socket);
		this.chatRoom = chatRoom;
		this.me = me;
		this.is = socket.getInputStream();
		this.os = socket.getOutputStream();
	}

	@Override
	public void request() {
		Server.threadPool.execute(() -> {
			try {
				while (true) {
					System.out.println("chatService: " + socket.isConnected());
					ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(is));
					Message message = (Message) ois.readObject();
					message.setChatRoom(chatRoom);
					message.push();
					System.out.println("[Server]" + message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException cfe) {
				cfe.printStackTrace();
			}
		});
	}
	
	public Member getMe() {
		return me;
	}
	
	public OutputStream getOs() {
		return os;
	}
	
	public boolean equalsUser(String name) {
		return me.getName().equals(name);
	}
}
