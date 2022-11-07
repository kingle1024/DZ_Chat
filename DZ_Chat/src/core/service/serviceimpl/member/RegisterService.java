package core.service.serviceimpl.member;

import java.io.*;
import org.json.JSONObject;
import core.service.Service;
import log.Log;
import log.LogQueue;
import member.*;
import property.ServerProperties;

public class RegisterService extends Service {
	private static final MemberManager memberManager = MemberManager.getInstance();

	@Override
	public void request() throws IOException {
		try {
			System.out.println("member.RegisterService");
			JSONObject receive = receive();
			Member tmpMember = Member.parseJSON(receive.getJSONObject("member"));
			String pwChk = receive.getString("pwChk");
			boolean successRegister = memberManager.register(tmpMember, pwChk);
			if (successRegister) LogQueue.add(toLog());
			
			JSONObject sendJSON = new JSONObject();
			sendJSON.put("success", successRegister);
			send(sendJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Log toLog() {
		return new Log(ServerProperties.getChatLogFile(), "Register Success");
	}
}
