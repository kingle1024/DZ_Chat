package core.client.mapper;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestType {
	private String commandType;

	public RequestType(String commandType) {
		this.commandType = commandType;
	}
	
	public JSONObject toJson() {
		JSONObject ret = new JSONObject();
		ret.put("commandType", commandType);
		return ret;
	}
}

