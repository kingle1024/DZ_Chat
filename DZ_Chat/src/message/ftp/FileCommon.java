package message.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCommon {
	/**
	 * 
	 * @param isAppend true이면 연속해서 파일 작성, false이면 새로 파일 작성
	 */
	public boolean saveContent(String filePath, String message, boolean isAppend) {
		try {
			FileOutputStream f = new FileOutputStream(filePath, isAppend);
			byte[] byteArr = message.getBytes(); //converting string into byte array
			f.write(byteArr);
			f.close();

			//채팅시간, 유저아디@ip, 채팅내용
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
			return false;
		}

		return true;		
	}
	public String fileNameBalance(String path, String fileName) {
		String[] files;
		try {
			files = fileName.split("\\.");
		}catch(Exception e) {
			System.out.println(fileName + " : 파일명이 올바르지 않습니다. (Ex test.txt) ");
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

		System.out.println("사용할 파일위치와 파일명 : "+sbFileName.toString());
		return sbFileName.toString();
	}
}
