package member;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MemberRegister {

	DataInputStream dis;
	MemberDao dao;

	public void register(Scanner scanner) {
		try {
			String uid;
			String pwd;
			String pwdChk;
			String name;
			int birth;

			System.out.println("\n2. 회원가입 하세요.");
			do {
				System.out.print("아이디 : ");
				uid = scanner.nextLine();
			} while (dao.containKey(uid));

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

			new MemberFile(member);
			dao = new MemberDao();
			dao.registerMember(member);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	private boolean checkDupId(String id) {
//		File f = new File("./MemberFile.txt");
//
//		try (BufferedReader br = new BufferedReader(new FileReader(f));) {
//
//			while (true) {
//				String data = br.readLine();
//				if (data == null)
//					break;
//				String idData = data.split(" ")[0];
//				if (idData.equals(id)) {
//					System.out.println("이미 등록된 아이디입니다. 다시 입력해주세요.");
//					return false; // 아이디 중복
//				}
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return true;
//	}

	private boolean checkPwd(String pwd, String pwdChk) {
		if (pwd.equals(pwdChk))
			return true;
		else {
			System.out.println("비밀번호가 다릅니다. 다시 입력해주세요.");
			return false;
		}
	}

}
