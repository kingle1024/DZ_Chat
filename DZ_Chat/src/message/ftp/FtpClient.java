package message.ftp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

// 보내는 곳 (Client)
public class FtpClient {
	
	public void run(String chat, String chatRoomName) {
		String serverIP = "localhost";
		int port = 55_552;

		try {
			// 서버 연결
			System.out.println("FtpClient > run() > ");
			Socket socket = new Socket(serverIP, port);
			connectErrorCheck(socket);
			FtpService ftp = new FtpService();

			String input = chat;

			input = input+" room/"+chatRoomName;
			messageSend(input, socket);

			//파일 보내는 부분
			if(input.startsWith("#fileSend")){
				ftp.sendFile(input, socket);
				System.out.println("파일 전송이 완료되었습니다.");
			}else if(input.startsWith("#fileSave")) {
				// 파일 받기
				saveFile(input, chatRoomName);
				System.out.println("파일 저장이 완료되었습니다.");
			}

//			os.flush();
//			socket.close();
//			System.out.println("FtpClient > run() > 끝");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Err1");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Err2");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	public static void main(String[] args) throws Exception, SecurityException, Exception {
//		FileTransferSender fileTransferSender = new FileTransferSender();
//		fileTransferSender.run("#fileSend fileName.txt", "qq");
		System.out.println("FileTransferSender main() 끝");
	}
	public static void saveFile(String input, String chatRoomName) throws Exception {
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
			String filePath = "resources/room/"+chatRoomName+"/"+inputArr[1];
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
