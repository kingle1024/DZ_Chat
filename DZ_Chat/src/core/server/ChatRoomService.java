package core.server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatRoomService extends Service {

	public ChatRoomService(Server server, Socket socket) {
		super(server, socket);
	}

	@Override
	public void request() {
		Server.threadPool.execute(() -> {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				oos.writeObject(Server.chatRoomMap);
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

}
