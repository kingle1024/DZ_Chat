package core.client;

import java.io.*;
import java.util.*;
import java.net.Socket;

import member.Member;
import message.chat.ChatMessage;
import message.chat.Message;

public abstract class Client {
	private static final String SERVER_HOST = "192.168.30.84";
	private static final int PORT_NUMBER = 50_001;
	Socket socket;
	ObjectInputStream is;
	ObjectOutputStream os;

	public void connect() throws IOException {
		System.out.println("[클라이언트] 서버 연결 시도");
		socket = new Socket(SERVER_HOST, PORT_NUMBER);
		System.out.println("Socket 생성");
		os = new ObjectOutputStream(socket.getOutputStream());
		is = new ObjectInputStream(socket.getInputStream());

		System.out.println("[클라이언트] 서버에 연결 성공");
	}

	public void unconnect() throws IOException {
		socket.close();
		System.out.println("[클라이언트] 연결 종료");
	}

	public abstract void receive() throws IOException, ClassNotFoundException;

	public abstract void send(Object obj) throws IOException;

	public abstract void run();
}
