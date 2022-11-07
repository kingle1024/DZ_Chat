package core.service.member;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import core.service.ObjectStreamService;
import log.Log;
import log.LogQueue;
import member.Member;
import member.MemberManager;
import property.Property;

public class UpdatePWService extends ObjectStreamService {
	private final static MemberManager memberManager = MemberManager.getInstance();
	private Member member;
	private String validatePW;
	private String newPW;
	private LogQueue logQueue = LogQueue.getInstance();

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
			logQueue.add(toLog());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}

	public Log toLog() {
		return new Log(Property.server().get("CHAT_LOG_FILE"), "Update Success");
	}
}
