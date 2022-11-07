package member;

public class MemberManager {
	private MemberManager() {

	}

	public static boolean register(Member tmpMember, String pwChk) {
		if (!tmpMember.validatePw(pwChk)) {
			System.out.println("회원가입실패:비밀번호불일치");
			return false;
		}
		if (MemberMap.containsKey(tmpMember.getUserId())) {
			System.out.println("회원가입실패:이미존재하는회원");
			return false;
		}
		MemberMap.put(tmpMember.getUserId(), tmpMember);
		System.out.println("회원가입성공:" + tmpMember);
		return true;
	}

	public static Member login(String id, String pw) {
		if (!MemberMap.containsKey(id)) {
			return null;
		}
		Member member = MemberMap.get(id);
		if (!member.validatePw(pw))
			return null;
		return member;
	}

	public static boolean deleteMember(Member member, String pw) {
		if (!member.getPassword().equals(pw)) {
			return false;
		}
		MemberMap.deleteMember(member.getUserId());
		return true;
	}

	public static String findPw(String id) {
		if (MemberMap.containsKey(id)) {
			return MemberMap.getpw(id);
		}
		return null;
	}

	public static boolean updatePw(Member me, String validatePw, String newPw) {
		if (!me.validatePw(validatePw)) {
			return false;
		}
		me.setPassword(newPw);
		MemberMap.put(me.getUserId(), me);
		return true;
	}
}
