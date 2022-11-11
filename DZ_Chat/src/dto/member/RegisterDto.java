package dto.member;

import dto.Transfer;
import member.Member;

public class RegisterDto {
	public static class Request {
		private String member;
		private String pwChk;
		
		public Request(Member member, String pwChk) {
			this.member = Transfer.toJSON(member).toString();
			this.pwChk = pwChk;
		}
	}
}
