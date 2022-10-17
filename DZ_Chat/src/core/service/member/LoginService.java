package core.service.member;

import java.io.*;

import core.service.ObjectStreamService;
import member.*;

public class LoginService extends ObjectStreamService {
	private static final MemberManager memberManager = MemberManager.getInstance();
	public LoginService(ObjectInputStream is, ObjectOutputStream os, Object...obj) throws IOException {
		super(is, os);
	}
	@Override
	public void request() throws IOException {
		try {
			String id = (String) is.readObject();
			String pw = (String) is.readObject();
			Member member = memberManager.login(id, pw);
			if (member != null) {
				os.writeObject(member);
			} else {
				os.writeObject(null);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

}
