package message.chat;

import java.time.LocalDateTime;

public abstract class Message {
	private ChatRoom chatRoom;
	private final LocalDateTime time;
	public Message(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
		this.time = LocalDateTime.now();
	}
	public abstract void send();
}
