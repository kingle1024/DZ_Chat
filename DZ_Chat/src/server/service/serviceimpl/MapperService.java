package server.service.serviceimpl;

import java.io.*;
import java.net.Socket;

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
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            setInputStream(dis);
            setOutputStream(dos);
            System.out.println("Mapper: waiting receive");
            JSONObject commandJSON = receive();
			System.out.println("MapperService receive: " + commandJSON);
			
			String commandType = commandJSON.getString("commandType");
			
			Service mapping = ServiceMap.getService(commandType);
			mapping.setInputStream(dis);
			mapping.setOutputStream(dos);
			mapping.request();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
