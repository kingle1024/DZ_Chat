package core.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Queue;

import member.Member;
import message.chat.Message;

public class SocketClient {
	private final Server server;
	private final Socket socket;
	private final InputStream is;
	private final OutputStream os;
	private final Member member;

	public SocketClient(Server server, Socket socket) throws IOException {
		this.server = server;
		this.socket = socket;
		this.is = socket.getInputStream();
		this.os = socket.getOutputStream();
		this.member = null;
		
		System.out.println("연결");
		if (Server.taskMap.containsKey(new Task(socket.getInetAddress().getHostAddress(), socket.getPort()))) {
			System.out.println("TASKMAP");
			Queue<Message> messageQue = Server.taskMap.get(member);
			while (!messageQue.isEmpty()) {
				messageQue.poll().send(os);
			}
		}
		System.out.println("START RECEIVE");
		receive();
		System.out.println("END RECEIVE");
	}

	public void receive() {
		Server.threadPool.execute(() -> {
			try {
				while (true) {
					ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(is));
					Message message = (Message) ois.readObject();
					System.out.println("RECEIVE: " + message);
					message.sendAll(os);
					message.push();
				}
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (ClassNotFoundException cfe) {
				cfe.printStackTrace();
			}
		});
	}
}
