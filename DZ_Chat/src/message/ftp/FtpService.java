package message.ftp;

import property.Property;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FtpService {
	private final int DEFAULT_BUFFER_SIZE = Integer.parseInt(Property.client().get("DEFAULT_BUFFER_SIZE"));
	public static boolean fileValid(String filePath) {
		File file = new File(filePath);
		
		if (!file.exists()) {
			System.out.println("보낼 파일이 존재하지 않습니다 > "+file.getAbsolutePath());
			return false;
		}
		
		return true;
	}
	public void saveSocketFile(Socket socket, String saveFilePath) throws IOException {
		// Socket으로 데이터가 오는 경우
		saveFile(socket.getInputStream(), saveFilePath);
	}
	public void saveTargetFile(String targetFilePath, String saveFilePath) throws IOException {
		// 파일 경로로 데이터 위치를 알려주는 경우
		saveFile(new FileInputStream(targetFilePath), saveFilePath);
	}
	public void saveFile(InputStream is, String saveFilePath) {
		try {
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int readBytes;
			FileOutputStream fos = new FileOutputStream(saveFilePath); // 저장할 파일 위치
	
			while ((readBytes = is.read(buffer)) != -1) { // InputStream에서 내용을 읽어서 FileOutputStream으로 저장
				fos.write(buffer, 0, readBytes);
			}

			is.close();
			fos.close();
		} catch (IOException e) {
			System.out.println("saveFile IOException > "+e);
			throw new RuntimeException(e);
		}
		System.out.println("FtpService > saveFile 끝 ");
	}
	public String dir(String roomName) {
		File file = new File(Property.server().get("DOWNLOAD_PATH")+roomName+"");
		StringBuilder sb = new StringBuilder();
		if(!file.exists()) {
			sb.append("폴더에 파일이 존재하지 않습니다. ( ")
					.append(file.getAbsolutePath()).append(")");
			return sb.toString();
		}
		File[] contents = file.listFiles();
		sb.append("<파일 목록> ")
				.append(roomName)
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
	public String getOsPath(String osName, String userName){
		StringBuilder osPath = new StringBuilder();
		if(osName.contains("window")){
			osPath
					.append("C:\\Users\\")
					.append(userName);
		}else if(osName.contains("mac")) {
			osPath
					.append("/Users/")
					.append(userName);
		}
		return osPath.toString();
	}
	public String getDownloadPath(String osName, StringBuffer downloadPath, String userName, String[] inputArr) {
		String osPath = getOsPath(osName, userName);
		downloadPath.append(osPath);
		if(osName.contains("window")){
			downloadPath.append("\\Downloads\\");
		}else if(osName.contains("mac")){
			downloadPath.append("/Downloads/");
		}
		String fileName = inputArr[1];
		downloadPath.append(fileName);
		
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
	public boolean saveFileToServer(Socket socket, String filename) {
		// 서버에 파일 전송
		try {
			System.out.println("FtpServer > saveFile > 내가 받은 파일 경로 :"+filename);
			FtpService ftp = new FtpService();
			ftp.saveSocketFile(socket, filename);
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("파일 전송에 실패하였습니다.");
			return false;
		}

		return true;
	}
	public String clientMessageReceive(Socket socket) throws IOException {
		BufferedReader bufferedReader =
				new BufferedReader(
						new InputStreamReader(
								new ObjectInputStream(socket.getInputStream())
						)
				);
		String clientMessage = bufferedReader.readLine();
		System.out.println("클라이언트가 보내온 내용 :"+clientMessage);

		return clientMessage;
	}
	public void fileSend(String clientMessage, Socket socket) {
		String[] message = clientMessage.split(" ");
		System.out.println("FtpService > fileSend > message[1] > "+message[1]);
		File file = new File(message[1]);
		String fileName = file.getName();
		System.out.println("FtpService > fileSend > message[2] > "+message[2]);
		String roomName = message[2];
		FileCommon fileCommon = new FileCommon();
		String saveFilePath = fileCommon.fileNameBalance("resources/"+roomName+"/", fileName);
		FtpService ftpService = new FtpService();
		if(ftpService.saveFileToServer(socket, saveFilePath)){
			System.out.println("FtpServer > fileSend : 파일 전송 성공");
		}else{
			System.out.println("파일 전송 실패 ");
		}
	}
}
