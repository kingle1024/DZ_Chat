package server.service.serviceimpl.member;

import java.io.IOException;

import org.json.JSONObject;

import log.Log;
import log.LogQueue;
import member.Member;
import member.MemberManager;
import member.MemberMap;
import property.ServerProperties;
import server.service.Service;

public class UpdatePWService extends Service {
	private Member member;
	private String validatePW;
	private String newPW;

	@Override
	public void request() throws IOException {
		try {
			JSONObject receiveJSON = receive();
			System.out.println("receiveJSON: " + receiveJSON);
			member = Member.parseJSON(receiveJSON.getJSONObject("member"));
			validatePW = receiveJSON.getString("validatePW");
			newPW = receiveJSON.getString("newPW");
			
			JSONObject sendJSON = new JSONObject();
			sendJSON.put("success", MemberManager.updatePw(member, validatePW, newPW));
			sendJSON.put("member", MemberMap.get(member.getUserId()).getJSON());
			System.out.println(sendJSON);
			send(sendJSON);
			System.out.println("UpdatePWService: " + sendJSON);
			LogQueue.add(toLog());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Log toLog() {
		return new Log(ServerProperties.getChatLogFile(), "Register Success");
	}
}
