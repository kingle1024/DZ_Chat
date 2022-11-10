package message.ftp;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

import static message.ftp.FtpService.fileValid;

public class FileSendThread extends Thread {
	private final HashMap<String, Object> map;
	public FileSendThread(ThreadGroup threadGroup, HashMap<String, Object> map) {
		super(threadGroup, (String) map.get("threadName")); // 스레드 그룹과 스레드 이름을 설정
		this.map = map;
	}

	@Override
	public void run() {
		String command = (String) map.get("command");
		if (command.startsWith("#fileStop")) {
			System.out.println("fileStop");
			return;
		}

		String filePathAndName = (String) map.get("fileAndPath");
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
