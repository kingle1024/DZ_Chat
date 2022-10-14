package core.server;

import java.io.*;
import java.net.Socket;

public class GetChatRoomListService extends Service {
	public GetChatRoomListService(Server server, Socket socket) {
		super(server, socket);
	}

	@Override
	public void request() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
//		oos.writeObject(Server.chatRoomMap.keySet()); // Set is Not Serializable
		oos.writeObject(Integer.valueOf(Server.chatRoomMap.size()));
		Server.chatRoomMap.keySet().stream().forEach(t -> {
			try {
				oos.writeObject(t);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		oos.flush();
		oos.close();
		socket.close();
	}

}
