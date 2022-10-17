package member;

public class MemberManager {
	private static MemberMap memberMap = MemberMap.getInstance();
	private static MemberManager memberManager;
	private MemberDao dao;

	private MemberManager() {
		
	}
	
	public static MemberManager getInstance() {
		if (memberManager == null) return memberManager = new MemberManager();
		return memberManager;
	}
	
	public boolean register(Member tmpMember, String pwChk) {
		if (!tmpMember.validatePw(pwChk)) {
			return false;
		}
		if (memberMap.containKey(tmpMember.getUserId())) {
			return false;
		}
		memberMap.put(tmpMember.getUserId(), tmpMember);
		//파일에 넣기
		return true;
	}

	public Member login(String id, String pw) {
		if (!memberMap.containKey(id)) {
			return null;
		}
		Member member = memberMap.get(id);
		if (!member.validatePw(pw))
			return null;
		return member;
	}

	public boolean delete(Member member, String pw) {
		if (!member.getPassword().equals(pw)) {
			return false;
		}
		memberMap.deleteMember(member.getUserId());
		return true;
	}

	public String findPw(String id) {
		if (memberMap.containKey(id)) {
			return memberMap.getpw(id);
		}
		return null;
	}
	
	//정보수정 확인 - MemberMap 47-53
	public boolean edit(Member me, String validatePw, String newPw) {
		if (!me.validatePw(validatePw)) {
			return false;
		}
		me.setPassword(newPw);
		return true;
	}
}
