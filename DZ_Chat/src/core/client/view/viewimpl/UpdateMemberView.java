package core.client.view.viewimpl;

import java.util.Iterator;

import org.json.JSONObject;

import core.client.ClientMap;
import core.client.view.TextInputView;
import core.client.view.View;
import core.client.view.ViewMap;
import static core.client.Main.*;

public class UpdateMemberView extends TextInputView {
	private String validatePW;
	private String newPW;
	public UpdateMemberView() {
		super("UpdateMember", "기존 비밀번호 입력", "새로운 비밀번호 입력");
	}

	@Override
	public View nextView() {
		Iterator<String> answerIter = answerIterator();
		validatePW = answerIter.next();
		newPW = answerIter.next();
		JSONObject response = ClientMap.runClient("member.UpdatePWClient", getMe(), validatePW, newPW);
		return ViewMap.getView("SuccessLogin");
		// return View.getView("UserInfo"); // Fail
	}

}
