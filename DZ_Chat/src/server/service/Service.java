package server.service;

import java.io.*;

import org.json.JSONObject;

public abstract class Service {
	private DataInputStream dis;
	private DataOutputStream dos;
	private byte[] buff;
	
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
	
	public void setInputStream(DataInputStream dis) {
		this.dis = dis;
	}
	
	public void setOutputStream(DataOutputStream dos) {
		this.dos = dos;
	}
	
	public abstract void request() throws IOException;
}
