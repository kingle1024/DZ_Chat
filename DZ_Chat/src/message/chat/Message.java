package message.chat;

public abstract class Message {
	private ChatRoom chatRoom;
	public abstract void send();
}
