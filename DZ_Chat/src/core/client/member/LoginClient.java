package core.client.member;

import java.io.*;

import org.json.JSONObject;

import core.client.Client;
import core.client.mapper.RequestType;
import member.Member;
import static core.client.Main.*;

public class LoginClient extends Client {
	private String id;
	private String pw;
	private JSONObject loginJSON;
	
	public LoginClient(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}

	@Override
	public JSONObject run() {
		try {
			loginJSON = new JSONObject();
			loginJSON.put("id", id);
			loginJSON.put("pw", pw);
			
			connect(new RequestType("member.LoginService"));
			send(loginJSON);
			JSONObject response = receive();
			unconnect();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
