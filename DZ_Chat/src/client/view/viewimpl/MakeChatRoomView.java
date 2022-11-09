package client.view.viewimpl;

import org.json.JSONObject;

import client.ClientMap;
import client.view.TextInput;
import client.view.View;
import client.view.ViewMap;

public class MakeChatRoomView implements View {
	private String chatRoomName;
	private TextInput textInput;
	
	public MakeChatRoomView() {
		textInput = new TextInput("만들 채팅방 이름");
	}

	@Override
	public View nextView() {
		textInput.init();
		chatRoomName = textInput.next();
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
