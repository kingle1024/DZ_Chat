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
		socket = new Socket(SERVER_HOST, PORT_NUMBER);
		dos = new DataOutputStream(socket.getOutputStream());
		dis = new DataInputStream(socket.getInputStream());
		send(new JSONObject().put("commandType", commandType));
	}

	public void unconnect() throws IOException {
		socket.close();
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
