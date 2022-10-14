package member;

import java.util.Scanner;

public class MemberInfoChange {

	MemberDao dao;

	public void edit(Scanner scanner) {
		try {
			String uid;
			String pwd;
			String newPwd;

			dao = new MemberDao();

			System.out.println("\n1. 회원 정보 수정");
			System.out.println("본인 확인");
			do {
				System.out.print("아이디 : ");
				uid = scanner.nextLine();

				System.out.print("비밀번호 : ");
				pwd = scanner.nextLine();
			} while (!(checkMember(uid, pwd)));

			System.out.print("변경할 비밀번호 : ");
			newPwd = scanner.nextLine();

			if(dao.editMember(uid, newPwd)) {
				dao.fileSave();
				System.out.println("비밀번호가 변경되었습니다.");
			}
		

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean checkMember(String uid, String pwd) {
		if (!(dao.containKey(uid) && dao.getPwd(uid).equals(pwd))) {
			System.out.println("회원 정보가 일치하지 않습니다.");
			return false;
		} else {
			System.out.println(uid + "회원님 확인되었습니다.");
			return true;
		}
	}
}
