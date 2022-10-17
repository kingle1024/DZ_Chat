package core.mapper;

import java.io.*;
import java.net.Socket;

import core.service.ObjectStreamService;

public class ServiceResolver implements Serializable {
	private static final long serialVersionUID = 6041049640297416804L;
	private String commandType;
	private Object[] args;

	public ServiceResolver(String commandType, Object... args) {
		this.commandType = commandType;
		this.args = args;
		System.out.println(commandType);
	}

	public ObjectStreamService response(ObjectInputStream is, ObjectOutputStream os) {
		try {
			if (args.length > 0) {
				return (ObjectStreamService) Class.forName("core.service." + commandType)
						.getConstructor(ObjectInputStream.class, ObjectOutputStream.class, Object[].class)
						.newInstance(is, os, args);
			} else {
				return (ObjectStreamService) Class.forName("core.service." + commandType)
						.getConstructor(ObjectInputStream.class, ObjectOutputStream.class)
						.newInstance(is, os, args);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object[] getArgs() {
		return args;
	}

	public String getCommandType() {
		return commandType;
	}
}
