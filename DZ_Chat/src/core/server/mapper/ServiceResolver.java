package core.server.mapper;

import java.io.*;
import java.net.Socket;

import org.json.JSONObject;

import core.common.JSONizable;
import core.service.Service;

public class ServiceResolver implements JSONizable {
	private String commandType;
	private Object[] args;

	public ServiceResolver(String commandType, Object... args) {
		this.commandType = commandType;
		this.args = args;
	}

	public Service response(ObjectInputStream is, ObjectOutputStream os) {
		try {
			if (args.length > 0) {
				return (Service) Class.forName("core.service." + commandType)
						.getConstructor(ObjectInputStream.class, ObjectOutputStream.class, Object[].class)
						.newInstance(is, os, args);
			} else {
				return (Service) Class.forName("core.service." + commandType)
						.getConstructor(ObjectInputStream.class, ObjectOutputStream.class)
						.newInstance(is, os);
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

	@Override
	public JSONObject toJson() {
		JSONObject ret = new JSONObject();
		ret.put("commandType", commandType);
//		ret.put("paramConstructor", );
		return ret;
	}
}