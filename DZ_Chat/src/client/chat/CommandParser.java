package client.chat;

import java.io.IOException;
import dto.*;
import dto.chat.ChatDto;
import dto.chat.ChatInfo;
import dto.chat.DirDto;
import dto.chat.PrivateChatDto;

import org.json.JSONObject;
import member.Member;
import message.ftp.FileMessage;

public class CommandParser {
	private final Member sender;
	private final String chatRoomName;
	private ThreadGroup threadGroup;
	private final JSONObject json = new JSONObject();
	private String command;
	private String fileName;

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
		return Transfer.toJSON(new ChatDto("chat", msg, sender));
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
		return Transfer.toJSON(new PrivateChatDto("@", msg, sender, to));
	}

	private JSONObject createExitJSON() throws ChatRoomExitException {
		throw new ChatRoomExitException();
	}

	private JSONObject createDirJSON(String chat) {
		return Transfer.toJSON(new DirDto("#dir", chat, chatRoomName, sender));
	}

	private JSONObject createFileJSON(String chat) {
		System.out.println("createFileMessage");
		final String[] message = getMessageSplit(chat);
		command = message[0];
		fileName = message[1];

		boolean result = fileMessage();

		return result
				? Transfer.toJSON(new ChatDto("chat", fileName + " 파일 전송", sender))
				: Transfer.toJSON(new PrivateChatDto("privateChat", fileName + " 파일 전송 취소", sender, "privateMan"));
	}

	public boolean fileMessage() {
		createThreadGroupInstance();
		JSONObject json = getChatInfoJSON();

		try {
			return new FileMessage().client(json);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private JSONObject getChatInfoJSON() {
		JSONObject json = new JSONObject();
		json.put("threadGroup", threadGroup);
		json.put("chatInfo", new ChatInfo(command, fileName, this.chatRoomName));
		return json;
	}

	private void createThreadGroupInstance() {
		if(threadGroup == null){
			threadGroup = new ThreadGroup(sender.getUserId()+chatRoomName);
		}
	}

	public String[] getMessageSplit(String chat){
		String[] message = chat.split(" ");
		if(message.length == 1){
			message = new String[3];
			message[0] = chat;
			message[1] = "temp";
		}
		return message;
	}
}
