package core.client.member;

import java.io.IOException;

import org.json.JSONObject;

import core.client.Client;
import core.client.mapper.RequestType;
import core.client.view.View;
import core.client.view.ViewMap;

public class FindPWClient extends Client {
	private String id;
	private String findPW;

	public FindPWClient(String id) {
		this.id = id;
	}

	@Override
	public JSONObject run() {
		try {
			JSONObject sendJSON = new JSONObject();
			connect(new RequestType("member.FindPWService"));
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
