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
		JSONObject json = new JSONObject().put("message", this.toString());
		chatService.getChatRoom().getChatServices().forEach(s -> {
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
		return sender.nickname() +
				"> " +
				message;
	}

	public Log toLog() {
		String logMessage = "ChatMessage " + chatService.getChatRoom().getChatRoomName() + " " + sender.getUserId() + " : " + message;
		return new Log(ClientProperties.getChatLogFile(), logMessage);
	}
}
