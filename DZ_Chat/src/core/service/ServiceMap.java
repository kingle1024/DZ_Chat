package core.service;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ServiceMap {
	private static Map<String, Constructor<?>> serviceMap = new HashMap<>();

	static {
		try {
			File path = new File("./src/core/service/serviceimpl");
			Queue<File> que = new LinkedList<>();
			que.add(path);
			while (!que.isEmpty()) {
				File p = que.poll();
				for (File next : p.listFiles()) {
					if (next.isDirectory()) {
						que.offer(next);
					} else {
						String fileName = next.toString();
						fileName = fileName.substring(6, fileName.length()-5).replace(File.separator, ".");
						Constructor<?> constructor = Class.forName(fileName).getConstructors()[0];
						fileName = fileName.replace("core.service.serviceimpl.", "");
						serviceMap.put(fileName, constructor);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Service getService(String serviceName) {
		try {
			return (Service) serviceMap.get(serviceName).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Service getService(String serviceName, Object obj) {
		try {
			return (Service) serviceMap.get(serviceName).newInstance(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
