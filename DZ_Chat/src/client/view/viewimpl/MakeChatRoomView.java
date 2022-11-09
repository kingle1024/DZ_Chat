package client.view.viewimpl;

import java.util.Iterator;

import org.json.JSONObject;

import client.ClientMap;
import client.view.TextInputView;
import client.view.View;
import client.view.ViewMap;

public class MakeChatRoomView extends TextInputView {
	private String chatRoomName;
	
	public MakeChatRoomView() {
		super("만들 채팅방 이름을 입력하세요.");
	}

	@Override
	public View nextView() {
		Iterator<String> answerIterator = answerIterator();
		chatRoomName = answerIterator.next();
		JSONObject hasChatRoom = ClientMap.runClient("chat.HasChatRoomClient", chatRoomName);
		if (!hasChatRoom.getBoolean("result")) {
			ClientMap.runClient("chat.MakeChatRoomClient", chatRoomName);	
		}
		return ViewMap.getView("SuccessLogin");
	}

	@Override
	public String getViewName() {
		return "채팅방 만들기";
	}
}
