package server.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import message.chat.ChatRoom;

public class ChatRoomMap {
	public static final Map<String, ChatRoom> chatRoomMap = Collections.synchronizedMap(new HashMap<>());

	public static ChatRoom get(String chatRoomName) {
		return chatRoomMap.get(chatRoomName);
	}
	
	public static ChatRoom put(String chatRoomName, ChatRoom chatRoom) {
		return chatRoomMap.put(chatRoomName, chatRoom);
	}
	
	public static int size() {
		return chatRoomMap.size();
	}
	
	public static Set<String> keySet() {
		return chatRoomMap.keySet();
	}
	
	public static boolean containsKey(String key) {
		return chatRoomMap.containsKey(key);
	}
}
