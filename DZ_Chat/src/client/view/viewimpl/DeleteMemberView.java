package client.view.viewimpl;

import static client.Main.*;

import java.util.Iterator;

import org.json.JSONObject;

import client.ClientMap;
import client.view.TextInputView;
import client.view.View;
import client.view.ViewMap;

public class DeleteMemberView extends TextInputView {
	private String pw;

	public DeleteMemberView() {
		super("pw");
	}

	@Override
	public View nextView() {
		Iterator<String> answerIterator = answerIterator();
		pw = answerIterator.next();
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
