package member;

import java.io.DataInputStream;
import java.util.Scanner;

public class MemberLogin {

	DataInputStream dis;
	MemberDao dao;

	public void login(Scanner scanner) {
		
		try {
			String uid;
			String pwd;

			dao = new MemberDao();

			System.out.println("\n1. 로그인 하세요.");
			do {
				System.out.print("아이디 : ");
				uid = scanner.nextLine();
				System.out.print("비밀번호 : ");
				pwd = scanner.nextLine();
			} while (!(dao.containKey(uid) && dao.getPwd(uid).equals(pwd)));
			
//			System.out.println("로그인 성공");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
