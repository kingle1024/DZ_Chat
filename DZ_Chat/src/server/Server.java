package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public interface Server {
	void start() throws IOException;
	void stop() throws IOException;
}