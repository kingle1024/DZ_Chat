package client.chat;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import dto.IdPw;
import member.Member;
import message.ftp.FileMessage;

public class CommandParser {
	private final Member sender;
	private final String chatRoomName;
	private ThreadGroup threadGroup;
	private final JSONObject json = new JSONObject();
	
	public CommandParser(Member sender, String chatRoomName, ThreadGroup threadGroup) {
		this.sender = sender;
		this.chatRoomName = chatRoomName;
		this.threadGroup = threadGroup;
	}
	
	public JSONObject createJSONObject(String msg) throws IOException, ChatRoomExitException {
		json.clear();
		if (msg.startsWith("@")) return createPrivateChatJSON(msg);
		if (msg.startsWith("#exit")) return createExitJSON();
		if (msg.startsWith("#file")) return createFileJSON(msg);
		if (msg.startsWith("#dir")) return createDirJSON(msg);
		return createChatJSON(msg);
	}
	
	private JSONObject createChatJSON(String msg) {
		json.put("type", "chat");
		json.put("message", msg);
		json.put("sender", sender.getJSON());
		return json;
	}
	
	private JSONObject createPrivateChatJSON(String msg) {
		int idx = msg.indexOf(' ');
		if (idx == -1 || idx+1 >= msg.length())  {
			System.out.println("형식을 확인해주세요");
			System.out.println("@[보낼 사람 id] [보낼 내용]");
			return null;
		}
		String to = msg.substring(1, idx);
		msg = msg.substring(idx + 1);
		json.put("type", "privateChat");
		json.put("message", msg);
		json.put("sender", sender.getJSON());
		json.put("to", to);
		return json;
	}
	
	private JSONObject createExitJSON() throws ChatRoomExitException {
		throw new ChatRoomExitException();
	}
	
	private JSONObject createDirJSON(String chat) {
		json.put("type", "#dir");
		json.put("message", chat);
		json.put("chatRoomName", chatRoomName);
		json.put("sender", sender.getJSON());
		return json;
	}
	
	private JSONObject createFileJSON(String chat) throws IOException {
		System.out.println("createFileMessage");
		String[] message = chat.split(" ");
		if(message.length == 1){
			message = new String[3];
			message[1] = "temp";
			message[2] = "temp2";
		}
		String fileName = message[1];
		boolean result = fileMessage(chat);

		if(!result){
			json.put("type", "privateChat");
			json.put("message", fileName + " 파일 전송 취소");
			json.put("sender", sender.getJSON());
			json.put("to", "privateMan");
			return json;
		}else{
			json.put("type", "chat");
			json.put("message", fileName + " 파일 전송");
			json.put("sender", sender.getJSON());
			return json;
		}
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

		final String[] message = getMessageSplit(chat);
		map.put("command", message[0]);
		map.put("fileAndPath", message[1]);

		return new FileMessage().client(map);
	}
	public String getChatAndRoomNameStr(String chat){
		return chat +
				" " +
				"room/" +
				chatRoomName;
	}
	public String[] getMessageSplit(String chat){
		String[] message = chat.split(" ");
		if(message.length == 1){
			message = new String[3];
			message[1] = "temp";
			message[2] = "temp2";
		}
		return message;
	}
}
