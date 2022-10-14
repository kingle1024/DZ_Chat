package message.ftp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import message.chat.ChatMessage;
import message.chat.Message;

// 보내는 곳 (Client)
public class FileTransferSender {
	public static int DEFAULT_BUFFER_SIZE = 10000;

	public static void main(String[] args) throws Exception, SecurityException, Exception {
		String serverIP = "localhost";
		int port = 50001;

		try {
			// 서버 연결
			Socket socket = new Socket(serverIP, port);
			connectErrorCheck(socket);	
			System.out.print("채팅 입력 (Ex #fileSend fileName.txt) : ");			
			
//			messageSend(socket);
//			dir("");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			#fileSend fileName.txt
			String input = br.readLine();
			Message chatMessage = new ChatMessage(null, null, input);			
//			chatMessage.send(socket.getOutputStream());			
											
			//파일 보내는 부분 		
			if(input.startsWith("#fileSend")){
				fileSend(input, socket);			
			}else if(input.startsWith("#fileSave")) {
				saveFile(input, socket);
			}
			
//			os.flush();			
			
//			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Err1");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Err2");
		}
	}
	public static void saveFile(String input, Socket socket) throws ClassNotFoundException, Exception, SecurityException {
		try {
			byte[] buffer = new byte[4096];
			int readBytes;
			
			String osName = System.getProperty("os.name").toLowerCase();				
			String userName = System.getProperty("user.name");
			StringBuffer sb = new StringBuffer();
			if(osName.contains("window")){
				sb.append("C:\\Users\\"+userName+"\\Downloads\\");
			}else if(osName.contains("mac")) {
				sb.append("/Users/"+userName+"/Downloads/");
			}
			
			System.out.println(sb);
			
			FileOutputStream fos = new FileOutputStream(sb.toString()+"test.txt");
			InputStream is = new FileInputStream("fileName.txt");
			
			while ((readBytes = is.read(buffer)) != -1) {
				fos.write(buffer, 0, readBytes);
			}
			
			fos.close();
		}catch(IOException e) {
			
		}
	}
	public static String getOs() {
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
	public static void connectErrorCheck(Socket socket) {
		if (!socket.isConnected()) {
			System.out.println("Socket Connect Error.");
			System.exit(0);
		}
		System.out.println("Server Connected");
	}
	public static void dir(String path) {
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
	public static void messageSend(Socket socket) throws IOException {
		BufferedWriter bufferedWriter = 
				new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bufferedWriter.write("#fileSend fileName.txt");
		bufferedWriter.newLine();
		bufferedWriter.flush();
		System.out.println("메시지 전송 완료");
	}	
	public static void fileSend(String fileName, Socket socket) throws IOException {
		// 파일 존재 여부 확인 
//		String FileName = "fileName.txt";		
		String[] input = fileName.split(" ");
		File file = new File(input[1]);
		
		if (!file.exists()) {
			System.out.println("File not Exist");
			return;
		}
						
		int readBytes;
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE]; // 4K가 적절하지 않나?
		long totalReadBytes = 0;
		long fileSize = file.length();
		
		FileInputStream fis = new FileInputStream(file);
		OutputStream os = socket.getOutputStream();
		while ((readBytes = fis.read(buffer)) > 0) {
			os.write(buffer, 0, readBytes); // 실질적으로 보내는 부분				
			totalReadBytes += readBytes;
			System.out.println("In progress: " + totalReadBytes + "/" + fileSize + " Byte(s) ("
					+ (totalReadBytes * 100 / fileSize) + " %)");				
		}
		
		System.out.println("File transfer completed.");
		fis.close();
		os.close();
	}
}
