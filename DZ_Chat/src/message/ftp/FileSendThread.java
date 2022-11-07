package message.ftp;

import property.Property;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

import static message.ftp.FtpService.fileValid;

public class FileSendThread extends Thread {
	private volatile HashMap<String, Object> map;
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
		String fileAndPath = (String) map.get("fileAndPath");
		File originFile = new File(fileAndPath);

		System.out.println("ClientToServer > run() > fileName:" + fileAndPath);
		Socket socket = (Socket) map.get("socket");
		try {
			if (fileAndPath.startsWith("/") || fileAndPath.startsWith("C:\\") || fileAndPath.startsWith("D:\\")) {

			} else {
//				fileAndPath = "DZ_Chat/" + fileAndPath;
			}

			if (!fileValid(fileAndPath))
				return;

			StringBuilder fileSaveTargetSb = new StringBuilder();
			fileSaveTargetSb.append(Property.server().get("DOWNLOAD_PATH")).append(map.get("chatRoomName")).append("/")
					.append(originFile.getName());

			byte[] buffer = new byte[Integer.parseInt(Property.client().get("DEFAULT_BUFFER_SIZE"))];
			File originFileTarget = new File(fileAndPath);
			System.out.println("FtpService > sendFile() > 여기에 보내는 파일이 있음 ! > " + fileAndPath);
			long fileSize = originFileTarget.length();

			// 여기에 파일이 있음
			InputStream fis = new FileInputStream(originFileTarget);

			// 앞으로 저장할 파일
			OutputStream os = socket.getOutputStream();
			int readBytes;
			long totalReadBytes = 0;
			int cnt = 0;
			int loopTime = 2;
			while ((readBytes = fis.read(buffer)) > 0) {
				Thread.sleep(1);
				os.write(buffer, 0, readBytes); // 실질적으로 보내는 부분
				totalReadBytes += readBytes;
				if (cnt % loopTime == 0) {
					System.out.println("sendFile In progress: " + totalReadBytes + "/" + fileSize + " Byte(s) ("
							+ (totalReadBytes * 100 / fileSize) + " %)");
				}
				cnt++;
			}
			if ((cnt - 1) % loopTime != 0) {
				System.out.println("sendFile In progress: " + totalReadBytes + "/" + fileSize + " Byte(s) ("
						+ (totalReadBytes * 100 / fileSize) + " %)");
			}

			System.out.println("Client에서 파일 전송 완료");
			os.close();
		} catch (IOException e) {
			System.out.println("ClientToServerThread IOException");
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			System.out.println("파일 전송 중지");
		}
	}
}
