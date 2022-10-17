package message.ftp;

import property.Property;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

// 보내는 곳 (Client)
public class FtpClient extends Thread{
	private volatile HashMap<String, Object> map;
	public FtpClient(ThreadGroup threadGroup, String threadName, HashMap<String, Object> map) {
		super(threadGroup, threadName);
		this.map = map;
	}

	@Override
	public void start() {
		String chatRoomName = (String) map.get("chatRoomName");
		String chat = (String) map.get("chat");
		String serverIP = Property.list().get("IP");
		String fileName = chat.split(" ")[1];

		HashMap<String, Object> map = new HashMap<>();
		map.put("chatRoomAndFileName", chatRoomName+"/"+fileName);
		map.put("chat", chat);

		int port = Integer.parseInt(Property.list().get("FTP_PORT"));
		System.out.println("FtpClient > port > "+port);

		try {
			// 서버 연결
			System.out.println("FtpClient > run() > ");
			Socket socket = new Socket(serverIP, port);
			connectErrorCheck(socket);

			//파일 보내는 부분
			System.out.println("FtpClient > startWith > " + chatRoomName);
			// 파일 받기
			saveFile(map);
			System.out.println("파일 저장이 완료되었습니다.");


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
	public static void saveFile(HashMap<String, Object> map) throws Exception {
		String chatRoomAndFileName = (String) map.get("chatRoomAndFileName");
		String chat = (String) map.get("chat");
		try {
			FtpService ftp = new FtpService();
			String[] inputArr = chat.split(" ");
			
			String osName = System.getProperty("os.name").toLowerCase();				
			String userName = System.getProperty("user.name");
			StringBuffer downloadPath = new StringBuffer();
						
			downloadPath = new StringBuffer(
							ftp.getDownloadPath(
									osName, 
									downloadPath, 
									userName, 
									inputArr));

			String filePath = "resources/room/"+chatRoomAndFileName;

			System.out.println("FtpClient > saveFile > filePath > "+chatRoomAndFileName);
			System.out.println("downloadPath:"+downloadPath.toString());
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
