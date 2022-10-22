package message;

import java.io.IOException;
import java.util.HashMap;

import core.client.chat.ChatRoomExitException;
import member.Member;
import message.chat.ChatMessage;
import message.chat.DirMessage;
import message.chat.FileMessage;
import message.chat.Message;
import message.chat.PrivateChatMessage;

public class MessageFactory {
	private Member sender;
	private String chatRoomName;
	private ThreadGroup threadGroup;
	public MessageFactory(Member sender, String chatRoomName, ThreadGroup threadGroup) {
		this.sender = sender;
		this.chatRoomName = chatRoomName;
		this.threadGroup = threadGroup;
	}
	
	public Message createMessage(String msg) throws IOException, ChatRoomExitException {
		if (msg.startsWith("@")) return createPrivateChatMessage(msg);
		if (msg.startsWith("#exit")) return createExitMessage();
		if (msg.startsWith("#file")) return createFileMessage(msg);
		if (msg.startsWith("#dir")) return createDirMessage(msg);
		return createChatMessage(msg);
	}
	private ChatMessage createChatMessage(String msg) {
		return new ChatMessage(sender, msg);
	}
	
	private PrivateChatMessage createPrivateChatMessage(String msg) {
		int idx = msg.indexOf(' ');
		if (idx == -1 || idx+1 >= msg.length())  {
			System.out.println("형식을 확인해주세요");
			System.out.println("@[보낼 사람 id] [보낼 내용]");
			return null;
		}
		String to = msg.substring(1, idx);
		String message = msg.substring(idx + 1, msg.length());
		return new PrivateChatMessage(sender, message, to);
	}
	
	private Message createExitMessage() throws ChatRoomExitException {
		throw new ChatRoomExitException();
	}
	private Message createFileMessage(String chat) throws IOException {
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
			return new PrivateChatMessage(sender, fileName + " 파일 전송 취소", "privateMan");
		}else{
			return new ChatMessage(sender, fileName + " 파일 전송");
		}
	}
	
	private DirMessage createDirMessage(String chat) {
		return new DirMessage(chatRoomName, sender, chat);
	}
	
	public boolean fileMessage(String chat) throws IOException {
		if(threadGroup == null){
			threadGroup = new ThreadGroup(sender.getUserId()+chatRoomName);
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("chat", chat);
		map.put("chatRoomName", this.chatRoomName);
		map.put("threadGroup", threadGroup);

		FileMessage fileMessage = new FileMessage();
		return fileMessage.run(map);
	}
}
