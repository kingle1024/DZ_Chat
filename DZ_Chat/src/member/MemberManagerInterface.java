package member;

public interface MemberManagerInterface {
	boolean register(Member tmpMember, String pwChk);

	Member login(String id, String pw);

	boolean deleteMember(Member member, String pw);

	String findPw(String id);

	boolean updatePw(Member me, String validatePw, String newPw);
}
