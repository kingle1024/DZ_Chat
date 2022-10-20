package core.client;

import core.client.chat.ChatClient;
import core.client.chat.GetChatRoomListClient;
import core.client.chat.HasChatRoomClient;
import core.client.chat.MakeChatRoomClient;
import core.client.member.*;
import core.client.view.*;
import member.Member;

import java.util.*;

public class Main {
	private static Member me;
	private static boolean hasChatRoom(String chatRoomName) {
		HasChatRoomClient hasChatClient = new HasChatRoomClient(chatRoomName);
		hasChatClient.run();
		return hasChatClient.getHasGetRoom();
	}

	public static View viewInit() {
//		System.out.println("View 생성");
		Scanner scanner = new Scanner(System.in);
		View main = new MenuChooseView("메인화면");
		View login = new TextInputView("로그인", (str) -> {
			String id = str.get(0);
			String pw = str.get(1);
			LoginClient loginClient = new LoginClient(id, pw);
			loginClient.run();
			if (loginClient.getMember() != null) {
				me = loginClient.getMember();
				return "로그인 성공";
			} else {
				return "메인화면";
			}

		}, "id", "pw");
		View register = new TextInputView("회원가입", (str) -> {
			String id = str.get(0);
			String pw = str.get(1);
			String pwChk = str.get(2);
			String name = str.get(3);
			String birth = str.get(4);
			Member tmp = new Member(id, pw, name, birth);
			System.out.println("HERE" + tmp);
			RegisterClient registerClient = new RegisterClient(tmp, pwChk);
			registerClient.run();
			if (registerClient.getRegisterSuccess()) {
				return "로그인";
			} else {
				System.out.println("회원가입 실패");
				return "메인화면";
			}
		}, "id", "pw", "pwChk", "name", "birth");
		View findpw = new TextInputView("비밀번호 찾기", (str) -> {
			String id = str.get(0);
			new FindPWClient(id).run();
			return "메인화면";
		}, "id");

		View successLogin = new MenuChooseView("로그인 성공");
		View userInfo = new MenuChooseView("회원정보", () -> {
			System.out.println(me);
		});
		View getChatRoomList = new TextInputView("채팅방 목록", (str) -> {
			new GetChatRoomListClient().run();
			return "로그인 성공";
		});

		View makeChatRoom = new TextInputView("채팅방 만들기", (str) -> {
			String chatRoomName = str.get(0);
			if (hasChatRoom(chatRoomName)) {
				System.out.println("이미 존재하는 채팅방 이름입니다.");
			} else {
				new MakeChatRoomClient(chatRoomName).run();
				new ChatClient(chatRoomName, me).run();
			}
			return "로그인 성공";
		}, "만들 채팅방 이름을 입력하세요.");

		View entranceChatRoom = new TextInputView("채팅방 입장", (str) -> {
			String chatRoomName = str.get(0);
			if (hasChatRoom(chatRoomName)) {
				ChatClient client = new ChatClient(chatRoomName, me);
				client.run();
//				while (client.getSendExit()) {
//					client = new ChatClient(chatRoomName, me);
//					client.run();
//				}
			} else {
				System.out.println("존재하지 않는 채팅방 입니다.");
			}
			return "로그인 성공";
		}, "입장할 채팅방 이름을 입력하세요.");
		
		View updateMember = new TextInputView("회원 비밀번호 수정", (str) -> {
			String validatePW = str.get(0);
			String newPW = str.get(1);
			UpdatePWClient updatePWClient = new UpdatePWClient(me, validatePW, newPW);
			updatePWClient.run();
			if (updatePWClient.getUpdateSuccess()) {
				me.setPassword(newPW);
				return "로그인 성공";	
			} else {
				System.out.println("비밀번호 불일치");
				return "회원정보";
			}
			
		}, "기존 비밀번호 입력", "새로운 비밀번호 입력");
		View deleteMember = new TextInputView("탈퇴", (str) -> {
	         String pw = str.get(0);
	         DeleteClient deleteClient = new DeleteClient(me, pw);
	         deleteClient.run();
	         if (deleteClient.getDeleteSuccess()) {
	            me = null;
	            return "메인화면";
	         } else {
	            return "회원정보";
	         }

	      }, "pw");

		main.addSubView(login);
		main.addSubView(register);
		main.addSubView(findpw);

		successLogin.addSubView(userInfo);
		successLogin.addSubView(getChatRoomList);
		successLogin.addSubView(entranceChatRoom);
		successLogin.addSubView(makeChatRoom);

		userInfo.addSubView(updateMember);
		userInfo.addSubView(deleteMember);

		getChatRoomList.addSubView(entranceChatRoom);

		return main;
	}

	public static void main(String[] args) {
		System.out.println("클라이언트 시작");
		View view = viewInit();
		while (true) {
			System.out.println(view.getName());
			view = view.act();
		}
	}
}
