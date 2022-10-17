package message.chat;

import java.io.*;

import core.server.MainServer;
import log.Log;

public class SystemMessage extends Message implements Serializable {
	private static final long serialVersionUID = 7033598494494691135L;
	private final ChatRoom chatRoom;
	private final String chatRoomName;
	
	public SystemMessage(String chatRoomName, String message) {
		super(message);
		this.chatRoomName = chatRoomName;
		this.chatRoom = MainServer.chatRoomMap.get(chatRoomName);
	}

	public SystemMessage(ChatRoom chatRoom, String message) {
		super(message);
		this.chatRoom = chatRoom;
		this.chatRoomName = chatRoom.getRoomName();
	}
	
	@Override
	public void send(ObjectOutputStream os) throws IOException {
		os.writeObject("\t[System: " + message + "]");
		os.flush();
	}
	
	@Override
	public void push() {
		chatRoom.getChatServiceList().stream().forEach(s -> {
			try {
				send(s.getOs());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("\t[System")
				.append("> ")
				.append(message)
				.append("]\t")
				.toString();
	}

	@Override
	public Log toLog() {
		// TODO Auto-generated method stub
		return null;
	}
}
