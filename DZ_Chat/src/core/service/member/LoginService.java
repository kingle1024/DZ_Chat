package core.service.member;

import java.io.*;
import core.service.ObjectStreamService;
import log.Log;
import log.LogQueue;
import log.NeedLog;
import member.*;
import property.Property;

public class LoginService extends ObjectStreamService implements NeedLog {
	private String id;
	private static final MemberManager memberManager = MemberManager.getInstance();
	private LogQueue logQueue = LogQueue.getInstance();

	public LoginService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		super(is, os);
	}

	@Override
	public void request() throws IOException {
		try {
			this.id = (String) is.readObject();
			String pw = (String) is.readObject();
			Member member = memberManager.login(id, pw);
			if (member != null) {
				os.writeObject(member);
				logQueue.add(this);
			} else {
				os.writeObject(null);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Log toLog() {
		return new Log(Property.server().get("CHAT_LOG_FILE"), "id : " + this.id + " Login Success");
	}

}
