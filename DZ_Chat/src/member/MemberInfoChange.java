package member;

import java.io.FileOutputStream;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import message.ftp.FileCommon;

public class MemberInfoChange {

	MemberDao dao;
	FileCommon fileCommon;

	public void edit(Scanner scanner) {
		try {
			String uid;
			String pwd;
			String newPwd;

			dao = new MemberDao();
			fileCommon = new FileCommon();

			System.out.println("\n1. 회원 정보 수정");
			System.out.println("본인 확인");
			do {
				System.out.print("아이디 : ");
				uid = scanner.nextLine();

				System.out.print("비밀번호 : ");
				pwd = scanner.nextLine();
			} while (!(dao.checkMember(uid, pwd)));

			System.out.print("변경할 비밀번호 : ");
			newPwd = scanner.nextLine();

			editFile(uid, newPwd);
			System.out.println("비밀번호가 변경되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void editFile(String uid, String pwd) throws Exception {

		Map<String, Member> memberMap = dao.editMember(uid, pwd);

		// 파일 비우기
		new FileOutputStream("./memberFile.txt").close();

		// map에 있는 모든 값 파일에 적기
		for (Entry<String, Member> entry : memberMap.entrySet()) {
			fileCommon.saveContent("./memberFile.txt", entry.getValue() + "\n", true);
		}

	}
}
