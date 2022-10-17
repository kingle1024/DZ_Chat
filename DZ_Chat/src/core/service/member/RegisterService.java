package core.service.member;

import java.io.*;

import core.service.ObjectStreamService;
import member.*;

public class RegisterService extends ObjectStreamService {
	private static final MemberManager memberManager = MemberManager.getInstance();
	public RegisterService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		super(is, os);
	}
	
	@Override
	public void request() throws IOException {
		try {
			Member tmpMember = (Member) is.readObject();
			String pwChk = (String) is.readObject();
			os.writeObject(Boolean.valueOf(memberManager.register(tmpMember, pwChk)));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
}
