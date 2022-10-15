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
		View getChatRoomList = new MenuChooseView("채팅방 목록");
		View deleteMember = new MenuChooseView("탈퇴");
		
		main.addSubView(login);
		main.addSubView(register);
		main.addSubView(findpw);
		
		successLogin.addSubView(userInfo);
		successLogin.addSubView(getChatRoomList);
		successLogin.addSubView(deleteMember);
		
		return main;
	}
	public static void main(String[] args) {
		System.out.println("클라이언트 시작");
//		ObjectStreamClient client = new ChatClient("TEST ROOM1");
//		client.run();
		
		View view = viewInit();
		while (true) {
			view = view.act();
		}
		
	}
}
