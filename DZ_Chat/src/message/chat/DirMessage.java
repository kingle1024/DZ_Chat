package message.chat;

import java.io.IOException;

import core.service.serviceimpl.chat.ChatService;
import log.Log;
import member.Member;
import message.ftp.FtpService;
import org.json.JSONObject;
import property.ServerProperties;

public class DirMessage implements Message {
	private final ChatService chatService;

	public DirMessage(ChatService chatService) {
		this.chatService = chatService;
	}

	@Override
	public void push() {
		FtpService ftp = new FtpService();
		String chatRoomName = chatService.getChatRoom().getChatRoomName();
		JSONObject json = new JSONObject();
		json.put("message", ftp.dir(chatRoomName));
		try {
			chatService.send(json);
		}catch (IOException exception){
			exception.printStackTrace();
		}
	}
	
	public Log toLog() {
		String sender = chatService.getMe().getUserId();
		String message = "dir 명령어 호출함";
		String DirMessage = "Dir:" + sender + ":" + message;
		return new Log(ServerProperties.getDirLogFile(), DirMessage);
	}

}
