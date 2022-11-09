package client.view.viewimpl;

import client.view.MenuChoose;
import client.view.View;

public class SuccessLoginView implements View {
	private MenuChoose menuChoose;
	
	public SuccessLoginView() {
		menuChoose = new MenuChoose(
				"UserInfo",
				"GetChatRoomList",
				"EntranceChatRoom",
				"MakeChatRoom");
	}

	@Override
	public View nextView() {
		return menuChoose.choose();
		
	}

	@Override
	public String getViewName() {
		return "로그인 성공";
	}
}
