package core.client.view.viewimpl;

import java.util.Iterator;

import core.client.ClientMap;
import core.client.view.TextInputView;
import core.client.view.View;
import core.client.view.ViewMap;

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
		ClientMap.runClient("member.LoginClient", id, pw);
		return ViewMap.getView("SuccessLogin");
	}
}
