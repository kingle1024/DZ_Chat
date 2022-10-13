package message.ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

// 받는곳(Server)
public class FileTransferReceiver {
	private static ServerSocket serverSocket = null;
	public static final int DEFAULT_BUFFER_SIZE = 10000;
	
	public static void main(String[] args) {
		printInfo();
		
		startServer();
		
		exitLoop();
		
		stopServer();
	}	
	public static void printInfo() {
		System.out.println("----------------------------");
		System.out.println("서버를 종료하려면 q를 입력하고 Enter 키를 입력하세요.");
		System.out.println("----------------------------");
	}
	public static void exitLoop() {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			String key = scanner.nextLine();
			if(key.toLowerCase().equals("q")){
				break;
			}
		}
		scanner.close();
	}
	public static void startServer() {
		int port = 50001; // int port =  9999;
		
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					ServerSocket server = new ServerSocket(port);
					System.out.println("This server is listening... (Port: " + port + ")");
					Socket socket = server.accept(); // 새로운 연결 소켓 생성 및 accept대기
										
					// 전달한 사용자 정보 표시
					showClientInfo(socket);
				
					// Client 메시지 확인
					String clientMessage = clientMessageReceive(socket);
					
					System.out.println("Message:"+clientMessage);
					// 파일 전송 
					if(clientMessage.startsWith("#fileSend")) {
						fileSend(clientMessage, socket);
					}
					
//					socket.close();
//					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		};	
		
		thread.start();
	}
	public static void showClientInfo(Socket socket) {
		InetSocketAddress isaClient = 
				(InetSocketAddress) socket.getRemoteSocketAddress();
		System.out.println(
				"A client(" + isaClient.getAddress().getHostAddress() + 
				" is connected. (Port: " + isaClient.getPort() + ")");
	}
	public static String clientMessageReceive(Socket socket) throws IOException {
		BufferedReader bufferedReader = 
				new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String clientMessage = bufferedReader.readLine();
		System.out.println("클라이언트가 보내온 내용 :"+clientMessage);
		
		return clientMessage;
	}
	public static void fileSend(String clientMessage, Socket socket) throws IOException {
		String[] message = clientMessage.split(" ");
		message[1] = fileNameBalance(message[1]);						
		saveFile(socket, message[1]);
	}
	public static void stopServer() {
		try {
			// ServerSocket을 닫고 Port 언바인딩
			serverSocket.close();
			System.out.println("[서버] 종료");
		}catch(IOException e1) {}
	}
	public static String saveFile(Socket socket, String filename) {		
		// 서버에 파일 전송	
		try {						
			System.out.println("내가 받은 :"+filename);
			FileOutputStream fos = new FileOutputStream(filename);		
			InputStream is = socket.getInputStream();

			byte[] buffer = new byte[4096];
			int readBytes;
			
			while ((readBytes = is.read(buffer)) != -1) {
				fos.write(buffer, 0, readBytes);
			}
			
//			fos.flush();			
						
			is.close();
			fos.close();
		}catch(IOException e) {
			e.printStackTrace();			
			return "파일 전송에 실패하였습니다.";
		}
		
		return "파일이 전송되었습니다.";
	}
	public static String fileNameBalance(String fileName) throws IOException {		
		String[] files = null;
		try {
			files = fileName.split("\\.");
		}catch(Exception e) {
			System.out.println(fileName + " : 파일명이 올바르지 않습니다. (Ex test.txt) ");
			return null;
		}
		
		File file = null;
		
		int idx = 1;
		String path = "resources/abc/";
		StringBuffer sbFileName = new StringBuffer(path + fileName);
		
		file = new File(path);
		if(!new File(path).exists()) {
			file.mkdirs();
		}
		
		while(true) {
			file = new File(sbFileName.toString());
			System.out.println("경로:"+file.getAbsolutePath());
			if (file.exists()) {
				sbFileName = new StringBuffer(
							path + files[0] + " ("+idx+")." + files[1]);				
				idx++;
			}else {			
				break;
			}
		}
		
		System.out.println("사용할 파일명 : "+sbFileName);
		return sbFileName.toString();
	}
}
