package client.view.viewimpl;

import static client.Main.*;

import org.json.JSONObject;

import client.ClientMap;
import client.view.TextInput;
import client.view.View;
import client.view.ViewMap;
import member.Member;

public class LoginView implements View {
	private String id;
	private String pw;
	private TextInput textInput;
	public LoginView() {
		textInput = new TextInput("id", "pw");
	}

	public View nextView() {
		textInput.init();
		id = textInput.next();
		pw = textInput.next();
		JSONObject response = ClientMap.runClient("member.LoginClient", id, pw);
		
		boolean hasMember = response.has("member");
		Member me = hasMember
				? Member.parseJSON(new JSONObject(response.getString("member")))
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
