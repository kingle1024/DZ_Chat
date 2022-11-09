package client.chat;

import java.io.*;
import java.net.Socket;

import org.json.JSONObject;

public class FileSendClient {
	Socket socket;
	private InputStream is;
	private OutputStream os;
	
	public JSONObject run() {

		try {
			socket = new Socket("localhost", 50003);
			is = socket.getInputStream();
			os = socket.getOutputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			FileOutputStream fos = new FileOutputStream(new File("./resources/tmp.jpg"));
			byte[] buff = new byte[4096];
			while (bis.read(buff) != -1) {
				fos.write(buff);
				System.out.println("write");
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
