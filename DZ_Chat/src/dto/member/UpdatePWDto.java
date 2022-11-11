package dto.member;

import dto.Transfer;
import member.Member;

public class UpdatePWDto {
	public static class Request {
		private String member;
		private String validatePW;
		private String newPW;
		
		public Request(Member member, String validatePW, String newPW) {
			this.member = Transfer.toJSON(member).toString();
			this.validatePW = validatePW;
			this.newPW = newPW;
		}
	}
	
	public static class Response {
		private String success;
		private String member;
		
		public Response(boolean success, Member member) {
			this.success = Boolean.toString(success);
			this.member = Transfer.toJSON(member).toString();
		}
	}
}
