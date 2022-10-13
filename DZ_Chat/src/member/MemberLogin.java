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

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("command", "login");
			jsonObject.put("uid", uid);
			jsonObject.put("pwd", pwd);
			
			System.out.println("jsonObject = " + jsonObject.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
}
