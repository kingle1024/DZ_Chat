package core.service.serviceimpl.member;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.json.JSONObject;

import core.service.Service;
import log.Log;
import log.LogQueue;
import member.MemberManager;
import property.ServerProperties;

public class FindPWService extends Service {
	private String id = null;
	
	@Override
	public void request() throws IOException {
		id = receive().getString("id");
		String findPW = MemberManager.findPw(id);
		JSONObject sendJSON = new JSONObject();
		sendJSON.put("success", findPW != null);
		sendJSON.put("findPW", findPW);
		send(sendJSON);
		LogQueue.add(toLog());	
	}

	public Log toLog() {
		return new Log(ServerProperties.getChatLogFile(), "FindPw " + this.id);
	}

}
