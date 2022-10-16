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
	File file;
	private int DEFAULT_BUFFER_SIZE = 10000;
	public static boolean fileValid(String filePath) {
		File file = new File(filePath);
		
		if (!file.exists()) {
			System.out.println("File not Exist : "+file.getAbsolutePath());
			return false;
		}
		
		return true;
	}
	public void saveSocketFile(Socket socket, String saveFilePath) throws IOException {
		saveFile(socket.getInputStream(), saveFilePath);
	}
	public void saveTargetFile(String targetFilePath, String saveFilePath) throws IOException {				
		saveFile(new FileInputStream(targetFilePath), saveFilePath);
	}
	public void saveFile(InputStream is, String saveFilePath) throws IOException {
		byte[] buffer = new byte[4096];
		int readBytes;
		FileOutputStream fos = new FileOutputStream(saveFilePath);
		
		while ((readBytes = is.read(buffer)) != -1) {
			fos.write(buffer, 0, readBytes);
		}

		is.close();
		fos.close();
	}
	public String dir(String path) {
		File file = new File("resources/room/"+path+"");
		StringBuffer sb = new StringBuffer();
		if(!file.exists()) {
			sb.append("폴더에 파일이 존재하지 않습니다. ( "+file.getAbsolutePath()+")");
			return sb.toString();
		}
		File[] contents = file.listFiles();
		sb.append("<전송된 파일 목록>");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a HH:mm");
		for(File f : contents) {
//			System.out.printf("%-25s", sdf.format(new Date(f.lastModified())));
			sb.append(String.format("%-25s\n", sdf.format(new Date(f.lastModified()))));
			if(f.isDirectory()) {
//				System.out.printf("%-10s%-20s", "<DIR>", f.getName());
				sb.append(String.format("%-10s%-20s", "<DIR>", f.getName()));
			}else {
//				System.out.printf("%-20s", f.getName());
				sb.append(String.format("%-20s", f.getName()));
			}
//			System.out.println();
			sb.append("\n");
		}
		return sb.toString();
	}
	public void sendFile(String fileName, String chatRoomName, Socket socket) throws IOException{
		String[] input = fileName.split(" ");
		String splitFileName = input[1];
		String path = input[2];
		splitFileName = "DZ_Chat/"+splitFileName;
		
		if(!fileValid(splitFileName)) return;
		System.out.println("sendFile1:"+splitFileName);
		// 파일이 존재하는 경로
		File file = new File(splitFileName);
						
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE]; // 4K가 적절하지 않나?
		long fileSize = file.length();
		
		// 여기에 파일이 있음 
		InputStream fis = new FileInputStream(file);

		// 앞으로 저장할 파일
		OutputStream os = socket.getOutputStream();

		try {
			int readBytes;
			long totalReadBytes = 0;
			while ((readBytes = fis.read(buffer)) > 0
//							&& stop
					) {
				os.write(buffer, 0, readBytes); // 실질적으로 보내는 부분
				totalReadBytes += readBytes;
				System.out.println("In progress: " + totalReadBytes + "/" + fileSize + " Byte(s) ("
						+ (totalReadBytes * 100 / fileSize) + " %)");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


//		stop = true;
		System.out.println("FtpService > sendFile 끝");
		
//		fis.close();
//		os.close();
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
			downloadPath.append("C:\\Users\\"+userName+"\\Downloads\\");
		}else if(osName.contains("mac")) {
			downloadPath.append("/Users/"+userName+"/Downloads/");
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
			p.waitFor();
            p.destroy();
		}
	}
}
