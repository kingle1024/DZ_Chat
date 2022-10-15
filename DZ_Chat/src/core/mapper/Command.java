package core.mapper;

import java.io.*;
import java.net.Socket;

import core.server.ObjectStreamService;

public class Command implements Serializable {
	private static final long serialVersionUID = 6041049640297416804L;
	private String commandType;
	private Object[] args;
	public Command(String commandType, Object... args) {
		this.commandType = commandType;
		this.args = args;
	}
	
	public ObjectStreamService response(ObjectInputStream is, ObjectOutputStream os) {
		try {
			return (ObjectStreamService) Class
					.forName("core.server." + commandType)
					.getConstructor(ObjectInputStream.class, ObjectOutputStream.class, Object[].class)
					.newInstance(is, os, args);
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
