package core.client;

import core.client.view.*;
import java.util.*;

public class Main {
	public static View viewInit() {
		System.out.println("View 생성");
		Scanner scanner = new Scanner(System.in);
		View main = new MenuChooseView("메인화면");
		View login = new TextInputView("로그인", (str) -> "메인화면", "id", "pw");
		View register = new TextInputView("회원가입", (str) -> "메인화면", "id", "pw", "pwdChk", "name", "birth");
		View findpw = new TextInputView("비밀번호 찾기", (str) -> "메인화면", "id", "name", "birth");
		
		View successLogin = new MenuChooseView("로그인 성공");
		View userInfo = new MenuChooseView("회원정보");
		View getChatRoomList = new TextInputView("채팅방 목록", (str) -> {
			new GetChatRoomListClient().run();
			return "로그인 성공";
		});
		View deleteMember = new MenuChooseView("탈퇴");
		View makeChatRoom = new TextInputView("채팅방 만들기", (str) -> {
			new MakeChatRoomClient().run();
			return "로그인 성공";
		}, "만들 채팅방 이름을 입력하세요.");
		
		View entranceChatRoom = new TextInputView("채팅방 입장", (str) -> {
			String chatRoomName = str.get(0);
			HasChatRoomClient hasChatClient = new HasChatRoomClient(chatRoomName);
			hasChatClient.run();
			if (hasChatClient.getHasGetRoomClient()) {
				System.out.println("채팅방 입장aaaaaa");
				new ChatClient(chatRoomName).run();
			} else {
				System.out.println("존재하지 않는 채팅방 입니다.");
			}			
			return "로그인 성공";
		}, "입장할 채팅방 이름을 입력하세요.");
		
		main.addSubView(login);
		main.addSubView(register);
		main.addSubView(findpw);
		
		successLogin.addSubView(userInfo);
		successLogin.addSubView(getChatRoomList);
		successLogin.addSubView(entranceChatRoom);
		successLogin.addSubView(makeChatRoom);
		successLogin.addSubView(deleteMember);
		
		getChatRoomList.addSubView(entranceChatRoom);
		
		return successLogin;
	}
	public static void main(String[] args) {
		System.out.println("클라이언트 시작");		
		View view = viewInit();
		while (true) {
			view = view.act();
		}
	}
}
