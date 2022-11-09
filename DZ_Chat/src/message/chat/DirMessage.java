package message.chat;

import java.io.IOException;

import log.Log;
import member.Member;
import property.ServerProperties;
import server.service.serviceimpl.chat.ChatService;

public class DirMessage implements Message {
	private final ChatService chatService;
	private final Member sender;
	private final String message;
	
	public DirMessage(ChatService chatService, Member sender, String message) {
		this.chatService = chatService;
		this.sender = sender;
		this.message = message;
	}

	public void send() throws IOException {
		// TODO JSON
//		FtpService ftp = new FtpService();
//		chatService.send(ftp.dir(chatRoomName));
//		System.out.println("[System] "+ftp.dir(chatRoomName));
	}

	@Override
	public void push() {
		chatService.getChatRoom().getChatServices().forEach(s -> {
			try {
				System.out.println(s.getMe());
				send();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	public Log toLog() {
		String DirMessage = "DirMessage " + sender.getUserId();
		return new Log(ServerProperties.getDirLogFile(), DirMessage);
	}

}
