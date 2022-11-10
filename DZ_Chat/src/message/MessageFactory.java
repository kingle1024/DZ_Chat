package message;

import message.chat.*;
import server.service.serviceimpl.chat.ChatService;

import org.json.JSONObject;

import member.Member;

public class MessageFactory {

	public static Message create(ChatService chatService, JSONObject json) {
		String type = json.getString("type");
		if ("@".equals(type)) return createPrivateChatMessage(chatService, json);
		if ("#dir".equals(type)) return createDirMessage(chatService);
		return createChatMessage(chatService, json);
	}

	public static SystemMessage createSystemMessage(ChatService chatService, String message) {
		return new SystemMessage(chatService, message);
	}

	private static PrivateChatMessage createPrivateChatMessage(ChatService chatService, JSONObject json) {
		return new PrivateChatMessage(
				chatService,
				Member.parseJSONString(
						json.getString("sender"))
				, json.getString("message")
				, json.getString("to"));
	}

	private static DirMessage createDirMessage(ChatService chatService) {
		return new DirMessage(chatService);
	}

	private static ChatMessage createChatMessage(ChatService chatService, JSONObject json) {
		return new ChatMessage(
				chatService,
				Member.parseJSONString(json.getString("sender"))
				, json.getString("message"));
	}
}