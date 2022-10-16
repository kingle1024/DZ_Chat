package member;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.TreeMap;

public class MemberDao {

	private static Map<String, Member> MemberMap = new TreeMap<String, Member>();
	
	public MemberDao() {
		String filePath = "./memberFile.txt";
		readContent(filePath);
	}
	
	// 파일에서 회원정보 받아와서 Map에 저장
	public void readContent(String filePath) {
		try {

			BufferedReader br = new BufferedReader(new FileReader(filePath));

			String readStr = "";
			String[] splitStr = null; // new String[MemberMap.size()];
			while ((readStr = br.readLine()) != null) {

				splitStr = readStr.split(",");
				MemberMap.put(splitStr[0],
						new Member(splitStr[0], splitStr[1], splitStr[2], splitStr[3]));
			}

			br.close();

		} catch (Exception e) {
		}
	}

	// 회원 등록
	public void registerMember(Member member) throws Exception {
		MemberMap.put(member.getUserId(), member);
	}

	public void registerMember(String userId, String password, String name, String birth) throws Exception {
		MemberMap.put(userId, new Member(userId, password, name, birth));
	}

	// 키 중복 체크
	public boolean containKey(String key) {
		return MemberMap.containsKey(key);
	}

	// 키 값에 해당하는 비밀번호 value 값 가져오기
	public String getPwd(String key) {
		return MemberMap.get(key).getPassword();
	}

	// 회정 정보 수정, 비밀번호만 수정 가능
	public Map<String, Member> editMember(String userId, String newPwd) {

		if (MemberMap.containsKey(userId)) {
			Member member = MemberMap.get(userId);
			member.setPassword(newPwd);
			return MemberMap;
		}
		return MemberMap;
	}

	// 회원 탈퇴
	public Map<String, Member> deleteMember(String userId) {
		// boolean check = false;
		if (MemberMap.containsKey(userId)) {
			MemberMap.remove(userId);
			return MemberMap;
		}
		return MemberMap;
	}
	
	public boolean checkMember(String userId, String pwd) {
		if (!(containKey(userId) && getPwd(userId).equals(pwd))) {
			System.out.println("회원 정보가 일치하지 않습니다.");
			return false;
		} else {
			System.out.println(userId + "회원님 확인되었습니다.");
			return true;
		}
	}
	
	public static boolean isCorrectPW(String userId, String password) {
		if (MemberMap.get(userId).getPassword().equals(password)) {
			return true;
		}
		return false;
	}

}
