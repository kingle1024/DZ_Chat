package core.service;

import java.io.*;


public class MapperService extends ObjectStreamService {
	public MapperService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		super(is, os);
	}

	@Override
	public void request() throws IOException {
		System.out.println("Mapper Service");
		try {
			// TODO receive JSON
//			RequestType cmd = (RequestType) is.readObject();
			
//			System.out.println("Receive ServiceResolver: "+ cmd.getCommandType());
//			ObjectStreamService mapping = cmd.response(is, os);
//			mapping.request();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
