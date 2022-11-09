package client.view.viewimpl;

import static client.Main.*;

import client.view.MenuChooseView;
import client.view.View;

public class UserInfoView extends MenuChooseView {

	public UserInfoView() {
		super("UserInfo", "UpdatePW", "DeleteMember");
	}

	@Override
	public View nextView() {
		System.out.println(getMe());
		return super.nextView();
	}
	
	@Override
	public String getViewName() {
		return "회원 정보";
	}
}
