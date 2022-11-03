package core.client.view.viewimpl;


import java.util.Iterator;

import core.client.ClientMap;
import core.client.view.TextInputView;
import core.client.view.View;
import core.client.view.ViewMap;
import member.Member;

public class RegisterView extends TextInputView {
	private String id;
	private String pw;
	private String pwChk;
	private String name;
	private String birth;
	public RegisterView() {
		super("Register", "id", "pw", "pwChk", "name", "birth");
	}

	@Override
	public View nextView() {
		Iterator<String> answerIterator = answerIterator();
		id = answerIterator.next();
		pw = answerIterator.next();
		pwChk = answerIterator.next();
		name = answerIterator.next();
		birth = answerIterator.next();
		Member tmp = new Member(id, pw, name, birth);
		ClientMap.runClient("member.register", tmp, pwChk);
		return ViewMap.getView("Main");
	}
}