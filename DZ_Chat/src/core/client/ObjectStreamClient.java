package core.client;

import java.io.*;

import core.client.view.View;
import core.mapper.ServiceResolver;
import java.net.Socket;

import org.json.JSONObject;

import property.Property;

public abstract class ObjectStreamClient implements Client {
	private static final String SERVER_HOST = Property.server().get("IP");
	private static final int PORT_NUMBER = Integer.parseInt(Property.server().get("SERVER_PORT"));
	private Socket socket;
	protected ObjectOutputStream os;
	protected ObjectInputStream is;
	
	public void connect(ServiceResolver command) throws IOException {
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

	public JSONObject receive() throws IOException, ClassNotFoundException {
		is.readObject();
		return new JSONObject();
	}

	public void send(Object obj) throws IOException {
		os.writeObject(obj);
		os.flush();
	}

	public abstract JSONObject run();
}
