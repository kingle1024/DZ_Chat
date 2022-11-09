package client.view.viewimpl;

import org.json.JSONObject;

import client.ClientMap;
import client.view.View;
import client.view.ViewMap;

public class GetChatRoomListView implements View {

	@Override
	public View nextView() {
		JSONObject response = ClientMap.runClient("chat.GetChatRoomListClient");
		response.getJSONArray("chatRoomList").toList().forEach(chatRoomName -> System.out.println("[" + chatRoomName + "]"));
		return ViewMap.getView("SuccessLogin");
	}

	@Override
	public String getViewName() {
		return "채팅방 리스트";
	}
}
