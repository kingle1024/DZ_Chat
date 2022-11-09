package message.ftp;

import property.ServerProperties;

import java.io.*;
import java.net.UnknownHostException;
import java.util.*;

// 보내는 곳 (Client)
public class FileSaveThread extends Thread{
	private volatile HashMap<String, Object> map;
	
	public FileSaveThread(ThreadGroup threadGroup, HashMap<String, Object> map) {
		super(threadGroup, map.get("threadName").toString());
		this.map = map;
	}

	@Override
	public void start() {
		String saveRoomAndFileNameStr = getSaveRoomAndFileName();
		File file = new File(saveRoomAndFileNameStr);

		map.put("chatRoomAndFileName", saveRoomAndFileNameStr);
		map.put("fileName", file.getName());

		try {
			System.out.println("FtpClient > run() > ");
			saveFile();
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
	public void saveFile() throws Exception {
		String chatRoomAndFileName = (String) map.get("chatRoomAndFileName");
		File file = new File(chatRoomAndFileName);
		System.out.println("chatRoomAndFileName:"+chatRoomAndFileName);
		System.out.println("file:"+file.getAbsolutePath());
		
		try {
			FtpService ftp = new FtpService();
			String filePath = ServerProperties.getDownloadPath() +chatRoomAndFileName;

			StringBuffer downloadPath =
					new StringBuffer(ftp.getDownloadPath((String)map.get("fileName")));

			if(ftp.sendTargetFileInputStream(filePath, downloadPath.toString())){
				ftp.showPicture(downloadPath);
				System.out.println("FtpClient > start() > 파일 저장이 완료되었습니다.");
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public String getSaveRoomAndFileName(){
		String chatRoomName = (String) map.get("chatRoomName");
		File originFileAndPath = new File((String)map.get("fileAndPath"));
		StringBuilder saveRoomAndFileName = new StringBuilder();
		saveRoomAndFileName.append(chatRoomName)
				.append("/")
				.append(originFileAndPath.getName());
		return saveRoomAndFileName.toString();
	}
}
