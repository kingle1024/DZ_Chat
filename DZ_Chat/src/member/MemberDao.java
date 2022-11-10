package member;

import java.io.BufferedReader;
import java.io.FileReader;

import message.ftp.FileCommon;
import property.ServerProperties;

public class MemberDao {
	private static final String filePath = ServerProperties.getMemberFile();

	private MemberDao() {
	}

	public static void readContent() {
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

	public static void writeContent() {
		FileCommon fileCommon = new FileCommon();
		fileCommon.saveContent(filePath, "", false);		
		for (Member member : MemberMap.values()) {
			fileCommon.saveContent(filePath, member.toString() + "\n", true);
		}
	}
}
