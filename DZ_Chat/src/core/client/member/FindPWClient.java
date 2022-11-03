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
			connect(new RequestType("member.FindPWService"));
			send("id", id);
			JSONObject response = receive();
			if (findPW != null) {
				System.out.println("비밀번호: " + findPW);
			} else {
				System.out.println("존재하지 않는 ID 입니다.");
			}
			unconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
