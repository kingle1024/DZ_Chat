package core.service.member;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import core.service.ObjectStreamService;
import log.Log;
import log.LogQueue;
import log.NeedLog;
import member.Member;
import member.MemberManager;
import property.Property;

public class DeleteMemberService extends ObjectStreamService implements NeedLog {
	private static final MemberManager memberManager = MemberManager.getInstance();
	private LogQueue logQueue = LogQueue.getInstance();
	public DeleteMemberService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		super(is, os);
	}

	@Override
	public void request() throws IOException {
		try {
			Member me = (Member) is.readObject();
			String pw = (String) is.readObject();
			os.writeObject(Boolean.valueOf(memberManager.delete(me, pw)));
			logQueue.add(this);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Log toLog() {
		return new Log(Property.server().get("CHAT_LOG_FILE"), "Delete MemberData");
	}
}
