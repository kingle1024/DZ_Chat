package client.view.viewimpl;

import static client.Main.*;

import client.view.MenuChoose;
import client.view.View;

public class UserInfoView implements View {
	private MenuChoose menuChoose;

	public UserInfoView() {
		menuChoose = new MenuChoose("UpdatePW", "DeleteMember");
	}

	@Override
	public View nextView() {
		System.out.println(getMe());
		return menuChoose.choose();
	}
	
	@Override
	public String getViewName() {
		return "회원 정보";
	}
}
