package core.client.member;

import java.io.IOException;

import org.json.JSONObject;

import core.client.ObjectStreamClient;
import core.mapper.ServiceResolver;
import member.Member;

public class RegisterClient extends ObjectStreamClient {
	private Member tmpMember;
	private String pwChk;
	private boolean registerSuccess = false;

	public RegisterClient(Member tmpMember, String pwChk) {
		this.tmpMember = tmpMember;
		this.pwChk = pwChk;
	}

	@Override
	public JSONObject run() {
		try {
			connect(new ServiceResolver("member.RegisterService"));
			send(tmpMember);
			send(pwChk);
			JSONObject response = receive();
			System.out.println(registerSuccess ? "회원가입 성공" : "회원가입 실패");
//			return registerSuccess ? ViewMap.getView("로그인") : ViewMap.getView("메인화면");
			return response;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean getRegisterSuccess() {
		return registerSuccess;
	}
}
