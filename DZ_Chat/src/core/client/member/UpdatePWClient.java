package core.client.member;

import java.io.IOException;

import org.json.JSONObject;

import core.client.ObjectStreamClient;
import core.mapper.ServiceResolver;
import member.Member;

public class UpdatePWClient extends ObjectStreamClient {
	private final Member me;
	private final String validatePW;
	private final String newPW;
	private boolean updateSuccess = false;

	public UpdatePWClient(Member me, String validatePW, String newPW) {
		this.me = me;
		this.validatePW = validatePW;
		this.newPW = newPW;
	}

	@Override
	public JSONObject run() {
		try {
			connect(new ServiceResolver("member.UpdatePWService"));
			send(me);
			send(validatePW);
			send(newPW);
			JSONObject response = receive();
			return response;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean getUpdateSuccess() {
		return updateSuccess;
	}
}
