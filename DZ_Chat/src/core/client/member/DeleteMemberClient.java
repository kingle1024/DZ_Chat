package core.client.member;

import java.io.IOException;
import core.client.ObjectStreamClient;
import core.mapper.ServiceResolver;
import member.Member;

public class DeleteMemberClient extends ObjectStreamClient {
	private Member me;
	private String pw;
	private boolean deleteSuccess = false;

	public DeleteMemberClient(Member me, String pw) {
		this.me = me;
		this.pw = pw;
	}

	@Override
	public void run() {
		try {
			connect(new ServiceResolver("member.DeleteMemberService"));
			send(me);
			send(pw);
			deleteSuccess = (Boolean) receive();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean getDeleteSuccess() {
		return deleteSuccess;
	}
}
