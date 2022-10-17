package core.client.member;

import java.io.IOException;
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
	public void run() {
		try {
			connect(new ServiceResolver("member.RegisterService"));
			send(tmpMember);
			send(pwChk);
			registerSuccess = (Boolean) receive();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean getRegisterSuccess() {
		return registerSuccess;
	}
}
