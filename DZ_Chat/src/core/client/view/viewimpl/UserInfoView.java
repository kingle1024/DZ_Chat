package core.client.view.viewimpl;

import core.client.view.MenuChooseView;
import core.client.view.View;
import static core.client.Main.*;

public class UserInfoView extends MenuChooseView {

	public UserInfoView() {
		super("UserInfo", "회원 비밀번호 수정", "탈퇴");
	}

	@Override
	public View nextView() {
		System.out.println(getMe());
		return super.nextView();
	}
}
