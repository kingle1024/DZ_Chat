package client.member;

import static client.Main.*;

import java.io.IOException;

import org.json.JSONObject;

import client.Client;

public class DeleteMemberClient extends Client {
	private String pw;
	private JSONObject json = new JSONObject();

	public DeleteMemberClient(String pw) {
		this.pw = pw;
	}

	@Override
	public JSONObject run() {
		try {
			json.put("member", getMe().getJSON());
			json.put("pw", pw);
			connect("member.DeleteMemberService");
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
