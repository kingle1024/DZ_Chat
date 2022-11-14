package dto;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

import org.json.JSONException;
import org.json.JSONObject;

public class Transfer {
	private static Map<String, Constructor<?>> dtoConstructorMap = new HashMap<>();
	
	// DTO 클래스 로딩
	// java compile 옵션 -parameters
	// Eclipse: Window > Preferences > Java > Compiler > Store information about method parameters
	static {
		try {
			String src = "." + File.separator + "src" + File.separator;
			File path = new File("./src/dto");
			Queue<File> que = new LinkedList<>();
			que.add(path);
			while (!que.isEmpty()) {
				File crnt = que.poll();
				for (File next : crnt.listFiles()) {
					if (next.isDirectory()) {
						que.add(next);
					} else {
						String className = next.toString();
						className = className.replace(src, "");
						className = className.replace(".java", "");
						className = className.replace(File.separator, ".");
						List<Class<?>> classList = new ArrayList<>();
						classList.addAll(Arrays.asList(Class.forName(className).getDeclaredClasses()));
						classList.add(Class.forName(className));
						classList.stream().forEach(cls -> {
							Constructor<?> constructor = Arrays.asList(cls.getConstructors())
									.stream()
									.filter(cnstrct -> Arrays.asList(cnstrct.getParameters())
										.stream()
										.allMatch(param -> param.getType().equals(String.class)))
									.findAny()
									.orElse(null);
							dtoConstructorMap.put(cls.getName(), constructor);
						});
					}
				}
			}
		} catch (SecurityException | ClassNotFoundException e) {
		}
	}
	
	public static JSONObject toJSON(Object obj) {
		JSONObject ret = new JSONObject();
		if (obj == null) return ret;
		
		Arrays.asList(obj.getClass().getDeclaredFields())
			.stream()
			.peek(field -> field.setAccessible(true))
			.filter(field -> field.getType().equals(String.class) && !Objects.isNull(getValue(obj, field)))
			.forEach(field -> {
				try {
					ret.put(field.getName(), getValue(obj, field));
				} catch (JSONException | IllegalArgumentException e) {
				}
			});
		ret.put("__dto__", obj.getClass().getName());
		return ret;
	}
	
	public static Object toDTO(JSONObject json) {
		try {
			Constructor<?> constructor = dtoConstructorMap.get(json.getString("__dto__"));
			Object[] params = Arrays.asList(constructor.getParameters())
					.stream()
					.map(param -> json.getString(param.getName())).toArray();
			return constructor.newInstance(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String getValue(Object obj, Field field) {
		try {
			return (String) field.get(obj);
		} catch (IllegalAccessException e) {
			
		}
		return null;
	}
}
