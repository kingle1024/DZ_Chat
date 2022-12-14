package message.chat;

import java.io.*;

import org.json.JSONObject;

import log.Log;
import member.Member;
import property.ServerProperties;
import server.service.serviceimpl.chat.ChatService;

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
		JSONObject json = new JSONObject().put("message", this.toString());
		chatService.getChatRoom().getChatServices().stream()
			.filter(s -> s.equalsUser(to))
			.forEach(s -> {
			try {
				s.send(json);
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

	public Log toLog() {
		String logMessage = "PrivateMessage:" + sender.getUserId() + " > " + to + " : "+ message;
		return new Log(ServerProperties.getChatLogFile(), logMessage);
	}
}
