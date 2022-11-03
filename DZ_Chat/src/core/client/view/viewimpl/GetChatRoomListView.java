package core.client.view.viewimpl;

import core.client.ClientMap;
import core.client.view.View;
import core.client.view.ViewMap;

public class GetChatRoomListView extends View {

	public GetChatRoomListView() {
		super("GetChatRoomList");
	}

	@Override
	public View nextView() {
		ClientMap.runClient("chat.GetChatRoomListClient");
		return ViewMap.getView("SuccessLogin");
	}

}
