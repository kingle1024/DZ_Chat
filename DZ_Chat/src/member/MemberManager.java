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
	
	public boolean register(Member member, String pwChk) {
		if (!memberMap.checkPw(member.getPassword(), pwChk)) {
			return false;
		}
		if (memberMap.containKey(member.getUserId())) {
			return false;
		}
		memberMap.put(member.getUserId(), member);
		//파일에 넣기
		return true;
	}

	public Member login(String id, String pw) {
		if (!memberMap.containKey(id)) {
			return null;
		}
		if (!memberMap.checkMember(id, pw))
			return null;
		return memberMap.get(id);
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
}
