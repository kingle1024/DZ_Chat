package core.client.view.viewimpl;

import java.util.Iterator;

import org.json.JSONObject;

import core.client.ClientMap;
import core.client.view.TextInputView;
import core.client.view.View;
import core.client.view.ViewMap;
import static core.client.Main.*;

public class EntranceChatRoomView extends TextInputView {
	private String chatRoomName;
	
	public EntranceChatRoomView() {
		super("EntranceChatRoom", "입장할 채팅방 이름을 입력하세요.");
	}

	@Override
	public View nextView() {
		Iterator<String> answerIterator = answerIterator();
		chatRoomName = answerIterator.next();
		JSONObject hasChatRoomJSON = ClientMap.runClient("chat.HasChatRoomClient", chatRoomName);
		boolean check = true;
		if (check) {
			ClientMap.runClient("chat.ChatClient", chatRoomName, getMe());
		} else {
			System.out.println("존재하지 않는 채팅방 입니다.");
		}
		return ViewMap.getView("SuccessLogin");
	}
}
