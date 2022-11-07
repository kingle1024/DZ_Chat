package message.chat;

import java.io.*;

import org.json.JSONObject;

import core.service.serviceimpl.chat.ChatService;
import log.Log;

public class SystemMessage implements Message {
	private final ChatService chatService;
	private String message;
	
	public SystemMessage(ChatService chatService, String message) {
		this.chatService = chatService;
		this.message = message;
	}

	@Override
	public void push() {
		chatService.getChatServices().stream().forEach(s -> {
			try {
				chatService.send(new JSONObject().put("message", toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("\t[System")
				.append("> ")
				.append(message)
				.append("]\t")
				.toString();
	}

	@Override
	public Log toLog() {
		return null;
	}
}
