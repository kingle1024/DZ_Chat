package core.service.member;

import java.io.*;
import core.service.ObjectStreamService;
import member.*;

public class RegisterService extends ObjectStreamService {
	private static final MemberManager memberManager = MemberManager.getInstance();
	private static final MemberMap memberMap = MemberMap.getInstance();
	public RegisterService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		super(is, os);
	}

	@Override
	public void request() throws IOException {
		try {
			System.out.println("member.RegisterService");
			Member tmpMember = (Member) is.readObject();
			String pwChk = (String) is.readObject();
			System.out.println(tmpMember);
			os.writeObject(Boolean.valueOf(memberManager.register(tmpMember, pwChk)));
			for (Member m : memberMap.values()) System.out.println(m);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
}
