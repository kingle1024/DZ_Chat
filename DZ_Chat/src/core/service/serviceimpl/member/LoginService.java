package core.service.serviceimpl.member;

import java.io.*;

import org.json.JSONObject;

import core.service.Service;
import log.Log;
import log.LogQueue;
import member.*;
import property.Property;

public class LoginService extends Service implements NeedLog {
	private static final MemberManager memberManager = MemberManager.getInstance();
	private LogQueue logQueue = LogQueue.getInstance();

	@Override
	public void request() throws IOException {
		System.out.println("Login Service");
		try {
			JSONObject loginJSON = receive();
			String id = loginJSON.getString("id");
			String pw = loginJSON.getString("pw");
			System.out.println("id: " + id + ", pw: " + pw);
			Member member = memberManager.login(id, pw);
			JSONObject sendJSON = new JSONObject();
			sendJSON.put("hasMember", member != null);
			if (member != null) {
				logQueue.add(this);
				sendJSON.put("member", member.getJSON());
			}
			send(sendJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public Log toLog() {
		return new Log(Property.server().get("CHAT_LOG_FILE"), "id : " + this.id + " Login Success");
	}

}
