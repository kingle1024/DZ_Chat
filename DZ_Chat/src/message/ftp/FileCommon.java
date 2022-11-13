package message.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;

import property.ClientProperties;

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

	public String makeFileName(String storeDirPath, String filePath) {
		String[] files = filePath.split("\\.");
		if(files.length < 2){
			throw new IllegalArgumentException("파일명이 올바르지 않습니다. (ex test.txt)");
		}

		File file = new File(filePath);
		String fileFullName = file.getName();
		String fileName = fileFullName.replaceAll("\\..*", "");
		file = new File(storeDirPath);
		file.mkdirs();

		long idx = Arrays.stream(Objects.requireNonNull(file.list()))
				.filter(x -> x.matches(fileName + "(\\([0-9])?.*"))
				.map(x -> x.replaceAll("[^0-9]+", ""))
				.map(x -> 0 + x)
				.map(Long::parseLong)
				.max(Long::compare)
				.orElse(-1L);
		return idx == -1
				? storeDirPath + fileFullName
				: storeDirPath + files[0] + "(" + (idx+1) + ")." + files[1];
	}

	public void fileSend(String filePathAndName, Socket socket) throws IOException, InterruptedException {
		File originFileTarget = new File(filePathAndName);
		InputStream fis = new FileInputStream(originFileTarget);
		OutputStream os = socket.getOutputStream();

		long fileSize = originFileTarget.length();
		byte[] buffer = new byte[ClientProperties.getDefaultBufferSize()];

		System.out.println("FtpService > sendFile() > 여기에 보내는 파일이 있음 ! > " + originFileTarget.getAbsolutePath());

		int readBytes;
		long totalReadBytes = 0;
		int cnt = 0;
		int loopTime = 2;
		long percent = -1;
		
		while ((readBytes = fis.read(buffer)) > 0) {
//			Thread.sleep(1);
			os.write(buffer, 0, readBytes); // 실질적으로 보내는 부분
			totalReadBytes += readBytes;
			percent = printStreamProgress(cnt++, loopTime, totalReadBytes, fileSize);
		}
		if (percent != 100) printStreamProgress(0, 1, totalReadBytes, fileSize);
		os.close();
		fis.close();
	}

	private long printStreamProgress(int cnt, int loopTime, long totalReadBytes, long fileSize) {
		if (cnt % loopTime == 0) {
			long percent = totalReadBytes * 100 / fileSize;
			System.out.println("sendFile In progress: " + totalReadBytes + "/" + fileSize + " Byte(s) ("
					+ percent + " %)");
			return percent;
		}
		return -1;
	}

	public static void main(String[] args) {
		FileCommon fc = new FileCommon();
		System.out.println(fc.makeFileName("C:\\Users\\KOSA\\Desktop\\", "images.jpg"));
	}
}
