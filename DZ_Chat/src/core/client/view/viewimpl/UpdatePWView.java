package core.client.view.viewimpl;

import java.util.Iterator;

import org.json.JSONObject;

import core.client.ClientMap;
import core.client.member.UpdatePWClient;
import core.client.view.TextInputView;
import core.client.view.View;
import core.client.view.ViewMap;
import static core.client.Main.*;

public class UpdatePWView extends TextInputView {
	private String validatePW;
	private String newPW;
	public UpdatePWView() {
		super("UpdatePW", "기존 비밀번호 입력", "새로운 비밀번호 입력");
	}

	@Override
	public View nextView() {
		Iterator<String> answerIter = answerIterator();
		validatePW = answerIter.next();
		newPW = answerIter.next();
		JSONObject response = ClientMap.runClient("member.UpdatePWClient", getMe(), validatePW, newPW);
		if (response.getBoolean("success")) {
			return ViewMap.getView("SuccessLogin");	
		} else {
			return ViewMap.getView("UserInfo");
		}
		
		
	}

}
