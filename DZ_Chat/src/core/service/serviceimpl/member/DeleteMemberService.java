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
import property.ServerProperties;

public class DeleteMemberService extends Service {
	private Member me = null;
	
	@Override
	public void request() throws IOException {
		JSONObject receiveJSON = receive();
		me = Member.parseJSON(receiveJSON.getJSONObject("member"));
		String pw = receiveJSON.getString("pw");
		
		JSONObject sendJSON = new JSONObject();
		sendJSON.put("result", MemberManager.deleteMember(me, pw));
		send(sendJSON);
		LogQueue.add(toLog());
	}

	public Log toLog() {
		return new Log(ServerProperties.getChatLogFile(), "Delete " + me.getUserId());
	}
}
