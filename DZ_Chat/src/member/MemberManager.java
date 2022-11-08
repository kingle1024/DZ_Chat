package member;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import property.ServerProperties;

public class MemberManager {
	private static List<MemberManagerInterface> impls = new ArrayList<>();
	
	static {
		try {
			Properties clsNames = new Properties();
			clsNames.load(new FileInputStream(ServerProperties.getConnectProperties()));
			for (Object obj : clsNames.values()) {
				String value = (String) obj;
				String[] cls = value.split(",");
				System.out.println(Arrays.toString(cls));
				if (cls.length > 1) {
					impls.add((MemberManagerInterface) Class.forName(cls[0])
							.getConstructor(String.class)
							.newInstance(cls[1]));
				} else {
					impls.add((MemberManagerInterface) Class.forName(cls[0])
							.getConstructor()
							.newInstance());
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private MemberManager() { }
	
	public static boolean register(Member tmpMember, String pwChk) {
		return impls.stream()
				.map(x -> x.register(tmpMember, pwChk))
				.reduce((acc, x) -> acc && x)
				.orElse(false);
	}

	public static Member login(String id, String pw) {
		return impls.stream()
				.map(x -> x.login(id, pw))
				.filter(x -> x != null)
				.findAny()
				.orElse(null);
	}

	public static boolean deleteMember(Member member, String pw) {
		return impls.stream()
				.map(x -> x.deleteMember(member, pw))
				.reduce((acc, x) -> acc && x)
				.orElse(false);
	}

	public static String findPw(String id) {
		return impls.stream()
				.map(x -> x.findPw(id))
				.filter(x -> x != null)
				.findAny()
				.orElse(null);
	}

	public static boolean updatePw(Member me, String validatePw, String newPw) {
		return impls.stream()
				.map(x -> x.updatePw(me, validatePw, newPw))
				.reduce((acc, x) -> acc && x)
				.orElse(false);
	}
}
