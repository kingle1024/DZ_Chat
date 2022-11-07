package core.service.serviceimpl.member;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.json.JSONObject;
import core.service.Service;
import log.Log;
import log.LogQueue;
import member.Member;
import member.MemberManager;
import property.Property;

public class DeleteMemberService extends Service {
	private static final MemberManager memberManager = MemberManager.getInstance();

	@Override
	public void request() throws IOException {
		JSONObject receiveJSON = receive();
		Member me = Member.parseJSON(receiveJSON.getJSONObject("member"));
		String pw = receiveJSON.getString("pw");
		
		JSONObject sendJSON = new JSONObject();
		sendJSON.put("result", memberManager.delete(me, pw));
		send(sendJSON);
		LogQueue.add(toLog());
	}

	public Log toLog() {
		return new Log(Property.server().get("CHAT_LOG_FILE"), "Delete MemberData");
	}
}
