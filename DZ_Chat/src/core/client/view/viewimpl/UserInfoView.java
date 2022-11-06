package core.client.view.viewimpl;

import core.client.view.MenuChooseView;
import core.client.view.View;
import static core.client.Main.*;

public class UserInfoView extends MenuChooseView {

	public UserInfoView() {
		super("UserInfo", "UpdateMember", "DeleteMember");
	}

	@Override
	public View nextView() {
		System.out.println(getMe());
		return super.nextView();
	}
}
