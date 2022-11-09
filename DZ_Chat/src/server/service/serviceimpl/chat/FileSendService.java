package server.service.serviceimpl.chat;

import java.io.*;

public class FileSendService {
	private InputStream is;
	private OutputStream os;
	
	public FileSendService(InputStream is, OutputStream os) {
		this.is = is;
		this.os = os;
	}
	
	public void run() {
		try {
			FileInputStream fis = new FileInputStream(new File("C:\\Users\\KOSA\\Desktop\\images.jpg"));
			BufferedOutputStream bos = new BufferedOutputStream(os);
			byte[] buff = new byte[4096];
			while (fis.read(buff) != -1) {
				bos.write(buff);
			}
			fis.close();
			bos.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
