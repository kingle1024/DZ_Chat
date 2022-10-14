package member;

import java.io.FileOutputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import message.ftp.FileCommon;

public class MemberDelete {

	MemberDao dao;
	FileCommon fileCommon;

	public void delete(Scanner scanner) {
		try {
			String uid;
			String pwd;
			int check;

			dao = new MemberDao();
			fileCommon = new FileCommon();

			System.out.println("\n2. 회원 탈퇴");
			System.out.println("본인 확인");
			do {
				System.out.print("아이디 : ");
				uid = scanner.nextLine();

				System.out.print("비밀번호 : ");
				pwd = scanner.nextLine();
			} while (!(dao.checkMember(uid, pwd)));

			System.out.print("탈퇴하시겠습니까? (1 탈퇴 2 취소) : ");
			check = scanner.nextInt();

			if (1 == check) {
				deleteFile(uid);
				System.out.println("탈퇴되었습니다.");
			} else if (2 == check) {
				System.out.println("취소되었습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteFile(String uid) throws Exception {

		Map<String, Member> memberMap = dao.deleteMember(uid);
		
		//파일 비우기
		new FileOutputStream("./memberFile.txt").close();
		
		//map에 있는 모든 값 파일에 적기
		for (Entry<String, Member> entry : memberMap.entrySet()) {
			fileCommon.saveContent("./memberFile.txt", entry.getValue() + "\n", true);
		}

	}

}
