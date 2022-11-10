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
			JSONObject sendJSON = new JSONObject();
			connect("member.FindPWService");
			sendJSON.put("id", id);
			send(sendJSON);
			JSONObject response = receive();
			unconnect();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
