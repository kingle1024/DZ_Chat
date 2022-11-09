package client.view.viewimpl;

import static client.Main.*;

import java.util.Iterator;

import org.json.JSONObject;

import client.ClientMap;
import client.view.TextInputView;
import client.view.View;
import client.view.ViewMap;
import member.Member;

public class UpdatePWView extends TextInputView {
	private String validatePW;
	private String newPW;
	public UpdatePWView() {
		super("기존 비밀번호 입력", "새로운 비밀번호 입력");
	}

	@Override
	public View nextView() {
		Iterator<String> answerIter = answerIterator();
		validatePW = answerIter.next();
		newPW = answerIter.next();
		JSONObject response = ClientMap.runClient("member.UpdatePWClient", validatePW, newPW);
		if (response.getBoolean("success")) {
			System.out.println("비밀번호 변경 성공");
			setMe(Member.parseJSON(response.getJSONObject("member")));
			return ViewMap.getView("SuccessLogin");	
		} else {
			System.out.println("비밀번호 변경 실패");
			return ViewMap.getView("UserInfo");
		}
	}

	@Override
	public String getViewName() {
		return "비밀번호 변경";
	}

}
