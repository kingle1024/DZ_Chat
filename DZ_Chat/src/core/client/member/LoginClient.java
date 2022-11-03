package core.client.member;

import java.io.*;

import org.json.JSONObject;

import core.client.ObjectStreamClient;
import core.client.view.View;
import core.client.view.ViewMap;
import core.mapper.ServiceResolver;
import member.Member;

public class LoginClient extends ObjectStreamClient {
	private Member member;
	private String id;
	private String pw;

	public LoginClient(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}

	@Override
	public JSONObject run() {
		try {
			connect(new ServiceResolver("member.LoginService"));
			send(id);
			send(pw);
			JSONObject member = receive();
			unconnect();
			return member;
//			if (member != null) {
//				System.out.println("로그인 성공했습니다.");
//				return ViewMap.getView("로그인 성공");
//			} else {
//				System.out.println("로그인 실패했습니다.");
//				return ViewMap.getView("메인화면");
//			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Member getMember() {
		return member;
	}
}
