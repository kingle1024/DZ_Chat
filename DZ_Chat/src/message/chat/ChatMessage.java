package message.chat;

import java.io.*;

import log.Log;
import log.LogQueue;
import log.NeedLog;
import member.Member;

public class ChatMessage extends Message implements NeedLog {
	private static final long serialVersionUID = -4472963080600091036L;
	private final Member sender;
	private final String chatRoomName;
	public ChatMessage(String chatRoomName, Member sender, String message) {
		super(message);
		this.chatRoomName = chatRoomName;
		this.sender = sender;
	}

	
	@Override
	public void send(ObjectOutputStream oos) throws IOException {
		oos.writeObject(this.toString());
		oos.flush();
	}
	
	@Override
	public void push() {
		System.out.println("message push: " + message);
		System.out.println("chatRoom: " + chatRoom);
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
				.append(sender.nickname())
				.append("> ")
				.append(message)
				.toString();
	}

	@Override
	public Log toLog() {
		String logMessage = "ChatMessage:" + sender.getUserId() + ":" + message;
		return new Log("chatlog.txt", logMessage);
	}
}
