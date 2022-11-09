package client.view.viewimpl;

import static client.Main.*;

import java.util.Iterator;

import org.json.JSONObject;

import client.ClientMap;
import client.view.TextInputView;
import client.view.View;
import client.view.ViewMap;
import member.Member;

public class LoginView extends TextInputView {
	private String id;
	private String pw;
	
	public LoginView() {
		super("id", "pw");
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

	@Override
	public String getViewName() {
		return "로그인";
	}
}
