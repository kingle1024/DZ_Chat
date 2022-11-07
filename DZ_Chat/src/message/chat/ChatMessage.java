package message.chat;

import java.io.*;

import org.json.JSONObject;

import core.service.serviceimpl.chat.ChatService;
import log.Log;
import log.NeedLog;
import member.Member;
import property.Property;

public class ChatMessage implements NeedLog, Message {
	private final ChatService chatService;
	private final Member sender;
	private final String message;
	
	public ChatMessage(ChatService chatService, Member sender, String message) {
		this.chatService = chatService;
		this.sender = sender;
		this.message = message;
	}
	
	@Override
	public void push() {
		System.out.println("message push: " + message);
		JSONObject json = new JSONObject().put("message", this.toString());
		System.out.println(json);
		chatService.getChatServices().stream().forEach(s -> {
			try {
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
}
