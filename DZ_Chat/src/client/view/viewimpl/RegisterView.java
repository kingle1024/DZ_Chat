package client.view.viewimpl;

import org.json.JSONObject;

import client.ClientMap;
import client.view.TextInput;
import client.view.View;
import client.view.ViewMap;
import member.Member;

public class RegisterView implements View {
	private TextInput textInput;
	private String id;
	private String pw;
	private String pwChk;
	private String name;
	private String birth;
	
	public RegisterView() {
		textInput = new TextInput("id", "pw", "pwChk", "name", "birth");
	}

	@Override
	public View nextView() {
		textInput.init();
		id = textInput.next();
		pw = textInput.next();
		pwChk = textInput.next();
		name = textInput.next();
		birth = textInput.next();
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