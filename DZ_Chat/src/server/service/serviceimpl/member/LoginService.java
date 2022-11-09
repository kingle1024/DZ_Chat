package server.service.serviceimpl.member;

import java.io.*;

import org.json.JSONObject;

import log.Log;
import log.LogQueue;
import member.*;
import property.ServerProperties;
import server.service.Service;

public class LoginService extends Service {
	private String id;
	private String pw;
	
	@Override
	public void request() throws IOException {
		System.out.println("Login Service");
		try {
			JSONObject loginJSON = receive();
			id = loginJSON.getString("id");
			pw = loginJSON.getString("pw");
			System.out.println("id: " + id + ", pw: " + pw);
			Member member = MemberManager.login(id, pw);
			JSONObject sendJSON = new JSONObject();
			sendJSON.put("hasMember", member != null);
			if (member != null) {
				LogQueue.add(toLog());
				sendJSON.put("member", member.getJSON());
			}
			send(sendJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public Log toLog() {
		return new Log(ServerProperties.getChatLogFile(), "id : " + this.id + " Login Success");
	}

}
