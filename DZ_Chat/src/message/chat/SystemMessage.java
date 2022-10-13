package message.chat;

public class SystemMessage extends Message {
	private final String message;
	
	public SystemMessage(ChatRoom chatRoom, String message) {
		super(chatRoom);
		this.message = message;
	}
	
	@Override
	public void send() {
		// TODO Auto-generated method stub
		
	}

}
