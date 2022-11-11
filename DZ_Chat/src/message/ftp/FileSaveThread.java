package message.ftp;

import org.json.JSONObject;

import dto.chat.ChatInfo;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

// 보내는 곳 (Client)
public class FileSaveThread extends Thread{
	private final JSONObject map;
	private final ChatInfo chatInfo;
	
	public FileSaveThread(ThreadGroup threadGroup, JSONObject map) {
		super(threadGroup, map.get("threadName").toString());
		this.map = map;
		this.chatInfo = (ChatInfo) map.get("chatInfo");
	}

	@Override
	public void start() {
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
		try {
			FtpService ftp = new FtpService();
			StringBuffer downloadPath =
					new StringBuffer(ftp.getDownloadPath(chatInfo.getFilePath()));
			Socket socket = (Socket)map.get("socket");

			if(ftp.saveFile(socket.getInputStream(), downloadPath.toString())){
				ftp.showPicture(downloadPath);
				System.out.println("FtpClient > start() > 파일 저장이 완료되었습니다.");
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
