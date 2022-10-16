package core.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class Server {
	protected final InetAddress HOST;
	protected final int PORT_NUMBER;
	public Server(int port) throws UnknownHostException {
		HOST = InetAddress.getLocalHost();
		PORT_NUMBER = port;
	}
	public abstract void start() throws IOException;
	public abstract void stop() throws IOException;
}
