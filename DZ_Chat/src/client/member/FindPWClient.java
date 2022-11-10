package client.member;

import java.io.IOException;

import org.json.JSONObject;

import client.Client;

public class FindPWClient extends Client {
	private String id;

	public FindPWClient(String id) {
		this.id = id;
	}

	@Override
	public JSONObject run() {
		try {
			connect("member.FindPWService");
			send(new JSONObject().put("id", id));
			JSONObject response = receive();
			unconnect();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
