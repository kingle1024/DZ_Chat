package core.service;

import java.io.*;
import member.MemberDao;

public class LoginService extends ObjectStreamService {
	public LoginService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		super(is, os);
	}
	@Override
	public void request() throws IOException {
		try {
			String id = (String) is.readObject();
			String pw = (String) is.readObject();
			if (MemberDao.isCorrectPW(id, pw)) {
				os.writeObject(Boolean.valueOf(true));
			} else {
				os.writeObject(Boolean.valueOf(false));
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

}
