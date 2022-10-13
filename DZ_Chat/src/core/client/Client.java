package core.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Scanner;

import core.server.Server;
import member.Member;
import message.chat.ChatMessage;
import message.chat.ChatRoom;
import message.chat.Message;

public class Client {
	private static String SERVER_HOST = "localhost";
	private static int PORT_NUMBER = 50_001;
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private Member member;
	private DatagramSocket datagramSocket;

	public void connect() throws IOException {
		socket = new Socket(SERVER_HOST, PORT_NUMBER);
		is = socket.getInputStream();
		os = socket.getOutputStream();
		System.out.println("[클라이언트] 서버에 연결");
	}

	public void send(Message message) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		oos.writeObject(message);
		oos.flush();
	}

	public void receive() {
		Thread thread = new Thread(() -> {
			try {
				ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				while (true) {
					Message message = (Message) ois.readObject();
					System.out.println(message);
				}
			} catch (IOException e) {

			} catch (ClassNotFoundException e1) {
			}
		});
		thread.start();
	}

	public void unconnect() throws IOException {
		socket.close();
		socket = null;
		System.out.println("[클라이언트] 연결 종료");
	}
	
	// Mock
	public void login(int x) {
		login("id"+x, "pw"+x, "name"+x, x);
	}
	public void login(String id, String pw, String name, int birth) {
		this.member = new Member(id, pw, name, birth);
		System.out.println(member.getName() + member.getUserId());
	}
	
	public static void main(String[] args) {
		System.out.println("[클라이언트] 시작");
		try {
			Client client = new Client();
			
			// Mock
			client.login(11);
			ChatRoom chatRoom = Server.chatRoomMap.get("TEST ROOM");
			System.out.println(Server.chatRoomMap.keySet().size());
			
			Scanner scanner = new Scanner(System.in);
			client.connect();
			while (true) {
				String inputStr = scanner.nextLine();
				if ("q".equals(inputStr.toLowerCase()))
					break;

				// TODO Message send
				Message message = new ChatMessage(chatRoom, client.member, inputStr);
//				Message message = new FileMessage(chatRoome, me, filePath(inputStr));
				client.send(message);
			}
			scanner.close();
			client.unconnect();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("[클라이언트] 종료");
	}
}
