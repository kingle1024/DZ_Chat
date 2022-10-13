package member;

import java.util.Scanner;


public class MemberPwdSearch {
	
	MemberDao dao;
	
	public void passwdSearch(Scanner scanner) {
		try {
			String uid;
			
			System.out.println("\n3. 비밀번호 찾기");
			
			System.out.print("아이디 : ");
			uid = scanner.nextLine();
			
			dao = new MemberDao();
			System.out.println("비밀번호는 " + dao.getPwd(uid));
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
