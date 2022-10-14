package core.client;

import java.io.*;
import java.util.*;
import java.net.Socket;

import member.Member;
import message.chat.ChatMessage;
import message.chat.Message;

public class Client {
	private static String SERVER_HOST = "192.168.30.84";
	private static int PORT_NUMBER = 50_001;
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Member member;

	public void connect() throws IOException {
		System.out.println("[클라이언트] 서버 연결 시도");
		socket = new Socket(SERVER_HOST, PORT_NUMBER);
		is = socket.getInputStream();
		os = socket.getOutputStream();
		System.out.println("[클라이언트] 서버에 연결 성공");
	}

	public void send(Message message) throws IOException {
		oos = new ObjectOutputStream(os);
		oos.writeObject(message);
		oos.flush();
	}

	public void receive() {
		Thread thread = new Thread(() -> {
			try {
				while (true) {
					ois = new ObjectInputStream(is);
					Message message = (Message) ois.readObject();
					System.out.println(message);
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
		thread.start();
	}

	public void unconnect() throws IOException {
		socket.close();
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
			client.login(999);
			String chatRoomName = "TEST ROOM";
			
			Scanner scanner = new Scanner(System.in);
			client.connect();
			client.receive();
			while (true) {
				String inputStr = scanner.nextLine();
				if ("q".equals(inputStr.toLowerCase()))
					break;

				// TODO Message send
				Message message = new ChatMessage(chatRoomName, client.member, inputStr);
//				Message message = new FileMessage(chatRoome, me, filePath(inputStr));
				client.send(message);
			}
			scanner.close();
			client.unconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("[클라이언트] 종료");
	}
}
