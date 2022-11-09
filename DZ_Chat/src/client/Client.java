package client;

import java.io.*;

import java.net.Socket;

import org.json.JSONObject;

import property.ServerProperties;

public abstract class Client {
	private static final String SERVER_HOST = ServerProperties.getIP();
	private static final int PORT_NUMBER = Integer.parseInt(ServerProperties.getServerPort());
	private Socket socket;
	private BufferedWriter bw;
	private BufferedReader br;

	public void connect(String commandType) throws IOException {
		System.out.println("[클라이언트] 서버 연결 시도");
		socket = new Socket(SERVER_HOST, PORT_NUMBER);
		System.out.println("Socket 생성");
		this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		System.out.println("[클라이언트] 서버에 연결 성공");
		send(new JSONObject().put("commandType", commandType));
		System.out.println("send command");
	}

	public void unconnect() throws IOException {
		socket.close();
		System.out.println("[클라이언트] 연결 종료");
	}

	public JSONObject receive() throws IOException {
		return new JSONObject(br.readLine());
	}
	
	public void send(JSONObject json) throws IOException {
		bw.write(json.toString());
		bw.newLine();
		bw.flush();
	}
	
	public void send(String key, String value) throws IOException {
		JSONObject json = new JSONObject();
		json.put(key, value);
		bw.write(json.toString());
		bw.flush();
	}
	

	public abstract JSONObject run();
}
