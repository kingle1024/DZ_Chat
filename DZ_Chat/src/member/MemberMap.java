package member;

import java.util.Map;
import java.util.TreeMap;

public class MemberMap {
	private final static Map<String, Member> memberMap = new TreeMap<String, Member>();

	private MemberMap() {
	}

	public static Iterable<Member> values() {
		return memberMap.values();
	}

	public static int size() {
		return memberMap.size();
	}
	
	public static void put(String key, Member value) {
		memberMap.put(key, value);
	}

	public static Member get(String id) {
		return memberMap.get(id);
	}

	public static boolean containsKey(String key) {
		return memberMap.containsKey(key);
	}

	public static String getpw(String id) {
		return memberMap.get(id).getPassword();
	}

	// 회원 탈퇴
	public static void deleteMember(String userId) {
		if (memberMap.containsKey(userId)) {
			memberMap.remove(userId);
		}
	}
}
