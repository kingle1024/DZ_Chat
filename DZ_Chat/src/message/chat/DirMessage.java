package message.chat;

import java.io.IOException;
import java.io.ObjectOutputStream;

import member.Member;
import message.ftp.FtpService;

public class DirMessage extends Message {
	private static final long serialVersionUID = 1326688109607339081L;
	private final Member sender;
	private final String message;
	private final String chatRoomName;
	public DirMessage(String chatRoomName, Member sender, String message) {
		this.chatRoomName = chatRoomName;
		this.sender = sender;
		this.message = message;
	}

	@Override
	public void send(ObjectOutputStream oos) throws IOException {
		FtpService ftp = new FtpService();

		oos.writeObject(ftp.dir(String.valueOf(chatRoom.hashCode())));
		System.out.println("[System] "+ftp.dir(chatRoomName));
		oos.flush();
	}

	@Override
	public void push() {
		System.out.println("message push: " + message);
		System.out.println("chatRoom: " + chatRoom);
		chatRoom.getChatServiceList().forEach(s -> {
			try {
				System.out.println(s.getMe());
				send(s.getOs());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}
