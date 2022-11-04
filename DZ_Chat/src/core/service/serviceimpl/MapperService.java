package core.service.serviceimpl;

import java.io.*;
import java.net.Socket;
import java.util.List;

import org.json.JSONObject;

import core.service.Service;
import core.service.ServiceMap;

public class MapperService extends Service {
	
	
	private Socket socket;
	private BufferedReader br;
	private BufferedWriter bw;

	public MapperService(Socket socket) throws IOException {
		this.socket = socket;
	}

	@Override
	public void request() throws IOException {
		System.out.println("Mapper Service");
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			JSONObject commandJSON = receive();
			String commandType = commandJSON.getString("commandType");
			List<Object> params = commandJSON.getJSONArray("paramConstructor").toList();
			System.out.println("Receive ServiceResolver: " + commandType);
			Service mapping = ServiceMap.getService(commandType, params);
			mapping.setInputStream(br);
			mapping.setOutputStream(bw);
			mapping.request();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
