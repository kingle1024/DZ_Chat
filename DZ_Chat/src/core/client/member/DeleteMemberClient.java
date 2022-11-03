package core.client.member;

import java.io.IOException;

import org.json.JSONObject;

import core.client.Client;
import core.client.mapper.RequestType;
import member.Member;

public class DeleteMemberClient extends Client {
	private Member me;
	private String pw;
	private JSONObject json = new JSONObject();
	
	public DeleteMemberClient(Member me, String pw) {
		this.me = me;
		this.pw = pw;
	}

	@Override
	public JSONObject run() {
		try {
//			json.put("member", me.toJSON());
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
