package server.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import member.Member;

public class MemberMap {
	public static final Map<String, Member> memberMap = Collections.synchronizedMap(new HashMap<>());

	public static Member get(String userid) {
		return memberMap.get(userid);
	}
	
	public static Member put(String userid, Member member) {
		return memberMap.put(userid, member);
	}
}
