package dto;

import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

public class Transfer {
	public static JSONObject toJSON(Object obj) {
		JSONObject ret = new JSONObject();
		Arrays.asList(obj.getClass().getDeclaredFields())
			.stream()
			.peek(x -> x.setAccessible(true))
			.filter(x -> x.getType().equals("".getClass()))
			.forEach(x -> {
				try {
					ret.put(x.getName(), (String) x.get(obj));
				} catch (JSONException | IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			});
		ret.put("type", obj.getClass().toString());
		return ret;
	}
}
