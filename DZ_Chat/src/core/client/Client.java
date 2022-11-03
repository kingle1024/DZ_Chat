package core.client;

import java.io.*;

import core.client.mapper.RequestType;
import core.common.JSONizable;

import java.net.Socket;
import java.util.stream.Collectors;

import org.json.JSONObject;

import property.Property;

public abstract class Client {
	private static final String SERVER_HOST = Property.server().get("IP");
	private static final int PORT_NUMBER = Integer.parseInt(Property.server().get("SERVER_PORT"));
	private Socket socket;
	private BufferedWriter bw;
	private BufferedReader br;

	
	public void connect(RequestType command) throws IOException {
		System.out.println("[클라이언트] 서버 연결 시도");
		socket = new Socket(SERVER_HOST, PORT_NUMBER);
		System.out.println("Socket 생성");
		this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		System.out.println("[클라이언트] 서버에 연결 성공");
		send(command);
	}

	public void unconnect() throws IOException {
		socket.close();
		System.out.println("[클라이언트] 연결 종료");
	}

	public JSONObject receive() {
		return new JSONObject(br.lines().collect(Collectors.joining("\n")));
	}

	public void send(JSONizable json) throws IOException {
		bw.write(json.toJson().toString());
		bw.flush();
	}
	
	public void send(JSONObject json) throws IOException {
		bw.write(json.toString());
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
