package core.service.member;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import core.service.ObjectStreamService;
import member.MemberManager;

public class FindPWService extends ObjectStreamService {
	private static final MemberManager memberManager = MemberManager.getInstance();
	public FindPWService(ObjectInputStream is, ObjectOutputStream os, Object...objects) throws IOException {
		super(is, os);
	}
	
	@Override
	public void request() throws IOException {
		try {
			String id = (String) is.readObject();
			os.writeObject(memberManager.findPw(id));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
