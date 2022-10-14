package core.server;

import java.io.*;

import core.mapper.Command;

public class MapperService extends Service {

	public MapperService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		super(is, os);
	}

	@Override
	public void request() throws IOException {
		System.out.println("Mapper Service");
		try {
			Command cmd = (Command) is.readObject();
			Service mapping = cmd.response(is, os);
			mapping.request();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

}
