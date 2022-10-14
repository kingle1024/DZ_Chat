package core.mapper;

import java.io.*;

import core.server.Service;

public class Command implements Serializable {
	private static final long serialVersionUID = 1232091853465992402L;
	private String commandType;
	private Object[] args;
	public Command(String commandType, Object... args) {
		this.commandType = commandType;
		this.args = args;
	}
	
	public Service response(ObjectInputStream is, ObjectOutputStream os) {
		try {
			return (Service) Class
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
}
