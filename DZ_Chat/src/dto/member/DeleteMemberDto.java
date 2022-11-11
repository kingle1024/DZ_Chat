package dto.member;

import dto.Transfer;
import member.Member;

public class DeleteMemberDto {
	public static class Request {
		private String me;
		private String pw;
		
		public Request(Member me, String pw) {
			this.me = Transfer.toJSON(me).toString();
			this.pw = pw;
		}
	}
}
