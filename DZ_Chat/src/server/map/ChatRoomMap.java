package server.map;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import message.chat.ChatRoom;

public class ChatRoomMap {
	public static final Map<String, ChatRoom> chatRoomMap = Collections.synchronizedMap(new HashMap<>());

	public static void init() {
		Properties chatRoomProperties = new Properties();
		try {
			chatRoomProperties.load(new FileInputStream(new File("resources/chatRoom.properties")));
			chatRoomProperties.forEach((chatRoomName, x) 
					-> chatRoomMap.put((String)chatRoomName, new ChatRoom((String)chatRoomName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void record() {
		
	}
	
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
	
	public static ChatRoom remove(String key) {
		return chatRoomMap.remove(key);
	}
}
