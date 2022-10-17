package core.service.member;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import core.service.ObjectStreamService;
import member.Member;
import member.MemberManager;

public class UpdatePWService extends ObjectStreamService {
	private final static MemberManager memberManager = MemberManager.getInstance();
	private Member member;
	private String validatePW;
	private String newPW;

	public UpdatePWService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		super(is, os);
	}

	@Override
	public void request() throws IOException {
		try {
			member = (Member) is.readObject();
			validatePW = (String) is.readObject();
			newPW = (String) is.readObject();
			os.writeObject(memberManager.updatePw(member, validatePW, newPW));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}

}
