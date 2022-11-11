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
		private String member;
		
		public Response(Member member) {
			this.member = member != null
					? Transfer.toJSON(member).toString()
					: null;
		}
	}
}
