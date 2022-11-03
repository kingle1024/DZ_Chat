package core.client.member;

import java.io.IOException;

import org.json.JSONObject;

import core.client.Client;
import core.client.mapper.RequestType;
import static core.client.Main.*;

public class UpdatePWClient extends Client {
	private final String validatePW;
	private final String newPW;
	private JSONObject json = new JSONObject();
	
	public UpdatePWClient(String validatePW, String newPW) {
		this.validatePW = validatePW;
		this.newPW = newPW;
	}

	@Override
	public JSONObject run() {
		try {
			if (!validatePW.equals(getMe().getPassword())) return null;
			json.put("member", getMe());
			json.put("validatePW", validatePW);
			json.put("newPW", newPW);
			connect(new RequestType("member.UpdatePWService"));
			send(json);
			JSONObject response = receive();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
