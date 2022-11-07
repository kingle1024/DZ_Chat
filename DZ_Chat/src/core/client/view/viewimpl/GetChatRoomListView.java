package core.client.view.viewimpl;

import org.json.JSONObject;

import core.client.ClientMap;
import core.client.view.View;
import core.client.view.ViewMap;

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
