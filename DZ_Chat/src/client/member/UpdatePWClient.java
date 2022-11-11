package client.member;

import static client.Main.*;

import java.io.IOException;

import org.json.JSONObject;

import client.Client;
import dto.Transfer;
import dto.member.UpdatePWDto;

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
			if (!validatePW.equals(getMe().getPassword())) {
				return new JSONObject().put("success", false);
			}
			connect("member.UpdatePWService");
			send(Transfer.toJSON(new UpdatePWDto.Request(getMe(), validatePW, newPW)));
			JSONObject response = receive();
			unconnect();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
