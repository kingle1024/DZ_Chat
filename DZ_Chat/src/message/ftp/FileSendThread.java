package message.ftp;

import dto.ChatInfo;
import org.json.JSONObject;
import java.io.*;
import java.net.Socket;

import static message.ftp.FtpService.fileValid;

public class FileSendThread extends Thread {
	private final JSONObject map;
	private final ChatInfo chatInfo;
	public FileSendThread(ThreadGroup threadGroup, JSONObject map) {
		super(threadGroup, (String) map.get("threadName")); // 스레드 그룹과 스레드 이름을 설정
		this.map = map;
		this.chatInfo = (ChatInfo) map.get("chatInfo");
	}

	@Override
	public void run() {
		String command = chatInfo.getCommand();
		if (command.startsWith("#fileStop")) {
			System.out.println("fileStop");
			return;
		}

		String filePathAndName = chatInfo.getFilePath();
		System.out.println("ClientToServer > run() > fileName:" + filePathAndName);

		try {
			if (!fileValid(filePathAndName)) return;
			new FileCommon().fileSave(filePathAndName, (Socket) map.get("socket"));
			System.out.println("클라이언트에서 파일 전송 완료");
		} catch (IOException e) {
			System.out.println("ClientToServerThread IOException");
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			System.out.println("파일 전송 중지");
		}
	}
}
