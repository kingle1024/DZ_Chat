package core.client.member;

import java.io.IOException;

import org.json.JSONObject;

import core.client.ObjectStreamClient;
import core.mapper.ServiceResolver;
import member.Member;

public class DeleteMemberClient extends ObjectStreamClient {
	private Member me;
	private String pw;

	public DeleteMemberClient(Member me, String pw) {
		this.me = me;
		this.pw = pw;
	}

	@Override
	public JSONObject run() {
		try {
			connect(new ServiceResolver("member.DeleteMemberService"));
			send(me);
			send(pw);
			JSONObject response = receive();
			return response;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
