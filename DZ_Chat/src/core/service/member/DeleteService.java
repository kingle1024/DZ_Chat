package core.service.member;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import core.service.ObjectStreamService;
import member.Member;
import member.MemberManager;

public class DeleteService extends ObjectStreamService {
	private static final MemberManager memberManager = MemberManager.getInstance();

	public DeleteService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		super(is, os);
	}

	@Override
	public void request() throws IOException {
		try {
			Member me = (Member) is.readObject();
			String pw = (String) is.readObject();
			os.writeObject(Boolean.valueOf(memberManager.delete(me, pw)));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
}
