package message.chat;

import java.io.*;

import org.json.JSONObject;

import core.service.serviceimpl.chat.ChatService;
import log.Log;
import member.Member;
import property.Property;

public class PrivateChatMessage implements Message {
	private final ChatService chatService;
	private final Member sender;
	private final String to;
	private String message;
	
	public PrivateChatMessage(ChatService chatService, Member sender, String message, String to) {
		this.chatService = chatService;
		this.sender = sender;
		this.message = message;
		this.to = to;
	}

	@Override
	public void push() {
		JSONObject json = new JSONObject("message", toString());
		chatService.getChatServices().stream()
			.filter(s -> s.equalsUser(to))
			.forEach(s -> {
			try {
				chatService.send(json);
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
