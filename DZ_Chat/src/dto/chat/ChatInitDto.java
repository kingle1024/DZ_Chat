package dto.chat;

import dto.Transfer;
import member.Member;

public class ChatInitDto {
	public static class Request {
		private String chatRoomName;
		private String me;
		
		public Request(String chatRoomName, Member me) {
			this.chatRoomName = chatRoomName;
			this.me = Transfer.toJSON(me).toString();
		}
	}
	
}
