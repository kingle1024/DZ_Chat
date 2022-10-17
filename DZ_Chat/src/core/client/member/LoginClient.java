package core.client.member;

import java.io.*;
import java.util.Scanner;

import core.client.Main;
import core.client.ObjectStreamClient;
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
	public void run() {
		try {
			connect(new ServiceResolver("member.LoginService"));
			send(id);
			send(pw);
			member = (Member) receive();
			if (member != null) {
				System.out.println("로그인 성공했습니다.");
			} else {
				System.out.println("로그인 실패했습니다.");
			}
			unconnect();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Member getMember() {
		return member;
	}
}
