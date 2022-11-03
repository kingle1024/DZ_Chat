package core.client.view.viewimpl;

import java.util.Iterator;

import core.client.ClientMap;
import core.client.view.TextInputView;
import core.client.view.View;
import core.client.view.ViewMap;

public class MakeChatRoomView extends TextInputView {
	private String chatRoomName;
	public MakeChatRoomView() {
		super("MakeChatRoom", "만들 채팅방 이름을 입력하세요.");
	}

	@Override
	public View nextView() {
		Iterator<String> answerIterator = answerIterator();
		chatRoomName = answerIterator.next();
		ClientMap.runClient("chat.MakeChatRoomClient", chatRoomName);
		return ViewMap.getView("SuccessLogin");
	}

}
