package dto;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Transfer {
	
	private static String getValue(Object obj, Field field) {
		try {
			return (String) field.get(obj);
		} catch (IllegalAccessException e) {
			
		}
		return null;
	}
	
	public static String getUTF8(String str) {
		byte[] data;
		try {
			if (str == null) return null;
			data = str.getBytes("utf8");
			return new String(data, "utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject toJSON(Object obj) {
		if (obj == null) return null;
		JSONObject ret = new JSONObject();
		
		Arrays.asList(obj.getClass().getDeclaredFields())
			.stream()
			.peek(x -> x.setAccessible(true))
			.filter(x -> x.getType().equals(String.class) && !Objects.isNull(getValue(obj, x)))
			.forEach(x -> {
				try {
					ret.put(x.getName(), getValue(obj, x));
				} catch (JSONException | IllegalArgumentException e) {
				}
			});
		return ret;
	}
}
