package member;

import java.io.Serializable;
import java.util.Objects;

public class Member implements Comparable<Member>, Serializable {

	private static final long serialVersionUID = -4338769178604718663L;
	private String userId;
	private String password;
	private String name;
	private String birth;

	//생성자 함수
	public Member(String userId, String password, String name, String birth) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.birth = birth;
	}

	//Getter, Setter
	public String getUserId() {
		return userId;
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

	public String getBirth() {
		return birth;
	}

	//equals, hashCode, compareTo
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		return Objects.equals(userId, other.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}

	@Override
	public int compareTo(Member o) {
		return this.getName().compareTo(o.getName());
	}

	//toString 파일 저장 형식(, 구분자 이용)
	@Override
	public String toString() {
		return userId + "," + password + "," + name + "," + birth;
	}

	public String nickname() {
		return name + "( " + userId + " )";
	}

}