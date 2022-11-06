package message.chat;

import java.io.*;

import org.json.JSONObject;

import core.server.MainServer;
import core.service.serviceimpl.chat.ChatService;
import log.Log;

public class SystemMessage extends Message implements Serializable {
	private final ChatService chatService;
	private final ChatRoom chatRoom;
	
	public SystemMessage(ChatService chatService, String message) {
		super(message);
		this.chatService = chatService;
		this.chatRoom = chatService.c
	}

	@Override
	public void push() {
		chatRoom.getChatServices().stream().forEach(s -> {
			try {
				chatService.send(new JSONObject().put("message", "\t[System: " + message + "]"));;
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
		// TODO Auto-generated method stub
		return null;
	}
}
