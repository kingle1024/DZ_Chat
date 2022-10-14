package member;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MemberDao {

	FileOutputStream fos = new FileOutputStream("./MemberFile.txt");
	ObjectOutputStream oos;

	private Map<String, Member> MemberMap = new TreeMap<String, Member>();
	List<Member> memberList = new ArrayList<>();

	public MemberDao() throws Exception {
		oos = new ObjectOutputStream(fos);
	}

	// 회원 등록
	public void registerMember(Member member) throws Exception {
		MemberMap.put(member.getUserId(), member);
		memberList.add(member);
	}

	public void registerMember(String userId, String password, String name, int birth) throws Exception {
		MemberMap.put(userId, new Member(userId, password, name, birth));
		memberList.add(new Member(userId, password, name, birth));
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
	public boolean editMember(String userId, String password) {

		boolean check = false;
		if (MemberMap.containsKey(userId)) {
			Member member = MemberMap.get(userId);
			member.setPassword(password);
			check = true;
		}
		return check;
	}

	// 회원 탈퇴
	public boolean deleteMember(String userId) {
		boolean check = false;
		if (MemberMap.containsKey(userId)) {
			MemberMap.remove(userId);
			check = true;
		}
		return check;
	}

	public void fileSave() throws Exception {

		try {
			String MEMBER_FILE_NAME = "./MemberFile.txt";
			File file = new File(MEMBER_FILE_NAME);
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			out.writeObject(memberList);
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
