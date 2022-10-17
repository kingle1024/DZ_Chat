package member;

import java.util.Map;
import java.util.TreeMap;

public class MemberMap {
	private final static Map<String, Member> memberMap = new TreeMap<String, Member>();
	private static MemberMap map;

	private MemberMap() {
	}

	public static MemberMap getInstance() {
		if (map == null)
			return map = new MemberMap();
		return map;
	}

	public Iterable<Member> values() {
		return memberMap.values();
	}

	public void put(String key, Member value) {
		map.put(key, value);
	}
	
	public Member get(String id) {
		return memberMap.get(id);
	}
	

	// 회원 등록
	public void registerMember(Member member) throws Exception {
		memberMap.put(member.getUserId(), member);
	}

	public void registerMember(String userId, String password, String name, String birth) throws Exception {
		memberMap.put(userId, new Member(userId, password, name, birth));
	}

	// 키 중복 체크
	public boolean containKey(String key) {
		return memberMap.containsKey(key);
	}

	// 키 값에 해당하는 비밀번호 value 값 가져오기
	public String getpw(String id) {
		return memberMap.get(id).getPassword();
	}

	// 회원 탈퇴
	public void deleteMember(String userId) {
		// boolean check = false;
		if (memberMap.containsKey(userId)) {
			memberMap.remove(userId);
		}
	}

	// 로그인 확인
	public boolean isCorrectPW(String userId, String password) {
		if (containKey(userId) && getpw(userId).equals(password)) {
			return true;
		} else {
			System.out.println("로그인 정보가 일치하지 않습니다. 다시 입력해주세요.");
			return false;
		}
	}

	// 회원가입 - 아이디 중복 확인
	public boolean checkId(String uid) {
		if (containKey(uid)) {
			System.out.println("이미 등록된 아이디입니다. 다시 입력해주세요.");
			return false;
		} else
			return true;

	}

	// 회원가입 - 비밀번호 확인
	public boolean checkPw(String pw, String pwChk) {
		if (pw.equals(pwChk))
			return true;
		else {
			System.out.println("비밀번호가 다릅니다. 다시 입력해주세요.");
			return false;
		}
	}
}
