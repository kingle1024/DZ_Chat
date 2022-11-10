package server.service.serviceimpl.member;

import java.io.IOException;

import org.json.JSONObject;

import log.Log;
import log.LogQueue;
import member.MemberManager;
import property.ServerProperties;
import server.service.Service;

public class FindPWService extends Service {

	@Override
	public void request() throws IOException {
		String id = receive().getString("id");
		String findPW = MemberManager.findPw(id);
		JSONObject sendJSON = new JSONObject();
		sendJSON.put("success", findPW != null);
		sendJSON.put("findPW", findPW);
		send(sendJSON);
		LogQueue.add(toLog());
	}

	public Log toLog() {
		return new Log(ServerProperties.getChatLogFile(), "Find Pw");
	}

}
