package message.chat;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import core.server.Server;
import member.Member;

public class ChatMessage extends Message {
	private final Member sender;
	private final String message;
	
	public ChatMessage(ChatRoom chatRoom, Member sender, String message) {
		super(chatRoom);
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
		System.out.println(this);
		chatRoom.getChatServiceList().stream().forEach(s -> {
			try {
				System.out.println(s.getMe());
				send(s.getSocket().getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
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
