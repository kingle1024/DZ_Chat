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
	

	public static void main(String[] args) throws Exception, SecurityException, Exception {
		String serverIP = "localhost";
		int port = 50001;

		try {
			// 서버 연결
			Socket socket = new Socket(serverIP, port);
			connectErrorCheck(socket);	
			System.out.print("채팅 입력 (Ex #fileSend fileName.txt) : ");			
			FtpService ftp = new FtpService();
			
//			messageSend(socket);
//			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			#fileSend fileName.txt
			String input = br.readLine();
//			Message chatMessage = new ChatMessage(null, null, input);			
//			chatMessage.send(socket.getOutputStream());
			messageSend(input, socket);
			
//			Message chatMessage = new ChatMessage(null, null, input);			
//			chatMessage.send(socket.getOutputStream());			
											
			//파일 보내는 부분 		
			if(input.startsWith("#fileSend")){
				ftp.sendFile(input, socket);	
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
			FtpService ftp = new FtpService();
			String[] inputArr = input.split(" ");
			
			String osName = System.getProperty("os.name").toLowerCase();				
			String userName = System.getProperty("user.name");
			StringBuffer downloadPath = new StringBuffer();
						
			downloadPath = new StringBuffer(
							ftp.getDownloadPath(
									osName, 
									downloadPath, 
									userName, 
									inputArr));
			
			ftp.saveTargetFile(inputArr[1], downloadPath.toString());			
			ftp.showPicture(inputArr, osName, downloadPath);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void connectErrorCheck(Socket socket) {
		if (!socket.isConnected()) {
			System.out.println("Socket Connect Error.");
			System.exit(0);
		}
		System.out.println("Server Connected");
	}
	
	public static void messageSend(String input, Socket socket) throws IOException {
		BufferedWriter bufferedWriter = 
				new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//		bufferedWriter.write("#fileSend fileName.txt");
		bufferedWriter.write(input);
		bufferedWriter.newLine();
		bufferedWriter.flush();
		System.out.println("메시지 전송 완료");
	}		
}
