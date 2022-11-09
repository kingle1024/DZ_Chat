package message.ftp;

import property.ClientProperties;

import java.io.*;
import java.net.Socket;

public class FileCommon {
	/**
	 * @param isAppend true이면 연속해서 파일 작성, false이면 새로 파일 작성
	 */
	public void saveContent(String filePath, String message, boolean isAppend) {
		try {
			FileOutputStream f = new FileOutputStream(filePath, isAppend);
			byte[] byteArr = message.getBytes(); //converting string into byte array
			f.write(byteArr);
			f.close();

			//채팅시간, 유저아디@ip, 채팅내용
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public String fileNameBalance(String path, String fileName) {
		String[] files = fileName.split("\\.");
		System.out.println("file1:"+files[0]);
		if(files.length < 2){ 
			System.out.println("파일명이 올바르지 않습니다. (ex test.txt)");
			return null;
		}

		File file = new File(fileName);
		fileName = file.getName();

		int idx = 1;
		StringBuilder sbFileName = new StringBuilder(path + fileName);

		file = new File(path);
		if(!new File(path).exists()) {
			file.mkdirs();
		}
		//경로에 폴더가 있는지 확인
		System.out.println("files:"+files[0]);
		while(true) {
			file = new File(sbFileName.toString());

			if (file.exists()) {
				sbFileName = new StringBuilder(
						path + files[0] + " ("+idx+")." + files[1]);
				idx++;
			}else {
				break;
			}
		}
		
		System.out.println("사용할 파일위치와 파일명 : "+sbFileName);
		return sbFileName.toString();
	}

	public void fileSave(String filePathAndName, Socket socket) throws IOException, InterruptedException {
		File originFileTarget = new File(filePathAndName);

		byte[] buffer = new byte[Integer.parseInt(ClientProperties.getDefaultBufferSize())];
		System.out.println("FtpService > sendFile() > 여기에 보내는 파일이 있음 ! > " + originFileTarget.getAbsolutePath());
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
		os.close();
	}
}
