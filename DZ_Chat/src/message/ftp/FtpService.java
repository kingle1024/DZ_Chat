package message.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FtpService {
	File file;
	private int DEFAULT_BUFFER_SIZE = 10000;
	public static boolean fileValid(String filePath) {
		File file = new File(filePath);
		
		if (!file.exists()) {
			System.out.println("File not Exist");
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
	public void dir(String path) {
		File file = new File("resources"+path+"/abc");
		if(!file.exists()) {
			System.out.println("폴더가 존재하지 않습니다.");
			System.out.println("path:"+file.getAbsolutePath());
			return;
		}
		File[] contents = file.listFiles();			
		System.out.println("<전송된 파일 목록>");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a HH:mm");
		for(File f : contents) {
			System.out.printf("%-25s", sdf.format(new Date(f.lastModified())));
			if(f.isDirectory()) {
				System.out.printf("%-10s%-20s", "<DIR>", f.getName());				
			}else {
				System.out.printf("%-20s", f.getName());
			}
			System.out.println();
		}
	}
	public void sendFile(String fileName, Socket socket) throws IOException {
		// 파일 존재 여부 확인 
//		String FileName = "fileName.txt";		
		String[] input = fileName.split(" ");
		String splitFileName = input[1];
		
		FtpService ftp = new FtpService();
		if(!ftp.fileValid(splitFileName)) return;
		
		File file = new File(splitFileName);
						
		
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE]; // 4K가 적절하지 않나?
		
		long fileSize = file.length();
		
		FileInputStream fis = new FileInputStream(file);
		OutputStream os = socket.getOutputStream();
		
//		static boolean stop = false;
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					
					int readBytes;
					long totalReadBytes = 0;
					while ((readBytes = fis.read(buffer)) > 0 && stop) {
						os.write(buffer, 0, readBytes); // 실질적으로 보내는 부분				
						totalReadBytes += readBytes;
						System.out.println("In progress: " + totalReadBytes + "/" + fileSize + " Byte(s) ("
								+ (totalReadBytes * 100 / fileSize) + " %)");				
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		thread.start();
		
		try {
			Thread.sleep(3000);
		}catch(Exception e){
			
		}
		stop = true;
		
		System.out.println("File transfer completed.");
		
		fis.close();
		os.close();
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
