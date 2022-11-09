package message.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class FileCommon {
	private String os;
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
			e.printStackTrace();
			return false;
		}

		return true;		
	}
	
	public boolean isAbsolutPath(String path) {
		switch (System.getProperty("os.name")) {
		case "Window":
			return path.matches("([A-Z]):\\\\");
		case "Max":
			return path.startsWith("/");
		default:
			throw new IllegalArgumentException("지원하지 않는 OS 입니다.");
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
		System.out.println("fileName: " + fileName);
		file = new File(storeDirPath);
		file.mkdirs();

		long idx = Arrays.asList(file.list()).stream()
			.filter(x -> x.matches(fileName + "(\\([0-9])?.*"))
			.map(x -> x.replaceAll("[^0-9]*", ""))
			.filter(x -> !"".equals(x))
			.map(Long::parseLong)
			.max(Long::compare)
			.orElse(-1L);
		return idx == -1
				? storeDirPath + fileFullName
				: storeDirPath + files[0] + "(" + (idx+1) + ")" + files[1];
	}
}
