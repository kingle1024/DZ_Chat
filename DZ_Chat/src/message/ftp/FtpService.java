package message.ftp;
import dto.chat.ChatInfo;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import net.lingala.zip4j.io.ZipOutputStream;
import property.ClientProperties;
import property.ServerProperties;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class FtpService {
	private static final int DEFAULT_BUFFER_SIZE = ClientProperties.getDefaultBufferSize();
	public static boolean fileValid(String filePath) {
		File file = new File(filePath);

		if (!file.exists()) {
			System.out.println("보낼 파일이 존재하지 않습니다 > " + file.getAbsolutePath());
			return false;
		}

		return true;
	}

	public boolean saveFile(InputStream is, String saveFilePath) {
		try {
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int readBytes;
			OutputStream fos = new FileOutputStream(saveFilePath); // 저장할 파일 위치

			while ((readBytes = is.read(buffer)) != -1) { // InputStream에서 내용을 읽어서 FileOutputStream으로 저장
				fos.write(buffer, 0, readBytes);
			}

			is.close();
			fos.close();
		} catch (IOException e) {
			System.out.println("saveFile IOException > " + e);
			return false;
		}
		System.out.println("FtpService > saveFile 끝 ");

		return true;
	}

	public String dir(String roomName) {
		File file = new File(ServerProperties.getDownloadPath() + roomName);
		StringBuilder sb = new StringBuilder();
		
		if (!file.exists()) {
			sb.append("폴더에 파일이 존재하지 않습니다. ( ")
			.append(file.getAbsolutePath())
			.append(")");
			
			return sb.toString();
		}
		
		File[] contents = file.listFiles();
		sb.append("<파일 목록> ")
		.append(roomName)
		.append("\n");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a HH:mm");

		if (contents != null) {
			for (File f : contents) {
				sb.append(String.format("%-25s", sdf.format(new Date(f.lastModified()))));

				if (f.isDirectory()) {
					sb.append(String.format("%-10s%-20s", "<DIR>", f.getName()));
				} else {
					sb.append(String.format("%-20s", f.getName()));
				}
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	
		
	public String getOsDownloadPath() {
		String userName = System.getProperty("user.name");
		String osName = System.getProperty("os.name").toLowerCase();
		StringBuilder osPath = new StringBuilder();
		
		//if 하나로 합침
		if (osName.contains("window")) {
			osPath.append("C:\\Users\\").append(userName);
			osPath.append("\\Downloads\\");
		} else if (osName.contains("mac")) {
			osPath.append("/Users/").append(userName);
			osPath.append("/Downloads/");
		}
		
		return osPath.toString();
	}

	public String getDownloadPath(String fileName) {
		return getOsDownloadPath() + fileName;
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

	public void fileSend(ChatInfo chatInfo, Socket socket) {
		try {
			String fileName = chatInfo.getFilePath();
			String roomName = chatInfo.getRoomName();
			FileCommon fileCommon = new FileCommon();

			String saveFilePath = fileCommon.makeFileName("resources/room/" + roomName + "/", fileName);
			System.out.println("saveFilePath:"+saveFilePath);
			if (saveFilePath != null) {
				saveFile(socket.getInputStream(), saveFilePath);
			}
		} catch (IOException e) {
			System.out.println("FtpService > fileSend > " + e);
			throw new RuntimeException(e);
		}
	}

	public void createZipfile(ChatInfo chatInfo, Socket socket) {
		try{
			String folderPath = ServerProperties.getDownloadPath() + chatInfo.getRoomName();
			File file = new File(folderPath);
			File[] files = file.listFiles();

			ZipParameters zipParameters = new ZipParameters();
			setPassword(chatInfo, zipParameters);
			ZipOutputStream out = new ZipOutputStream(socket.getOutputStream());

			for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
				writeZipToSocket(zipParameters, out, files[i]);
			}
			out.finish();
			System.out.println("압축 파일 생성 성공");
		} catch (IOException | ZipException e) {
			throw new RuntimeException(e);
		}

	}

	private static void writeZipToSocket(ZipParameters parameters, ZipOutputStream out, File files) {
		byte[] buf = new byte[DEFAULT_BUFFER_SIZE];

		try (InputStream in = new FileInputStream(files)) {
			out.putNextEntry(files, parameters);

			int len;
			while((len = in.read(buf)) > 0){
				out.write(buf, 0, len);
			}

			out.closeEntry();
		} catch (IOException | ZipException e) {
			throw new RuntimeException(e);
		}
	}

	private static void setPassword(ChatInfo chatInfo, ZipParameters zipParameters) {
		if(!"temp".equals(chatInfo.getFilePath())) {
			zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			zipParameters.setEncryptFiles(true);
			zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			zipParameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			zipParameters.setPassword(chatInfo.getFilePath());
		}
	}
}
