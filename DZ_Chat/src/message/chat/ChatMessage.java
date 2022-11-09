package message.chat;

import java.io.*;

import org.json.JSONObject;
import log.Log;
import log.LogQueue;
import member.Member;
import property.ClientProperties;
import server.service.serviceimpl.chat.ChatService;

public class ChatMessage implements Message {
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
		chatService.getChatRoom().getChatServices().stream().forEach(s -> {
			System.out.println(s.getChatRoom() + ": " + s.getMe());
			try {
				s.send(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		LogQueue.add(toLog());
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append(sender.nickname())
				.append("> ")
				.append(message)
				.toString();
	}

	public Log toLog() {
		String logMessage = "ChatMessage " + sender.getUserId() + " : " + message;
		return new Log(ClientProperties.getChatLogFile(), logMessage);
	}
}
