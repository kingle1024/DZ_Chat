package server.service.serviceimpl.member;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.json.JSONObject;

import log.Log;
import log.LogQueue;
import member.Member;
import member.MemberManager;
import property.ServerProperties;
import server.service.Service;

public class DeleteMemberService extends Service {

	@Override
	public void request() throws IOException {
		JSONObject receiveJSON = receive();
		Member me = Member.parseJSON(receiveJSON.getJSONObject("member"));
		String pw = receiveJSON.getString("pw");
		
		JSONObject sendJSON = new JSONObject();
		sendJSON.put("result", MemberManager.deleteMember(me, pw));
		send(sendJSON);
		LogQueue.add(toLog());
	}

	public Log toLog() {
		return new Log(ServerProperties.getChatLogFile(), "Delete MemberData");
	}
}
