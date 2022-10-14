package core.server;

import java.io.IOException;
import java.net.Socket;

import member.Member;

public abstract class Service {
	protected final Server server;
	protected final Socket socket;
	protected Member me;
	
	public Service(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
	}
	
	public abstract void request() throws IOException;
	public Socket getSocket() {
		return socket;
	}
}
