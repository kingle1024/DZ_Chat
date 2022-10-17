package core.service;

import java.io.*;
import java.net.Socket;

import core.mapper.ServiceResolver;

public class MapperService extends ObjectStreamService {
	public MapperService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		super(is, os);
	}

	@Override
	public void request() throws IOException {
		System.out.println("Mapper Service");
		try {
			ServiceResolver cmd = (ServiceResolver) is.readObject();
			
			System.out.println("Receive ServiceResolver: "+ cmd.getCommandType());
			ObjectStreamService mapping = cmd.response(is, os);
			mapping.request();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

}
