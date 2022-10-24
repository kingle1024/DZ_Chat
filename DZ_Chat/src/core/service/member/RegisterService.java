package core.service.member;

import java.io.*;
import core.service.ObjectStreamService;
import log.Log;
import log.LogQueue;
import log.NeedLog;
import member.*;
import property.Property;

public class RegisterService extends ObjectStreamService implements NeedLog {
	private static final MemberManager memberManager = MemberManager.getInstance();
	private static final MemberMap memberMap = MemberMap.getInstance();
	private LogQueue logQueue = LogQueue.getInstance();
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
			logQueue.add(this);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Log toLog() {
		return new Log(Property.server().get("CHAT_LOG_FILE"), "Register Success");
	}
}
