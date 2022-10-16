package message.chat;

import java.io.*;

import member.Member;

public class PrivateChatMessage extends Message {
	private static final long serialVersionUID = -286765041005171349L;
	private final Member sender;
	private final String message;
	private final String chatRoomName;
	private final String to;
	public PrivateChatMessage(String chatRoomName, Member sender, String message, String to) {
		this.chatRoomName =chatRoomName;
		this.sender = sender;
		this.message = message;
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
				.append(sender.getUserId())
				.append(">")
				.append(message)
				.toString();
	}
}
