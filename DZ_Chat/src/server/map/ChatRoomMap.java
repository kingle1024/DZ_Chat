package server.map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import message.chat.ChatRoom;

public class ChatRoomMap {
	public static final Map<String, ChatRoom> chatRoomMap = Collections.synchronizedMap(new HashMap<>());
	private static final String propertiesPath = "resources/chatRoom.properties";

	public static void init() {
		Properties chatRoomProperties = new Properties();
		try {
			chatRoomProperties.load(new InputStreamReader(new FileInputStream(propertiesPath), "UTF-8"));
			chatRoomProperties.forEach(
					(chatRoomName, x) -> chatRoomMap.put((String) chatRoomName, new ChatRoom((String) chatRoomName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void record() {
		System.out.println("record call");
		chatRoomMap.forEach((k, v) -> System.out.println("key: " + k));
		Properties chatRoomProperties = new Properties();
		chatRoomMap.forEach((key, value) -> chatRoomProperties.put(key, key));
		try {
			chatRoomProperties.store(new OutputStreamWriter(new FileOutputStream(new File(propertiesPath)), "UTF-8"),
					null);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
