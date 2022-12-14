package client.member;

import java.io.*;

import org.json.JSONObject;

import client.Client;
import dto.Transfer;
import dto.member.LoginDto;

public class LoginClient extends Client {
	private String id;
	private String pw;
	private JSONObject loginJSON;

	public LoginClient(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}

	@Override
	public JSONObject run() {
		try {
			connect("member.LoginService");
			send(Transfer.toJSON(new LoginDto.Request(id, pw)));
			JSONObject response = receive();
			unconnect();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
