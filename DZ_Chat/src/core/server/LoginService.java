package core.server;

import java.io.*;

public class LoginService extends Service<ObjectInputStream, ObjectOutputStream> {
	public LoginService(ObjectInputStream is, ObjectOutputStream os, Object... args) throws IOException {
		super(is, os);
	}
	@Override
	public void request() throws IOException {
		try {
			String id = (String) is.readObject();
			String pw = (String) is.readObject();
			if (Server.memberMap.get(id).getPassword().equals(pw)) {
				os.writeObject(Boolean.valueOf(true));
			} else {
				os.writeObject(Boolean.valueOf(false));
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
