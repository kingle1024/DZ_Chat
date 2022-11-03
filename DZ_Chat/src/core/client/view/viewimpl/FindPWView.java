package core.client.view.viewimpl;

import java.util.Iterator;

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
		ClientMap.runClient("member.FindPWClient", id);
		return ViewMap.getView("Main");
	}
}
