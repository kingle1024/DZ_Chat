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

import member.Member;
import message.chat.Message;

public class Client {
	private static String SERVER_HOST;
	private static int PORT_NUMBER;
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private Member member;
	private DatagramSocket datagramSocket;
	public void listenUDP() throws IOException {
		byte[] bytes = new byte[256];
		DatagramPacket receivePacket = new DatagramPacket(bytes, 0, bytes.length);
		datagramSocket.receive(receivePacket);
	}
	
	public void connect() throws IOException {
		socket = new Socket(SERVER_HOST, PORT_NUMBER);
		is = socket.getInputStream();
		os = socket.getOutputStream();
		System.out.println("[클라이언트] 서버에 연결");
	}
	
	public void send(Message message) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(os));
		oos.writeObject(message);
		oos.flush();
		oos.close();
	}
	
	public void receive() {
		Thread thread = new Thread(() -> {
			try {
				ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(is));
				while (true) {
					Message message = (Message) ois.readObject();
					System.out.println(message);
				}	
			} catch (IOException e) {
				
			} catch (ClassNotFoundException e1) { }
		});
		thread.start();
	}
	
	public void unconnect() throws IOException {
		socket.close();
	}
	
	public static void main(String[] args) {
		try {
			Client client = new Client();			
			Scanner scanner = new Scanner(System.in);
			while (true) {
				client.listenUDP();
				client.connect();
				String inputStr = scanner.nextLine();
				if ("q".equals(inputStr.toLowerCase())) break;
				
				// TODO Message send
//				Message message = new ChatMessage(chatRoom, me, inputStr);
//				Message message = new FileMessage(chatRoome, me, filePath(inputStr));
//				client.send(message);
				client.unconnect();
			}
			scanner.close();
			client.unconnect();
		} catch (IOException e) {
			
		}
	}
}
