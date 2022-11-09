package client.view.viewimpl;

import static client.Main.*;

import org.json.JSONObject;

import client.ClientMap;
import client.view.TextInput;
import client.view.View;
import client.view.ViewMap;

public class DeleteMemberView implements View {
	private String pw;
	private TextInput textInput;
	public DeleteMemberView() {
		textInput = new TextInput("pw");
	}

	@Override
	public View nextView() {
		textInput.init();
		pw = textInput.next();
		JSONObject response = ClientMap.runClient("member.DeleteMemberClient", pw);
		if (response.getBoolean("result")) {
			setMe(null);
			return ViewMap.getView("Main");
		} else {
			return ViewMap.getView("UserInfo");
		}
	}
	
	@Override
	public String getViewName() {
		return "회원 삭제";
	}
}
