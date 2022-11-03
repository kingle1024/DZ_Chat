package core.client.mapper;

import org.json.JSONObject;

import core.common.JSONizable;

public class RequestType implements JSONizable {
	private String commandType;
	private JSONObject paramConstructor;

	public RequestType(String commandType) {
		this.commandType = commandType;
	}
	
	public RequestType(String commandType, JSONObject paramConstructor) {
		this.commandType = commandType;
		this.paramConstructor = paramConstructor;
	}
	

	@Override
	public JSONObject toJson() {
		JSONObject ret = new JSONObject();
		ret.put("commandType", commandType);
		if (paramConstructor != null) ret.put("paramConstructor", paramConstructor.toString());
		return ret;
	}
}

