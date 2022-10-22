package message.ftp;

import property.Property;

import java.io.*;
import java.net.UnknownHostException;
import java.util.*;

// 보내는 곳 (Client)
public class FtpClient extends Thread{
	private volatile HashMap<String, Object> map;
	public FtpClient(ThreadGroup threadGroup, String threadName, HashMap<String, Object> map) {
		super(threadGroup, threadName);
		this.map = map;
	}

	@Override
	public void start() {
		String chatRoomName = (String) map.get("chatRoomName");
		String chat = (String) map.get("chat");
		String fileName = chat.split(" ")[1];
		HashMap<String, Object> map = new HashMap<>();
		StringBuilder sb = new StringBuilder();

		sb.append(chatRoomName)
				.append("/")
				.append(fileName);
		File file = new File(sb.toString());

		map.put("chatRoomAndFileName", sb.toString());
		map.put("fileName", file.getName());

		try {
			// 서버 연결
			System.out.println("FtpClient > run() > ");

			// 파일 받기
			saveFile(map);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("FtpClient > UnknownHostException");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("FtpClient > IOException");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public void saveFile(HashMap<String, Object> map) throws Exception {
		String chatRoomAndFileName = (String) map.get("chatRoomAndFileName");
		try {
			FtpService ftp = new FtpService();
			StringBuffer downloadPath = new StringBuffer(
							ftp.getDownloadPath((String)map.get("fileName")));
			String filePath = Property.server().get("DOWNLOAD_PATH")+chatRoomAndFileName;

			if(ftp.sendTargetFileInputStream(filePath, downloadPath.toString())){
				ftp.showPicture(downloadPath);
				System.out.println("FtpClient > start() > 파일 저장이 완료되었습니다.");
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
