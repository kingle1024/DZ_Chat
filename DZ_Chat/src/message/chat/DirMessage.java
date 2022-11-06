package message.chat;

import java.io.IOException;
import java.io.ObjectOutputStream;

import log.Log;
import member.Member;
import message.ftp.FtpService;
import property.Property;

public class DirMessage extends Message {
	private static final long serialVersionUID = 1326688109607339081L;
	private final Member sender;
	private final String chatRoomName;
	public DirMessage(String chatRoomName, Member sender, String message) {
		super(message);
		this.chatRoomName = chatRoomName;
		this.sender = sender;
	}

	public void send(ObjectOutputStream oos) throws IOException {
		FtpService ftp = new FtpService();

		oos.writeObject(ftp.dir(chatRoomName));
		System.out.println("[System] "+ftp.dir(chatRoomName));
		oos.flush();
	}

	@Override
	public void push() {
		
		chatRoom.getChatServices().forEach(s -> {
			try {
				System.out.println(s.getMe());
				send(s.getOs());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	@Override
	public Log toLog() {
		String DirMessage = "Dir:" + sender.getUserId() + ":" + message;
		return new Log(Property.server().get("DIR_LOG_FILE"), DirMessage);
	}

}
