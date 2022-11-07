package core.client.view.viewimpl;

import core.client.view.MenuChooseView;
import core.client.view.View;
import static core.client.Main.*;

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
