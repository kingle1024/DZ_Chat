package message;

import org.json.JSONObject;

import core.service.serviceimpl.chat.ChatService;
import member.Member;
import message.chat.ChatMessage;
import message.chat.DirMessage;
import message.chat.Message;
import message.chat.PrivateChatMessage;
import message.chat.SystemMessage;

public class MessageFactory {
	
	public static Message create(ChatService chatService, JSONObject json) {
		String type = json.getString("type");
		if ("@".equals(type)) return createPrivateChatMessage(chatService, json);
		if ("#dir".equals(type)) return createDirMessage(chatService, json);
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
	public String[] getMessageSplit(String chat){
		String message[] = chat.split(" ");
		if(message.length == 1){
			message = new String[3];
			message[1] = "temp";
			message[2] = "temp2";
		}
		return message;
	}
	
	private static DirMessage createDirMessage(ChatService chatService, JSONObject json) {
		return new DirMessage(
				chatService
				, Member.parseJSON(json.getJSONObject("sender"))
				, json.getString("message"));
	}
	

	public boolean fileMessage(String chat) throws IOException {
		if(threadGroup == null){
			threadGroup = new ThreadGroup(sender.getUserId()+chatRoomName);
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("chat", chat); //  삭제 예정
		map.put("chatRoomName", this.chatRoomName);
		map.put("threadGroup", threadGroup);
		map.put("chatAndRoomName", getChatAndRoomNameStr(chat));
		final String message[] = chat.split(" ");
		map.put("command", message[0]);
		map.put("fileAndPath", message[1]);

		return new FileMessage().run(map);
	}
	public String getChatAndRoomNameStr(String chat){
		StringBuilder input = new StringBuilder();
		input.append(chat)
				.append(" ")
				.append("room/")
				.append(chatRoomName);
		return input.toString();
	}
}



	private static ChatMessage createChatMessage(ChatService chatService, JSONObject json) {
		return new ChatMessage(
				chatService,
				Member.parseJSON(json.getJSONObject("sender"))
				, json.getString("message"));
	}
}
