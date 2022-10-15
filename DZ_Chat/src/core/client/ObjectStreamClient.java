package core.client;

import java.io.*;
import java.util.*;

import core.mapper.Command;

import java.net.Socket;

import member.Member;
import message.chat.ChatMessage;
import message.chat.Message;

public abstract class ObjectStreamClient {
	private static final String SERVER_HOST = "192.168.45.74";
	private static final int PORT_NUMBER = 50_001;
	private Socket socket;
	protected ObjectOutputStream os;
	protected ObjectInputStream is;
	
	public void connect(Command command) throws IOException {
		System.out.println("[클라이언트] 서버 연결 시도");
		socket = new Socket(SERVER_HOST, PORT_NUMBER);
		System.out.println("Socket 생성");
		this.os = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		this.is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		System.out.println("[클라이언트] 서버에 연결 성공");
		send(command);
	}

	public void unconnect() throws IOException {
		socket.close();
		System.out.println("[클라이언트] 연결 종료");
	}

	public abstract void receive() throws IOException, ClassNotFoundException;

	public void send(Object obj) throws IOException {
		os.writeObject(obj);
		os.flush();
	}

	public abstract void run() throws IOException;
}
