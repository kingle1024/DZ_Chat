package core.client.view.viewimpl;

import core.client.chat.GetChatRoomListClient;
import core.client.view.View;
import core.client.view.ViewMap;

public class GetChatRoomListView extends View {

	public GetChatRoomListView() {
		super("GetChatRoomList");
	}

	@Override
	public View nextView() {
//		ClientMap.runClient("chat.GetChatRoomListClient");
		new GetChatRoomListClient().run();
		return ViewMap.getView("SuccessLogin");
	}

}
