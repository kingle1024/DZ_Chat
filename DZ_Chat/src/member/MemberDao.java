package member;

import java.io.BufferedReader;
import java.io.FileReader;

import message.ftp.FileCommon;
import property.Property;

public class MemberDao {
	private static final String filePath = Property.server().get("MEMBER_FILE");
	private static MemberDao dao = new MemberDao();

	private MemberDao() {
	}
	
	public static MemberDao getInstance() {
		return dao;
	}

	// 파일에서 회원정보 받아와서 Map에 저장
	public void readContent() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String readStr = "";
			String[] splitStr = null;

			while ((readStr = br.readLine()) != null) {
				splitStr = readStr.split(",");
				MemberMap.put(splitStr[0], new Member(splitStr[0], splitStr[1], splitStr[2], splitStr[3]));
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Map에 있는 정보 파일에 쓰기 - 서버 종료 시 한번 반영하도록
	public void writeContent() {
		FileCommon fileCommon = new FileCommon();
		fileCommon.saveContent(filePath, "", false);		
		for (Member member : MemberMap.values()) {
			fileCommon.saveContent(filePath, member.toString() + "\n", true);
		}
	}
}
