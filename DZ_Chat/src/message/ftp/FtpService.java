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
	public boolean sendSocketInputStream(Socket socket, String saveFilePath) throws IOException {
		// Socket으로 데이터가 오는 경우
		System.out.println("FtpServer > saveFilePath > "+saveFilePath);
		return saveFile(socket.getInputStream(), saveFilePath);
	}
	public boolean sendTargetFileInputStream(String targetFilePath, String saveFilePath) throws IOException {
		// 파일 경로로 데이터 위치를 알려주는 경우
		System.out.println("FtpService > targetFilePath > "+targetFilePath);
		System.out.println("FtpService > saveFilePath > "+saveFilePath);
		return saveFile(new FileInputStream(targetFilePath), saveFilePath);
	}
	public boolean saveFile(InputStream is, String saveFilePath) {
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
			return false;
		}
		System.out.println("FtpService > saveFile 끝 ");

		return true;
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
	public String getOsDownloadPath(){
		String userName = System.getProperty("user.name");
		String osName = System.getProperty("os.name").toLowerCase();
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
		if(osName.contains("window")){
			osPath.append("\\Downloads\\");
		}else if(osName.contains("mac")){
			osPath.append("/Downloads/");
		}
		return osPath.toString();
	}
	public String getDownloadPath(String fileName) {
		StringBuffer downloadPath = new StringBuffer();
		downloadPath.append(getOsDownloadPath())
			.append(fileName);
		
		return downloadPath.toString();
	}
	public void showPicture(StringBuffer downloadPath) throws IOException, InterruptedException {
		String osName = System.getProperty("os.name").toLowerCase();
		String downloadPathStr = downloadPath.toString();
		if(downloadPathStr.contains("png") ||
				downloadPathStr.contains("jpg") ||
				downloadPathStr.contains("jpeg")) {
			Process p = null;
			if(osName.contains("mac")){
				String[] cmd = {"/bin/sh","-c","open "+downloadPath};
	            p = Runtime.getRuntime().exec(cmd);	            
			}else if(osName.contains("window")) {
				p = Runtime.getRuntime().exec("cmd /c " + "mspaint "+downloadPath);
			}
			assert p != null;
			p.waitFor();
            p.destroy();
		}
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
	public void fileSend(String chat, Socket socket) {
		try {
			String[] message = chat.split(" ");
			File file = new File(message[1]);
			String fileName = file.getName();
			String roomName = message[2];
			FileCommon fileCommon = new FileCommon();

			String saveFilePath = fileCommon.fileNameBalance("resources/" + roomName + "/", fileName);
			if(saveFilePath != null){
				sendSocketInputStream(socket, saveFilePath);
			}
		}catch (IOException e) {
			System.out.println("FtpService > fileSend > "+e);
			throw new RuntimeException(e);
		}
	}

}
