package member;

import java.io.Serializable;

public class Member implements Comparable<Member>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4338769178604718663L;
	private String userId;
	private String password;
	private String name;
	private int birth;
	
	public Member(String userId, String password, String name, int birth) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.birth = birth;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBirth() {
		return birth;
	}

	public void setBirth(int birth) {
		this.birth = birth;
	}

	@Override
	public int compareTo(Member o) {
		 return this.getName().compareTo(o.getName());
	}	
	
	@Override
	public String toString() {
		return userId + " " + password + " " + name + " " + birth;
	}
	
}
