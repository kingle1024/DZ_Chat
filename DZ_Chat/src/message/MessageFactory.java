package message;

import message.chat.*;
import org.json.JSONObject;
import core.service.serviceimpl.chat.ChatService;
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
				Member.parseJSON(
						json.getJSONObject("sender"))
				, json.getString("message")
				, json.getString("to"));
	}

	private static DirMessage createDirMessage(ChatService chatService) {
		return new DirMessage(chatService);
	}

	private static ChatMessage createChatMessage(ChatService chatService, JSONObject json) {
		return new ChatMessage(
				chatService,
				Member.parseJSON(json.getJSONObject("sender"))
				, json.getString("message"));
	}
}