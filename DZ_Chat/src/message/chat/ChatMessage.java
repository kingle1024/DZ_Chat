package message.chat;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import core.server.Server;
import member.Member;

public class ChatMessage extends Message {
	private static final long serialVersionUID = -4472963080600091036L;
	private final Member sender;
	private final String message;
	
	public ChatMessage(ChatRoom chatRoom, Member sender, String message) {
		super(chatRoom);
		// String
//		ChatRoom chatroom = Server.chatRoomMap.
		System.out.println("chatRoom size: " + chatRoom.size());
		this.sender = sender;
		this.message = message;
	}
	
	@Override
	public void send(OutputStream os) throws IOException {
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(os));
		dos.writeUTF(message);
		dos.flush();
	}
	
	@Override
	public void push() {
		System.out.println("PUSH: " + chatRoom.size());
		super.chatRoom.getChatServiceList().stream().forEach(s -> {
			try {
				System.out.println("HERE");
				send(s.getSocket().getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		System.out.println("Push END");
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append(sender.toString())
				.append("> ")
				.append(message)
				.toString();
	}


}
