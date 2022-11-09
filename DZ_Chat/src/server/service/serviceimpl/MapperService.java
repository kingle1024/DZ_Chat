package server.service.serviceimpl;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import server.service.Service;
import server.service.ServiceMap;

public class MapperService extends Service {
	private Socket socket;

	public MapperService(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void request() throws IOException {
		System.out.println("Mapper Service");
		try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            setInputStream(br);
            setOutputStream(bw);
			
            JSONObject commandJSON = receive();
			System.out.println("MapperService receive: " + commandJSON);
			
			String commandType = commandJSON.getString("commandType");
			
			Service mapping = ServiceMap.getService(commandType);
			mapping.setInputStream(br);
			mapping.setOutputStream(bw);
			mapping.request();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
