package core.client.view.viewimpl;

import java.util.Iterator;

import core.client.member.FindPWClient;
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
//		ClientMap.runClient("member.FindPWClient", id);
		new FindPWClient(id).run();
		return ViewMap.getView("Main");
	}
}
