package core.client.member;

import java.io.IOException;

import core.client.ObjectStreamClient;
import core.mapper.ServiceResolver;

public class FindPWClient extends ObjectStreamClient {
	private String id;
	private String findPW;
	public FindPWClient(String id) {
		this.id = id;
	}

	@Override
	public void run() {
		try {
			connect(new ServiceResolver("member.FindPWService"));
			send(id);
			findPW = (String) receive();
			if (findPW != null) {
				System.out.println("비밀번호: " + findPW);
			} else {
				System.out.println("존재하지 않는 ID 입니다.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
