package message.chat;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import core.server.Server;

public class SystemMessage extends Message implements Serializable {
	private static final long serialVersionUID = 7033598494494691135L;
	private final String message;
	private ChatRoom chatRoom;
	private final String chatRoomName;
	
	public SystemMessage(String chatRoomName, String message) {
		this.chatRoomName = chatRoomName;
		this.message = message;
	}
	
	@Override
	public void send(ObjectOutputStream os) throws IOException {

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
}
