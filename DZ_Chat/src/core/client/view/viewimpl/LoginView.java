package core.client.view.viewimpl;

import java.util.Iterator;

import org.json.JSONObject;

import core.client.ClientMap;
import core.client.view.TextInputView;
import core.client.view.View;
import core.client.view.ViewMap;
import member.Member;

import static core.client.Main.*;

public class LoginView extends TextInputView {
	private String id;
	private String pw;
	public LoginView() {
		super("Login", "id", "pw");
	}

	public View nextView() {
		Iterator<String> answerIterator = answerIterator();
		id = answerIterator.next();
		pw = answerIterator.next();
		JSONObject response = ClientMap.runClient("member.LoginClient", id, pw);
		boolean hasMember = response.getBoolean("hasMember");
		Member me = hasMember
				? Member.parseJSON(response.getJSONObject("member"))
				: null;
		setMe(me);
		System.out.println(hasMember ? "로그인 성공" : "로그인 실패");
		return hasMember ? ViewMap.getView("SuccessLogin") : ViewMap.getView("Main");
	
	}
}
