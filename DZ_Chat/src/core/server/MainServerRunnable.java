package core.server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import core.service.serviceimpl.MapperService;
import member.Member;
import message.chat.ChatRoom;

public class MainServerRunnable implements Runnable {
	private final int PORT_NUMBER;
	public static final Map<String, ChatRoom> chatRoomMap = Collections.synchronizedMap(new HashMap<>()); 
	public static final Map<String, Member> memberMap = Collections.synchronizedMap(new TreeMap<>());
	

	private ServerSocket serverSocket;
	
	public MainServerRunnable(int port) {
		this.PORT_NUMBER = port;
	}
	
	
	@Override
	public void run() {
		try {
			System.out.println("MainServer Thread");
			serverSocket = new ServerSocket(PORT_NUMBER);

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("New Socket Accept");
				ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream()); 
				ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
				new MapperService(is, os).request();
			}	
		} catch (IOException e) {
			
		}
		
	}
}
