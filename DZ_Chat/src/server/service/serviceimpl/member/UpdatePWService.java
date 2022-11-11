package server.service.serviceimpl.member;

import java.io.IOException;

import org.json.JSONObject;

import dto.Transfer;
import dto.member.UpdatePWDto;
import log.Log;
import log.LogQueue;
import member.Member;
import member.MemberManager;
import member.MemberMap;
import property.ServerProperties;
import server.service.Service;

public class UpdatePWService extends Service {
	private Member member;
	private String validatePW;
	private String newPW;

	@Override
	public void request() throws IOException {
		try {
			JSONObject receiveJSON = receive();
			System.out.println("receiveJSON: " + receiveJSON);
			member = Member.parseJSON(new JSONObject(receiveJSON.getString("member")));
			validatePW = receiveJSON.getString("validatePW");
			newPW = receiveJSON.getString("newPW");
			
			send(
				Transfer.toJSON(new UpdatePWDto.Response(
					MemberManager.updatePw(member, validatePW, newPW)
					, MemberMap.get(member.getUserId())
				)));
			LogQueue.add(toLog());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Log toLog() {
		return new Log(ServerProperties.getChatLogFile(), "Register Success");
	}
}
