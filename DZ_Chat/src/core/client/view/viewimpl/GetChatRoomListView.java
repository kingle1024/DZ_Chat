package core.client.view.viewimpl;

import org.json.JSONObject;

import core.client.ClientMap;
import core.client.chat.GetChatRoomListClient;
import core.client.view.View;
import core.client.view.ViewMap;

public class GetChatRoomListView extends View {

	public GetChatRoomListView() {
		super("GetChatRoomList");
	}

	@Override
	public View nextView() {
		JSONObject response = ClientMap.runClient("chat.GetChatRoomListClient");
		response.getJSONArray("chatRoomList").toList().forEach(chatRoomName -> System.out.println("[" + chatRoomName + "]"));
		return ViewMap.getView("SuccessLogin");
	}
}
