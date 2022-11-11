package server.service.serviceimpl.member;

import java.io.*;

import org.json.JSONObject;

import dto.Transfer;
import dto.member.LoginDto;
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
			Member member = MemberManager.login(id, pw);
			JSONObject sendJSON = Transfer.toJSON(new LoginDto.Response(member != null, member));
			if (member != null) {
				LogQueue.add(toLog());
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
