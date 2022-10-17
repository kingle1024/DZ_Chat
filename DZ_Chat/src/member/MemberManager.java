package member;

public class MemberManager {
	private static MemberMap memberMap = MemberMap.getInstance();
	private static MemberManager memberManager;

	private MemberManager() {

	}

	public static MemberManager getInstance() {
		if (memberManager == null)
			return memberManager = new MemberManager();
		return memberManager;
	}

	// 회원 가입
	public boolean register(Member tmpMember, String pwChk) {
		if (!tmpMember.validatePw(pwChk)) {
			System.out.println("회원가입실패:비밀번호불일치");
			return false;
		}
		if (memberMap.containsKey(tmpMember.getUserId())) {
			System.out.println("회원가입실패:이미존재하는회원");
			return false;
		}
		memberMap.put(tmpMember.getUserId(), tmpMember);
		System.out.println("회원가입성공:" + tmpMember);
		return true;
	}

	// 로그인
	public Member login(String id, String pw) {
		if (!memberMap.containsKey(id)) {
			return null;
		}
		Member member = memberMap.get(id);
		if (!member.validatePw(pw))
			return null;
		return member;
	}

	// 탈퇴
	public boolean delete(Member member, String pw) {
		if (!member.getPassword().equals(pw)) {
			return false;
		}
		memberMap.deleteMember(member.getUserId());
		return true;
	}

	// 비밀번호 찾기
	public String findPw(String id) {
		if (memberMap.containsKey(id)) {
			return memberMap.getpw(id);
		}
		return null;
	}

	// 정보수정 - 비밀번호 변경
	public boolean updatePw(Member me, String validatePw, String newPw) {
		if (!me.validatePw(validatePw)) {
			return false;
		}
		me.setPassword(newPw);
		return true;
	}
}
