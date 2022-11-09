package server.service.serviceimpl.chat;

import java.io.*;

public class FileSaveService {
	private InputStream is;
	private OutputStream os;
	
	public FileSaveService(InputStream is, OutputStream os) {
		this.is = is;
		this.os = os;
	}
	
	public void run() {
		try {
			FileInputStream fis = new FileInputStream(new File("C:\\Users\\KOSA\\Desktop\\images.jpg"));
			BufferedOutputStream bos = new BufferedOutputStream(os);
			int len = -1;
			byte[] buff = new byte[4096];
			while (true) {
				len = fis.read(buff);
				if (len == -1) break;
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
