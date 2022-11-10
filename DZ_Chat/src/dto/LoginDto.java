package dto;

import member.Member;

public class LoginDto {
	public static class Request {
		private String id;
		private String pw;

		public Request(String id, String pw) {
			this.id = id;
			this.pw = pw;
		}

		public String getId() {
			return id;
		}

		public String getPw() {
			return pw;
		}
	}

	public static class Response {
		private String member;
		
		public Response(Member member) {
			this.member = Transfer.toJSON(member).toString();
		}
		
		public String getMember() {
			return member;
		}
	}
}
