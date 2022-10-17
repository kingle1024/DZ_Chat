package message.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FtpService {
	private final int DEFAULT_BUFFER_SIZE = 4;
	public static boolean fileValid(String filePath) {
		File file = new File(filePath);
		
		if (!file.exists()) {
			System.out.println("파일이 존재하지 않습니다 : "+file.getAbsolutePath());
			return false;
		}
		
		return true;
	}
	public void saveSocketFile(Socket socket, String saveFilePath) throws IOException {
		saveFile(socket.getInputStream(), saveFilePath);
	}
	public void saveTargetFile(String targetFilePath, String saveFilePath) throws IOException {
		System.out.println("FtpService > saveTargetFile : 파일 저장 위치 : "+saveFilePath);
		saveFile(new FileInputStream(targetFilePath), saveFilePath);
	}
	public void saveFile(InputStream is, String saveFilePath) throws IOException {
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		int readBytes;
		FileOutputStream fos = new FileOutputStream(saveFilePath);
		long totalReadBytes = 0;
		long fileSize = is.available();
		while ((readBytes = is.read(buffer)) != -1) {
			fos.write(buffer, 0, readBytes);
			totalReadBytes += readBytes;
			System.out.println("saveFile In progress: " + totalReadBytes + "/" + fileSize + " Byte(s) ("
					+ (totalReadBytes * 100 / fileSize) + " %)");
		}

		is.close();
		fos.close();
	}
	public String dir(String path) {
		File file = new File("resources/room/"+path+"");
		StringBuilder sb = new StringBuilder();
		if(!file.exists()) {
			sb.append("폴더에 파일이 존재하지 않습니다. ( ")
					.append(file.getAbsolutePath()).append(")");
			return sb.toString();
		}
		File[] contents = file.listFiles();
		sb.append("<전송된 파일 목록> ")
				.append(path)
				.append("\n");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a HH:mm");
		assert contents != null;
		for(File f : contents) {
			sb.append(String.format("%-25s", sdf.format(new Date(f.lastModified()))));
			if(f.isDirectory()) {
				sb.append(String.format("%-10s%-20s", "<DIR>", f.getName()));
			}else {
				sb.append(String.format("%-20s", f.getName()));
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	public void sendFile(String fileName, Socket socket) throws IOException{
		String[] input = fileName.split(" ");
		String splitFileName = input[1];
		splitFileName = "DZ_Chat/"+splitFileName;
		
		if(!fileValid(splitFileName)) return;

		// 파일이 존재하는 경로
		File file = new File(splitFileName);
		System.out.println("FtpService > sendFile() > 보내는 파일 위치 > "+file.getAbsolutePath());

		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		long fileSize = file.length();
		
		// 여기에 파일이 있음 
		InputStream fis = new FileInputStream(file);

		// 앞으로 저장할 파일
		OutputStream os = socket.getOutputStream();

		try {
			int readBytes;
			long totalReadBytes = 0;
			while ((readBytes = fis.read(buffer)) > 0
					) {
				Thread.sleep(1000);
				os.write(buffer, 0, readBytes); // 실질적으로 보내는 부분
				totalReadBytes += readBytes;
				System.out.println("sendFile In progress: " + totalReadBytes + "/" + fileSize + " Byte(s) ("
						+ (totalReadBytes * 100 / fileSize) + " %)");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

//		System.out.println("FtpService > sendFile 끝");
		
//		fis.close();
		os.close(); // close 안하면 파일에 안씀
	}
	public String getOs() {
		String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            System.out.println("Windows");
            return "win";
        } else if (os.contains("mac")) {
            System.out.println("Mac");
            return "mac";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            System.out.println("Unix");
            return "nix";
        } else if (os.contains("linux")) {
            System.out.println("Linux");
            return "linux";
        } else if (os.contains("sunos")) {
            System.out.println("Solaris");
            return "sunos";
        }
        return null;
	}
	public String getDownloadPath(String osName, StringBuffer downloadPath, String userName, String[] inputArr) {
		if(osName.contains("window")){
			downloadPath
					.append("C:\\Users\\")
					.append(userName)
					.append("\\Downloads\\");
		}else if(osName.contains("mac")) {
			downloadPath
					.append("/Users/")
					.append(userName)
					.append("/Downloads/");
		}
		// 파일명 넣기
		downloadPath.append(inputArr[1]);
		
		return downloadPath.toString();
	}
	public void showPicture(String[] inputArr, String osName, StringBuffer downloadPath) throws IOException, InterruptedException {
		String fileName = inputArr[1];
		if(fileName.contains("png") || fileName.contains("jpg") || fileName.contains("jpeg")) {
			Process p = null;
			if(osName.contains("mac")){		        
				String[] cmd = {"/bin/sh","-c","open "+downloadPath.toString()};
	            p = Runtime.getRuntime().exec(cmd);	            
			}else if(osName.contains("window")) {
				p = Runtime.getRuntime().exec("cmd /c " + "mspaint "+downloadPath.toString());
			}
			assert p != null;
			p.waitFor();
            p.destroy();
		}
	}
}
