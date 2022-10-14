package message.chat;

import java.io.*;

import member.Member;

public class ChatMessage extends Message {
	private static final long serialVersionUID = -4472963080600091036L;
	private final Member sender;
	private final String message;
	private final String chatRoomName;
	public ChatMessage(String chatRoomName, Member sender, String message) {
		this.chatRoomName = chatRoomName;
		this.sender = sender;
		this.message = message;
	}

	
	@Override
	public void send(ObjectOutputStream oos) throws IOException {
		oos.writeObject(new ChatMessage(this.chatRoomName, this.sender, message));
		oos.flush();
	}
	
	@Override
	public void push() {
		System.out.println("message push: " + message);
		System.out.println("chatROom: " + chatRoom);
		chatRoom.getChatServiceList().stream().forEach(s -> {
			try {
				System.out.println(s.getMe());
				send(s.getOs());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append(sender.toString())
				.append("> ")
				.append(message)
				.toString();
	}


}
