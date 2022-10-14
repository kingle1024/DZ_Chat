package core.server;

import java.io.*;
import java.net.Socket;

import member.Member;

public abstract class Service {
	protected final ObjectInputStream is;
	protected final ObjectOutputStream os;
	protected Member me;

	public Service(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		this.is = is;
		this.os = os;
	}

	public abstract void request() throws IOException;

}
