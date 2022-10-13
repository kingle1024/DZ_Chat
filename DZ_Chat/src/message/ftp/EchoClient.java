package message.ftp;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class EchoClient {	
	
	public static void main(String[] args) {
		try {
			// Socket 생성과 동시에 localhost의 50001 포트로 연결 요청;
//			Socket socket = new Socket("192.168.30.107", 50001);
//			Socket socket = new Socket("192.168.30.51", 50001);
			Socket socket = new Socket("localhost", 50001);
			System.out.println("[클라이언트] 연결 성공");
			
			String fileName = "/Users/ejy1024/test.txt";
			File file = new File(fileName);
			
			DataOutputStream dataOutput = null;
			dataOutput = new DataOutputStream(socket.getOutputStream()); // output 스크림 생성
			dataOutput.writeInt((int) file.length()); // 수신측에 전송파일 사이즈 전달
			dataOutput.flush();			
			
			System.out.println("[클라이언트] 데이터 보냄: "+(int) file.length());
			DataInputStream dataInput = null;
			BufferedOutputStream bufferedOutput = null; // output 속도 향상을 위한 BufferedOutputStream
			
			int totalSize = (int) file.length();
			byte[] bytes = new byte[104857600]; // 100MB 저장할 바이트 배열
			dataInput = new DataInputStream(new FileInputStream(fileName));
			System.out.println("dataInput:"+dataInput);
			bufferedOutput = new BufferedOutputStream(dataOutput);
			int i = 0; // buf 배열 인덱스용 변수

			// 전송받은 파일 사이즈가 100MB 보다 크다면 100MB 단위로 배열에 저장 후 소켓 버퍼에 write 하고
			// 소켓 버퍼에 write한 100MB만큼을 파일 사이즈에서 제외하는 while문!!!
			while (totalSize > 104857600) {
				while (i < 104857600) {
					bytes[i] = (byte) dataInput.read();
					i++;
				} // while(i < 104857600)문
				totalSize -= 104857600; // 파일사이즈 - 100MB
				i = 0; // 배열 인덱스 초기화
				bufferedOutput.write(bytes); // 소켓 버퍼에 write
				System.out.println("by:"+bytes);
			} // while(totalSize > 104857600)문

			// 100MB보다 같거나 작은 남은 사이즈 혹은 원래의 사이즈가 100MB 보다 작을 시 if문 내용이 실행 되어
			// 소켓 버퍼에 write 함
			if (totalSize <= 104857600) {
				i = 0; // 배열 인덱스 초기화
				bytes = new byte[totalSize];
				while (i < totalSize) {
					bytes[i] = (byte) dataInput.read();
					i++; // 배열인덱스 이동
				} // while문
				bufferedOutput.write(bytes); // 소켓 버퍼에 write				
				System.out.println("[클라이언트] 파일을 보냄: " + bytes);
			} // if문

			bufferedOutput.flush();
//			String receiveMess
		}catch(Exception e) {
			
		}
	}
}
