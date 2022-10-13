package member;

import java.io.DataInputStream;
import java.util.Scanner;

import org.json.JSONObject;

public class MemberLogin {
	
	DataInputStream dis;
	
	public void login(Scanner scanner) {
		try {
			String uid;
			String pwd;
			
			System.out.println("\n1. 로그인 하세요.");
			System.out.print("아이디 : ");
			uid = scanner.nextLine();
			System.out.print("비밀번호 : "); 
			pwd = scanner.nextLine();
			
//			connect();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("command", "login");
			jsonObject.put("uid", uid);
			jsonObject.put("pwd", pwd);
			
			System.out.println("jsonObject = " + jsonObject.toString());
//			send(jsonObject.toString());
			
			loginResponse();

//			disconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loginResponse() throws Exception {
		String json = dis.readUTF();
		JSONObject root = new JSONObject(json);
		String statusCode = root.getString("statusCode");
		String message = root.getString("message");
		
		if (statusCode.equals("0")) {
			System.out.println("로그인 성공");
		} else {
			System.out.println(message);
		}
	}
	
}
