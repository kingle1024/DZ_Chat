package client.view.viewimpl;

import org.json.JSONObject;

import client.ClientMap;
import client.view.TextInput;
import client.view.View;
import client.view.ViewMap;

public class EntranceChatRoomView implements View {
	private String chatRoomName;
	private TextInput textInput;
	public EntranceChatRoomView() {
		textInput = new TextInput("입장할 채팅방 이름");
	}

	@Override
	public View nextView() {
		textInput.init();
		chatRoomName = textInput.next();
		JSONObject hasChatRoomJSON = ClientMap.runClient("chat.HasChatRoomClient", chatRoomName);
		if (hasChatRoomJSON.getBoolean("result")) {
			ClientMap.runClient("chat.ChatClient", chatRoomName);
		} else {
			System.out.println("존재하지 않는 채팅방 입니다.");
		}
		return ViewMap.getView("SuccessLogin");
	}

	@Override
	public String getViewName() {
		return "채팅방 입장";
	}
}
