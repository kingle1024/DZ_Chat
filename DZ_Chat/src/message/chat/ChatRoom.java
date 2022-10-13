package message.chat;

import java.util.*;

import member.Member;

public class ChatRoom {
	private final String name;
	private final List<Member> memberList;
	private final List<Message> messageList;
	
	public ChatRoom(String name) {
		this.name = name;
		this.memberList = new ArrayList<>();
		this.messageList = new ArrayList<>();
	}
	
	public void invite(Member newMember) {
		memberList.add(newMember);
	}
}
