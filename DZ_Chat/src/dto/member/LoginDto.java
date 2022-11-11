package dto.member;

import dto.Transfer;
import member.Member;

public class LoginDto {
	public static class Request {
		private String id;
		private String pw;

		public Request(String id, String pw) {
			this.id = id;
			this.pw = pw;
		}
	}

	public static class Response {
		private String hasMember;
		private String member;
		
		public Response(String hasMember, String member) {
			this.hasMember = hasMember;
			this.member = member;
		}
		
		public Response(boolean hasMember, Member member) {
			this(Boolean.toString(hasMember), Transfer.toJSON(member).toString());
		}
		
		public String getMember() {
			return member;
		}
	}
}
