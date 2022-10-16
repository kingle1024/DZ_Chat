package member;

import java.io.DataInputStream;
import java.util.Scanner;

public class MemberLogin {

	DataInputStream dis;
	MemberDao dao;

	public void login(Scanner scanner) {
		
		try {
			String id;
			String pwd;

			dao = new MemberDao();

			System.out.println("\n1. 로그인 하세요.");
			do {
				System.out.print("아이디 : ");
				id = scanner.nextLine();
				System.out.print("비밀번호 : ");
				pwd = scanner.nextLine();
			} while (!checkLogin(id, pwd));
			
//			System.out.println("로그인 성공");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean checkLogin(String id, String pwd) {
		if (dao.containKey(id) && dao.getPwd(id).equals(pwd))
			return true;
		else {
			System.out.println("로그인 정보가 일치하지 않습니다. 다시 입력해주세요.");
			return false;
		}
	}

}
