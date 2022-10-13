package message.ftp;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import message.chat.ChatMessage;
import message.chat.Message;

// 보내는 곳 (Client)
public class FileTransferSender {
	public static int DEFAULT_BUFFER_SIZE = 10000;

	public static void main(String[] args) {
		String serverIP = "localhost";
		int port = 50001;

		try {
			// 서버 연결
			Socket socket = new Socket(serverIP, port);
			connectErrorCheck(socket);			
			
//			messageSend(socket);
			dir("");
//			Message chatMessage = new ChatMessage(null, null, "");
//			chatMessage.send(socket.getOutputStream());			
								
			//파일 보내는 부분 			
//			fileSend(socket);
			
//			os.flush();			
			
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Err1");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Err2");
		}
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
	public static void fileSend(Socket socket) throws IOException {
		// 파일 존재 여부 확인 
		String FileName = "fileName.txt";
		File file = new File(FileName);
		if (!file.exists()) {
			System.out.println("File not Exist");
			System.out.println(0);
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
