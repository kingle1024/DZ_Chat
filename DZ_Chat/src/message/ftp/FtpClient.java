package message.ftp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import message.chat.ChatMessage;
import message.chat.Message;

// 보내는 곳 (Client)
public class FtpClient {
	
	public void run(String chat, String chatRoomName) {
		String serverIP = "localhost";
		int port = 55551;

		try {
			// 서버 연결
			System.out.println("FileTransferSender");
			Socket socket = new Socket(serverIP, port);
			connectErrorCheck(socket);
			FtpService ftp = new FtpService();

//			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			#fileSend fileName.txt
			String input = chat;
//			Message chatMessage = new ChatMessage(null, null, input);
//			chatMessage.send(new ObjectOutputStream(socket.getOutputStream()));
			input = input+" "+chatRoomName;
			messageSend(input, socket);

//			Message chatMessage = new ChatMessage(null, null, input);
//			chatMessage.send(socket.getOutputStream());

			//파일 보내는 부분
			if(input.startsWith("#fileSend")){
				ftp.sendFile(input, chatRoomName, socket);
			}else if(input.startsWith("#fileSave")) {
				saveFile(input, chatRoomName);
			}

//			os.flush();

//			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Err1");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Err2");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.out.println("FileTransferSender > run() 끝");
	}
	public static void main(String[] args) throws Exception, SecurityException, Exception {
//		FileTransferSender fileTransferSender = new FileTransferSender();
//		fileTransferSender.run("#fileSend fileName.txt", "qq");
		System.out.println("FileTransferSender main() 끝");
	}
	public static void saveFile(String input, String chatRoomName) throws ClassNotFoundException, Exception, SecurityException {
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
			String filePath = "resources/room/"+chatRoomName+""+inputArr[1];
			ftp.saveTargetFile(filePath, downloadPath.toString());
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
				new BufferedWriter(
						new OutputStreamWriter(
								new ObjectOutputStream(socket.getOutputStream())
						)
				);
//		bufferedWriter.write("#fileSend fileName.txt");
		bufferedWriter.write(input);
		bufferedWriter.newLine();
		bufferedWriter.flush();
//		System.out.println("메시지 전송 완료");
	}		
}
