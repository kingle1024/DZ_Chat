package core.client.view.viewimpl;


import java.util.Iterator;

import org.json.JSONObject;

import core.client.ClientMap;
import core.client.view.TextInputView;
import core.client.view.View;
import core.client.view.ViewMap;
import member.Member;

public class RegisterView extends TextInputView {
	public RegisterView() {
		super("id", "pw", "pwChk", "name", "birth");
	}

	@Override
	public View nextView() {
		Iterator<String> answerIterator = answerIterator();
		String id = answerIterator.next();
		String pw = answerIterator.next();
		String pwChk = answerIterator.next();
		String name = answerIterator.next();
		String birth = answerIterator.next();
		Member tmp = new Member(id, pw, name, birth);
		JSONObject response = ClientMap.runClient("member.RegisterClient", tmp, pwChk);
		System.out.println(response.getBoolean("success") ? "회원가입 성공" : "회원가입 실패");
		return ViewMap.getView("Main");
	}

	@Override
	public String getViewName() {
		return "회원가입";
	}
}