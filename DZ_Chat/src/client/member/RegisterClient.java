package client.member;

import java.io.IOException;

import org.json.JSONObject;

import client.Client;
import dto.Transfer;
import dto.member.RegisterDto;
import member.Member;

public class RegisterClient extends Client {
	private Member member;
	private String pwChk;
	
	public RegisterClient(Member member, String pwChk) {
		this.member = member;
		this.pwChk = pwChk;
	}

	@Override
	public JSONObject run() {
		try {
			connect("member.RegisterService");
			send(Transfer.toJSON(new RegisterDto.Request(member, pwChk)));
			JSONObject response = receive();
			return response;			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
