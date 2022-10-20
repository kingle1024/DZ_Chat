package message.chat;

import java.io.*;

import log.Log;
import member.Member;
import property.Property;

public class PrivateChatMessage extends Message {
	private static final long serialVersionUID = -286765041005171349L;
	private final Member sender;
	private final String chatRoomName;
	private final String to;
	public PrivateChatMessage(String chatRoomName, Member sender, String message, String to) {
		super(message);
		this.chatRoomName =chatRoomName;
		this.sender = sender;
		this.to = to;
	}
	
	@Override
	public void send(ObjectOutputStream os) throws IOException {
		os.writeObject(this.toString());
		os.flush();
	}

	@Override
	public void push() {
		chatRoom.getChatServiceList().stream()
			.filter(s -> s.equalsUser(to))
			.forEach(s -> {
			try {
				send(s.getOs());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	@Override
	public String toString() {
		return new StringBuilder("@")
				.append(sender.nickname())
				.append(">")
				.append(message)
				.toString();
	}

	@Override
	public Log toLog() {
		String logMessage = "PrivateMessage:" + sender.getUserId() + ":" + message;
		return new Log(Property.server().get("CHAT_LOG_FILE"), logMessage);
	}
}
