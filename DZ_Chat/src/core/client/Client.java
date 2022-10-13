package core.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import member.Member;

public class Client {
	Socket socket;
	InputStream is;
	OutputStream os;
	Member member;
}
