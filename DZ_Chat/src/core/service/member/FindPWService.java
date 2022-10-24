package core.service.member;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import core.service.ObjectStreamService;
import log.Log;
import log.LogQueue;
import log.NeedLog;
import member.MemberManager;
import property.Property;

public class FindPWService extends ObjectStreamService implements NeedLog {
	private static final MemberManager memberManager = MemberManager.getInstance();
	private LogQueue logQueue = LogQueue.getInstance();
	public FindPWService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		super(is, os);
	}

	@Override
	public void request() throws IOException {
		try {
			String id = (String) is.readObject();
			os.writeObject(memberManager.findPw(id));
			logQueue.add(this);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Log toLog() {
		return new Log(Property.server().get("CHAT_LOG_FILE"), "Find Pw");
	}

}
