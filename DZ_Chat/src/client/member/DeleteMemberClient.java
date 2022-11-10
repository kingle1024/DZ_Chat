package client.member;

import static client.Main.*;

import java.io.IOException;

import org.json.JSONObject;

import client.Client;
import dto.DeleteMemberDto;
import dto.Transfer;

public class DeleteMemberClient extends Client {
	private String pw;

	public DeleteMemberClient(String pw) {
		this.pw = pw;
	}

	@Override
	public JSONObject run() {
		try {
			connect("member.DeleteMemberService");
			send(Transfer.toJSON(new DeleteMemberDto.Request(getMe(), pw)));
			JSONObject response = receive();
			unconnect();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
