package member;

public class MemberManagerDB {
	private MemberManagerDB() { }
	
	public static boolean register(Member tmpMember, String pwChk) {
		return false;
	}
	
	public static Member login(String id, String pw) {
		return null;
	}

	public static boolean delete(Member member, String pw) {
		return false;
	}

	public static String findPw(String id) {
		return null;
	}

	public static boolean updatePw(Member me, String validatePw, String newPw) {
		return true;
	}
}
