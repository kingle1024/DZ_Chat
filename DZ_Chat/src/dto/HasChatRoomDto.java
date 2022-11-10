package dto;

public class HasChatRoomDto {
	public static class Request {
		private String chatRoomName;

		public Request(String chatRoomName) {
			this.chatRoomName = chatRoomName;
		}
	}
}
