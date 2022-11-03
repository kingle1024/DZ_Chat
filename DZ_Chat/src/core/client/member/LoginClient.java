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
	private JSONObject json = new JSONObject();
	
	public LoginClient(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}

	@Override
	public JSONObject run() {
		try {
			json.put("id", id);
			json.put("pw", pw);
			connect(new RequestType("member.LoginService"));
			JSONObject response = receive();
			unconnect();
//			setMe(response -> me);
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
