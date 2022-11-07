package core.service.serviceimpl.member;

import java.io.*;

import org.json.JSONObject;

import core.service.Service;
import log.Log;
import log.LogQueue;
import log.NeedLog;
import member.*;
import property.Property;

public class RegisterService extends Service implements NeedLog {
	private static final MemberManager memberManager = MemberManager.getInstance();
	private LogQueue logQueue = LogQueue.getInstance();

	@Override
	public void request() throws IOException {
		try {
			System.out.println("member.RegisterService");
			JSONObject receive = receive();
			Member tmpMember = Member.parseJSON(receive.getJSONObject("member"));
			String pwChk = receive.getString("pwChk");
			boolean successRegister = memberManager.register(tmpMember, pwChk);
			if (successRegister) logQueue.add(this);
			
			JSONObject sendJSON = new JSONObject();
			sendJSON.put("success", successRegister);
			send(sendJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Log toLog() {
		return new Log(Property.server().get("CHAT_LOG_FILE"), "Register Success");
	}
}
