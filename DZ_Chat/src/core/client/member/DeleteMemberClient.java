package core.client.member;

import java.io.IOException;

import org.json.JSONObject;

import core.client.Client;
import core.client.mapper.RequestType;
import member.Member;

public class DeleteMemberClient extends Client {
	private String pw;
	private JSONObject json = new JSONObject();
	
	public DeleteMemberClient( String pw) {
		this.pw = pw;
	}

	@Override
	public JSONObject run() {
		try {
//			json.put("member", getMe.toJSON());
			json.put("pw", pw);
			connect(new RequestType("member.DeleteMemberService"));
			send(json);
			JSONObject response = receive();
			unconnect();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
