package member;

import java.util.Scanner;

public class MemberRegister {

	MemberDao dao;

	public void register(Scanner scanner) {
		try {
			String uid;
			String pwd;
			String pwdChk;
			String name;
			int birth;

			dao = new MemberDao();

			System.out.println("\n2. 회원가입 하세요.");
			do {
				System.out.print("아이디 : ");
				uid = scanner.nextLine();
			} while (checkId(uid));

			do {
				System.out.print("비밀번호 : ");
				pwd = scanner.nextLine();

				System.out.print("비밀번호 확인: ");
				pwdChk = scanner.nextLine();
			} while (!checkPwd(pwd, pwdChk));

			System.out.print("이름 : ");
			name = scanner.nextLine();

			System.out.print("생년월일(8자리 입력) : ");
			birth = Integer.parseInt(scanner.nextLine());

			Member member = new Member(uid, pwdChk, name, birth);

			dao.registerMember(member);
			dao.fileSave();
			
//			System.out.println("회원가입 완료");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkId(String uid) {
		if (dao.containKey(uid)) {
			System.out.println("이미 등록된 아이디입니다. 다시 입력해주세요.");
			return true;
		} else
			return false;

	}

	private boolean checkPwd(String pwd, String pwdChk) {
		if (pwd.equals(pwdChk))
			return true;
		else {
			System.out.println("비밀번호가 다릅니다. 다시 입력해주세요.");
			return false;
		}
	}

}
