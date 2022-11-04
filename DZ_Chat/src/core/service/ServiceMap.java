package core.service;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ServiceMap {
	private static Map<String, Constructor<?>> serviceMap = new HashMap<>();

	static {
		try {
			File path = new File("./DZ_Chat/src/core/service/serviceimpl");
			Queue<File> que = new LinkedList<>();
			que.add(path);
			while (!que.isEmpty()) {
				File file = que.poll();
				for (File next : file.listFiles()) {
					if (next.isDirectory()) {
						que.offer(next);
					} else {
						String fileName = next.getName().replace("\\..*", "");
						Constructor<?> constructor = Class.forName("core.service.serviceimpl." + fileName)
								.getConstructors()[0];
						serviceMap.put(fileName, constructor);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Service getService(String serviceName, List<Object> args) {
		try {
			return (Service) serviceMap.get(serviceName).newInstance(args.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Service getService(String serviceName, Object arg) {
		try {
			return (Service) serviceMap.get(serviceName).newInstance(arg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
