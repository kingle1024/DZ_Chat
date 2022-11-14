package dto.member;

import dto.Transfer;
import member.Member;

public class DeleteMemberDto {
	public static class Request {
		private String member;
		private String pw;
		
		public Request(Member me, String pw) {
			this.member = Transfer.toJSON(me).toString();
			this.pw = pw;
		}
	}
}
