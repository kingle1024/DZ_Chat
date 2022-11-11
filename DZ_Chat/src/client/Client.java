package client;

import java.io.*;

import java.net.Socket;

import org.json.JSONObject;

import property.ServerProperties;

public abstract class Client {
	private static final String SERVER_HOST = ServerProperties.getIP();
	private static final int PORT_NUMBER = ServerProperties.getServerPort();
	private Socket socket;
	private DataOutputStream dos;
	private DataInputStream dis;
	private byte[] buff;
	
	public void connect(String commandType) throws IOException {
		System.out.println("[클라이언트] 서버 연결 시도");
		socket = new Socket(SERVER_HOST, PORT_NUMBER);
		System.out.println("Socket 생성");
		dos = new DataOutputStream(socket.getOutputStream());
		dis = new DataInputStream(socket.getInputStream());
		System.out.println("[클라이언트] 서버에 연결 성공");
		send(new JSONObject().put("commandType", commandType));
		System.out.println("send command");
	}

	public void unconnect() throws IOException {
		socket.close();
		System.out.println("[클라이언트] 연결 종료");
	}

	public JSONObject receive() throws IOException {
		int len = dis.readInt();
		buff = new byte[len];
		dis.read(buff, 0, len);
		return new JSONObject(new String(buff, "UTF-8"));
	}
	
	public void send(JSONObject json) throws IOException {
		buff = json.toString().getBytes("UTF-8");
		dos.writeInt(buff.length);
		dos.write(buff);
		dos.flush();
	}

	public abstract JSONObject run();
}
