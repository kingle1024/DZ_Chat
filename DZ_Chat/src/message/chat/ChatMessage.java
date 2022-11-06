package message.chat;

import java.io.*;

import org.json.JSONObject;

import log.Log;
import log.NeedLog;
import member.Member;
import property.Property;

public class ChatMessage extends Message implements NeedLog {
	private final Member sender;
	private JSONObject json = new JSONObject();
	public ChatMessage(Member sender, String message) {
		super(message);
		this.sender = sender;
	}
	
	@Override
	public void push() {
		System.out.println("message push: " + message);
		System.out.println("chatRoom: " + chatRoom);
		json.put("message", this.toString());
		chatRoom.getChatServices().stream().forEach(s -> {
			try {
				System.out.println(s.getMe());
				chatService.send(json);
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
		return new Log(Property.client().get("CHAT_LOG_FILE"), logMessage);
	}

	@Override
	public JSONObject getJSON() {
		return json;
	}
}
