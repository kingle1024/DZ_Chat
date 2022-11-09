package client.member;

import java.io.IOException;

import org.json.JSONObject;

import client.Client;
import member.Member;

public class RegisterClient extends Client {
	private Member tmpMember;
	private String pwChk;
	private JSONObject json = new JSONObject();
	
	public RegisterClient(Member tmpMember, String pwChk) {
		this.tmpMember = tmpMember;
		this.pwChk = pwChk;
	}

	@Override
	public JSONObject run() {
		try {
			json.put("member", tmpMember.getJSON());
			json.put("pwChk", pwChk);
			connect("member.RegisterService");
			send(json);
			JSONObject response = receive();
			return response;			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
