package core.client.view.viewimpl;

import java.util.Iterator;

import org.json.JSONObject;

import core.client.ClientMap;
import core.client.view.TextInputView;
import core.client.view.View;
import core.client.view.ViewMap;

public class FindPWView extends TextInputView {
	private String id;
	
	public FindPWView() {
		super("FindPW", "id");
	}

	@Override
	public View nextView() {
		Iterator<String> answerIterator = answerIterator();
		id = answerIterator.next();
		JSONObject response = ClientMap.runClient("member.FindPWClient", id);
		if (response.getBoolean("success")) {
			System.out.println("비밀번호: " + response.getString("findPW"));
		} else {
			System.out.println("존재하지 않는 ID 입니다.");
		}
		
		return ViewMap.getView("Main");
	}
}
