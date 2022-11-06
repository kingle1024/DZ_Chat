package core.client.view.viewimpl;

import java.util.Iterator;

import org.json.JSONObject;

import core.client.member.LoginClient;
import core.client.view.TextInputView;
import core.client.view.View;
import core.client.view.ViewMap;
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
//		JSONObject response = ClientMap.runClient("member.LoginClient", id, pw);
//		boolean success = false; // response -> success;
//		return success ? ViewMap.getView("SuccessLogin") : ViewMap.getView("Main");
	
		LoginClient loginClient = new LoginClient(id, pw);
		loginClient.run();
		if (loginClient.getMember() != null) {
			setMe(loginClient.getMember());
			return ViewMap.getView("SuccessLogin");
		} else {
			return ViewMap.getView("Main");
		}
	}
}
