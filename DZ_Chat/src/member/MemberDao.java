package member;

import java.util.Map;
import java.util.TreeMap;

public class MemberDao{
	
	private Map<String, Member> MemberMap = new TreeMap<String, Member>();

	public MemberDao() {
		
	}
	
	//회원 등록
	public void registerMember(Member member) {
		MemberMap.put(member.getUserId(), member);
	}
	
	public void registerMember(String userId, String password, String name, int birth) {
		MemberMap.put(userId, new Member(userId, password, name, birth));
	}
	
	
	// 키 중복 체크
	public boolean containKey(String key) {
		return MemberMap.containsKey(key);
	}
	// 키 값에 해당하는 value 값 가져오기
	public Member get(String key) {
		return MemberMap.get(key);
	}
	
	// 회정 정보 수정, 비밀번호만 수정 가능
	public boolean editMember(String userId, String password) {
		
		boolean check = false;
		if(MemberMap.containsKey(userId)) {
			Member member = MemberMap.get(userId);
			member.setPassword(password);
			check = true;
		}	
		return check;
	}
	
	// 회원 탈퇴
	public boolean deleteMember(String userId) {
		boolean check = false;
		if(MemberMap.containsKey(userId)) {
			Member member = MemberMap.remove(userId);
			check = true;
		}
		return check;
	}
	
	
}
