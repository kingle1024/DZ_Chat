package message.chat;

import java.io.Serializable;
import java.util.*;

import member.Member;

public class ChatRoom implements Serializable {
	private static final long serialVersionUID = 1823559605769244050L;
	private final String roomName;
	private final List<Member> memberList;
	
	public ChatRoom(String roomName) {
		this.roomName = roomName;
		this.memberList = new ArrayList<>();
	}
	
	public void invite(Member newMember) {
		memberList.add(newMember);
	}
	
	public List<Member> getMemberList() {
		return memberList;
	}
}
