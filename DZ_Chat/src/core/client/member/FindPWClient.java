package core.client.member;

import java.io.IOException;

import org.json.JSONObject;

import core.client.ObjectStreamClient;
import core.client.view.View;
import core.client.view.ViewMap;
import core.mapper.ServiceResolver;

public class FindPWClient extends ObjectStreamClient {
	private String id;
	private String findPW;

	public FindPWClient(String id) {
		this.id = id;
	}

	@Override
	public JSONObject run() {
		try {
			connect(new ServiceResolver("member.FindPWService"));
			send(id);
			JSONObject response = receive();
			if (findPW != null) {
				System.out.println("비밀번호: " + findPW);
			} else {
				System.out.println("존재하지 않는 ID 입니다.");
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
