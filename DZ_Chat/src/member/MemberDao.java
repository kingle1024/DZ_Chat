package member;

import java.io.BufferedReader;
import java.io.FileReader;

import message.ftp.FileCommon;

public class MemberDao {
	private static final MemberMap memberMap = MemberMap.getInstance();
	private static final String filePath = ".DZ_Chat/resources/member/memberFile.txt";

	public MemberDao() {
		readContent(filePath);
	}

	//파일에서 회원정보 받아와서 Map에 저장
	public void readContent(String filePath) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));

			String readStr = "";
			String[] splitStr = null;

			while ((readStr = br.readLine()) != null) {
				splitStr = readStr.split(",");
				memberMap.put(splitStr[0], new Member(splitStr[0], splitStr[1], splitStr[2], splitStr[3]));
			}
			br.close();
		} catch (Exception e) {
		}
	}

	// Map에 있는 정보 파일에 쓰기 - 종료시 한번 반영하도록
	public void WriteContent() {
		FileCommon fileCommon = new FileCommon();

		for (Member member : memberMap.values()) {
			fileCommon.saveContent(filePath, member.toString() + "\n", true);
		}

	}

}
