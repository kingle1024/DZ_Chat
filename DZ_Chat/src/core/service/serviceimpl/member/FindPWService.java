package core.service.serviceimpl.member;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.json.JSONObject;

import core.service.Service;
import log.Log;
import log.LogQueue;
import member.MemberManager;
import property.Property;

public class FindPWService extends Service {
	private static final MemberManager memberManager = MemberManager.getInstance();

	@Override
	public void request() throws IOException {
		String id = receive().getString("id");
		String findPW = memberManager.findPw(id);
		JSONObject sendJSON = new JSONObject();
		sendJSON.put("success", findPW != null);
		sendJSON.put("findPW", findPW);
		send(sendJSON);
		LogQueue.add(toLog());	
	}

	public Log toLog() {
		return new Log(Property.server().get("CHAT_LOG_FILE"), "Find Pw");
	}

}
