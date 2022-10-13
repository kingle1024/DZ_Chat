package member;

import java.util.Scanner;

public class MemberPwdSearch {

	MemberDao dao;

	public void passwdSearch(Scanner scanner) {
		try {
			String uid;

			System.out.println("\n3. 비밀번호 찾기");
			dao = new MemberDao();

			do {
				// 아이디 존재하지 않는 경우
				System.out.print("아이디 : ");
				uid = scanner.nextLine();
			} while (!dao.containKey(uid));
			
			// 아이디가 존재하면 비밀번호 알려주기
			System.out.println("비밀번호 : " + dao.getPwd(uid));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
