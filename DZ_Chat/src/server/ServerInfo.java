package server;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerInfo {
	private String name;
	private String HOST = "";
	private int PORT_NUMBER = -1;
	
	public ServerInfo(String name, int port) {
		this.name = name;
		try {
			this.HOST = InetAddress.getLocalHost().toString();
			this.PORT_NUMBER = port;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public String getHost() {
		return HOST;
	}
	
	public int getPort() {
		return PORT_NUMBER;
	}
	
	public void printStartMessage() {
		System.out.println("[" + name + "] 시작" + HOST + ":" + PORT_NUMBER);
	}
}
