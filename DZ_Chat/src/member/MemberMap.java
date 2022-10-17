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
		memberMap.put(key, value);
	}

	public Member get(String id) {
		return memberMap.get(id);
	}

	public boolean containsKey(String key) {
		return memberMap.containsKey(key);
	}

	public String getpw(String id) {
		return memberMap.get(id).getPassword();
	}

	// 회원 탈퇴
	public void deleteMember(String userId) {
		if (memberMap.containsKey(userId)) {
			memberMap.remove(userId);
		}
	}
}
