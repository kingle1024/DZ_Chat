package client;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class ClientMap {
	public static Map<String, Constructor<?>> map = new HashMap<>();

	private ClientMap() {
	}

	private static Constructor<?> getConstructor(String clientName, Object... paramConstructor) {
		if (map.containsKey(clientName))
			return map.get(clientName);
		try {
			String fullClassName = "client." + clientName;
			Constructor<?> constructor = null;
			if (paramConstructor.length == 0) {
				constructor = Class.forName(fullClassName).getConstructor();
			} else {
				Class<?>[] cls = (Class[]) Arrays.asList(paramConstructor).stream().map(obj -> obj.getClass())
						.toArray(Class[]::new);
				constructor = Class.forName(fullClassName).getConstructor(cls);
			}
			map.put(clientName, constructor);
			return constructor;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JSONObject runClient(String clientName, Object... paramConstructor) {
		Client client;
		try {
			client = (Client) getConstructor(clientName, paramConstructor).newInstance(paramConstructor);
			return client.run();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
