package core.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import member.Member;
import message.chat.ChatMessage;
import message.chat.Message;

public class Client {
	private static String SERVER_HOST = "192.168.30.84";
	private static int PORT_NUMBER = 50_001;
	private Socket socket;
	private ObjectInputStream is;
	private ObjectOutputStream os;
	private Member member;

	public void connect() throws IOException {
		System.out.println("[클라이언트] 서버 연결 시도");
		socket = new Socket(SERVER_HOST, PORT_NUMBER);
		is = new ObjectInputStream(socket.getInputStream());
		
		System.out.println("123");
		os = new ObjectOutputStream(socket.getOutputStream());
		System.out.println("[클라이언트] 서버에 연결 성공");
	}

	public void send(Message message) throws IOException {
		System.out.println("Client send");
		os.writeObject(message);
		os.flush();
	}

	public void receive() {
		System.out.println("[클라이언트] Receive");
		Thread thread = new Thread(() -> {
			try {
				System.out.println(socket.isConnected());
				while (true) {
					System.out.println("Client wait");
					Message message = (Message) is.readObject();
					System.out.println("Receive Message");
					System.out.println(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e1) {
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
			client.login(1);
			String chatRoomName = "TEST ROOM";
			
//			Scanner scanner = new Scanner(System.in);
			client.connect();
			client.receive();
			while (true) {
//				String inputStr = scanner.nextLine();
//				if ("q".equals(inputStr.toLowerCase()))
//					break;

				// TODO Message send
				Message message = new ChatMessage(chatRoomName, client.member, "HEEL");
//				Message message = new FileMessage(chatRoome, me, filePath(inputStr));
				client.send(message);
			}
//			scanner.close();
//			client.unconnect();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("[클라이언트] 종료");
	}
}
