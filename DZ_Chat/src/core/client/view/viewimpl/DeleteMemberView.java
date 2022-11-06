package core.client.view.viewimpl;

import java.util.Iterator;

import org.json.JSONObject;

import core.client.ClientMap;
import core.client.member.DeleteMemberClient;
import core.client.view.TextInputView;
import core.client.view.View;
import core.client.view.ViewMap;
import static core.client.Main.*;

public class DeleteMemberView extends TextInputView {
	private String pw;

	public DeleteMemberView() {
		super("DeleteMember", "pw");
		
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
}
