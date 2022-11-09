package client.chat;

import java.io.*;
import java.net.Socket;

import org.json.JSONObject;

public class FileSaveClient {
	Socket socket;
	private InputStream is;
	private OutputStream os;
	
	public JSONObject run() {

		try {
			socket = new Socket("192.168.0.82", 50003);
			is = socket.getInputStream();
			os = socket.getOutputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			FileOutputStream fos = new FileOutputStream(new File("./resources/tmp.jpg"));
			int len = -1;
			byte[] buff = new byte[4096];
			while (true) {
				len = bis.read(buff);
				if (len == -1) break;
				fos.write(buff, 0, len);
			}
			fos.close();
			is.close();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
