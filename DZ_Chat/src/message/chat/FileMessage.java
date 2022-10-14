package message.chat;

import java.io.IOException;
import java.io.OutputStream;

public class FileMessage extends Message{

	private static final long serialVersionUID = -4901402128143853344L;

	public FileMessage(ChatRoom chatRoomName) {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void send(OutputStream os) throws IOException {
		
	}

	public void sendAll(OutputStream os) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void push() {
		// TODO Auto-generated method stub
		
	}

}
