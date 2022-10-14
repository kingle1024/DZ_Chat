package core.server;

import java.io.*;
import java.net.Socket;

import member.Member;

public abstract class Service<I extends InputStream, O extends OutputStream> {
	protected final I is;
	protected final O os;
	protected Member me;

	public Service(I is, O os) throws IOException {
		this.is = is;
		this.os = os;
	}

	public abstract void request() throws IOException;

}
