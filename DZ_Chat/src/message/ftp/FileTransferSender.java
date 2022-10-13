package message.ftp;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

// 보내는 곳 (Client)
public class FileTransferSender {
	public static int DEFAULT_BUFFER_SIZE = 10000;

	public static void main(String[] args) {
		String serverIP = "localhost";
		int port = 50001;
		String FileName = "./test.txt";

		// 파일 존재 여부 확인 
		File file = new File(FileName);
		if (!file.exists()) {
			System.out.println("File not Exist");
			System.out.println(0);
		}

		long fileSize = file.length();
		long totalReadBytes = 0;
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE]; // 4K가 적절하지 않나?
		int readBytes;

		try {
			// 서버 연결
			Socket socket = new Socket(serverIP, port);
			if (!socket.isConnected()) {
				System.out.println("Socket Connect Error.");
				System.exit(0);
			}
			System.out.println("Server Connected");
			BufferedWriter bufferedWriter = 
					new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bufferedWriter.write("#fileSend fileName.txt");
			bufferedWriter.newLine();
			bufferedWriter.flush();
			
			System.out.println("메시지 전송 완료");
			
			//파일 보내는 부분 
			FileInputStream fis = new FileInputStream(file);
			OutputStream os = socket.getOutputStream();
			while ((readBytes = fis.read(buffer)) > 0) {
				os.write(buffer, 0, readBytes); // 실질적으로 보내는 부분				
				totalReadBytes += readBytes;
				System.out.println("In progress: " + totalReadBytes + "/" + fileSize + " Byte(s) ("
						+ (totalReadBytes * 100 / fileSize) + " %)");				
			}
			os.flush();

			System.out.println("File transfer completed.");
			fis.close();
			os.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Err1");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Err2");
		}
	}
}
