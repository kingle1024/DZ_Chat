package core.server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

import member.Member;
import message.chat.Message;

public class Server {
	static final SynchronousQueue<Task> taskQue =  new SynchronousQueue<>();
	static final Map<Member, Queue<Message>> taskMap = Collections.synchronizedMap(new HashMap<>()); 
	static DatagramSocket datagramSocket;
	
	private static final int PORT_NUMBER = 50_001;
	private ServerSocket serverSocket;
	public void start() throws IOException {
		serverSocket = new ServerSocket(PORT_NUMBER);
		System.out.println("[서버] 시작");
		
		Thread thread = new Thread(() -> {
			try {
				Socket socket = serverSocket.accept();
				SocketClient sc = new SocketClient(this, socket);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		thread.start();
	}
	public static void main(String[] args) {
		try {
			datagramSocket = new DatagramSocket();	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
