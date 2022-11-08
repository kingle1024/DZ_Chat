package log;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import property.ServerProperties;

public class LogManager {
	private static List<LogManagerInterface> impls = new ArrayList<>();

	static {
		try {
			Properties clsNames = new Properties();
			clsNames.load(new FileInputStream(ServerProperties.getConnectProperties()));
			for (Entry<Object, Object> entry : clsNames.entrySet()) {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				if (!key.startsWith("log")) continue;
				String[] cls = value.split(",");
				System.out.println(Arrays.toString(cls));
				if (cls.length > 1) {
					impls.add((LogManagerInterface) Class.forName(cls[0]).getConstructor(String.class)
							.newInstance(cls[1]));
				} else {
					impls.add((LogManagerInterface) Class.forName(cls[0]).getConstructor().newInstance());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void logSave(Log log) {
		impls.stream().forEach(x -> x.logSave(log));
	}
}
