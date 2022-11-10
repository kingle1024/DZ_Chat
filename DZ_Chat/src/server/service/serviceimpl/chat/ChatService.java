package server.service.serviceimpl.chat;

import java.io.IOException;
import java.util.Objects;

import org.json.JSONObject;

import member.Member;
import message.MessageFactory;
import message.chat.ChatRoom;
import message.chat.Message;
import server.MainServer;
import server.service.Service;

public class ChatService extends Service {
	private Member me;
	private String chatRoomName;
	private ChatRoom chatRoom;

	@Override
	public void request() {
		init();
		System.out.println("Chat Service");
		MainServer.threadPool.execute(() -> {
			try {
				while (true) {
					JSONObject messageJSON = receive();
					Message message = MessageFactory.create(this, messageJSON);
					message.push();
				}
			} catch (Exception e) {
				chatRoom.exit(this);
				System.out.println("ChatService > IOException > " + e);
			}
		});
	}

	private void init() {
		try {
			JSONObject initData = receive();
			chatRoomName = initData.getString("chatRoomName");
			me = Member.parseJSON(initData.getJSONObject("me"));
			chatRoom = MainServer.chatRoomMap.get(chatRoomName);
			chatRoom.entrance(this);
			System.out.println(chatRoom.getChatRoomName());
			System.out.println(chatRoom.getChatServices());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Member getMe() {
		return me;
	}

	public String nickname() {
		return me.nickname();
	}

	public boolean equalsUser(String id) {
		return me.getUserId().equals(id);
	}

	public ChatRoom getChatRoom() {
		return chatRoom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(chatRoomName, me);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatService other = (ChatService) obj;
		return Objects.equals(chatRoomName, other.chatRoomName) && Objects.equals(me, other.me);
	}
}
