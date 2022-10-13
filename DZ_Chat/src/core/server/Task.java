package core.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;

public class Task {
	private String ipAddress;
	private int port;
	
	public Task(String ipAddress, int port) {
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public void work() throws IOException {
		String data = "업데이트 내역";
		byte[] bytes = data.getBytes("UTF-8");
 		
		DatagramPacket sendPacket = new DatagramPacket(bytes, 0, bytes.length, new InetSocketAddress(ipAddress, port));
 		Server.datagramSocket.send(sendPacket);
	}
}
