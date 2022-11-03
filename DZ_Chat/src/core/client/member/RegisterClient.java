package core.client.member;

import java.io.IOException;

import org.json.JSONObject;

import core.client.Client;
import core.client.mapper.RequestType;
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
//			json.put("member", tmpMember.toJSON());
			json.put("pwChk", pwChk);
			connect(new RequestType("member.RegisterService"));
			send(json);
			JSONObject response = receive();
			boolean registerSuccess = false; // TODO
			System.out.println(registerSuccess ? "회원가입 성공" : "회원가입 실패");
			return response;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
