package core.server;

import java.io.*;
import java.net.Socket;

import member.Member;

public abstract class ObjectStreamService {
	protected ObjectInputStream is;
	protected ObjectOutputStream os;
	protected Member me;

	public ObjectStreamService(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		this.is = is;
		this.os = os;
	}

	public abstract void request() throws IOException;

}
