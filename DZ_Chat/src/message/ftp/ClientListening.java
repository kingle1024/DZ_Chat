package message.ftp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class ClientListening {

	public ClientListening(int portNumber, String fileName) {
		ServerSocket clientSocket = null; // 리스닝포트와 바인드될 ServerSocket 객체
		FileOutputStream fileOutput = null;
		DataInputStream dataInput = null;
		byte[] buf = null;
		BufferedInputStream bufferdInput = null; // input 속도 향상을 위한 BufferedInputStream

		try {
			clientSocket = new ServerSocket(portNumber);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			dataInput = new DataInputStream(clientSocket.accept().getInputStream()); // 송신측에서 연결요청시 accept

			// 메소드에서 송신측과

			// 연결을 위한 연결소켓생성
			int totalSize = dataInput.readInt(); // 전송받을 파일 사이즈 수신및 변수에 저장
			System.out.println(totalSize); // 수신 파일 사이즈 콘솔출력
			buf = new byte[104857600]; // 100MB 단위로 파일을 쓰기 위한 byte타입 배열

			fileOutput = new FileOutputStream(fileName, false);
			bufferdInput = new BufferedInputStream(dataInput);

			int i = 0; // buf 배열 인덱스용 변수

			// 전송받은 파일 사이즈가 100MB 보다 크다면 100MB 단위로 배열에 저장 후 파일에 write 하고
			// 파일에 write한 100MB만큼을 파일 사이즈에서 제외하는 while문!!!
			while (totalSize > 104857600) {
				while (i < 104857600) {
					buf[i] = (byte) bufferdInput.read();
					i++; // 배열인덱스 이동
				} // while(i < 104857600)문
				totalSize -= 104857600; // 파일사이즈 - 100MB
				i = 0; // 배열 인덱스 초기화
				fileOutput.write(buf); // 파일에 write
			} // while(totalSize > 104857600)문

			// 100MB보다 같거나 작은 남은 사이즈 혹은 원래의 사이즈가 100MB 보다 작을 시 if문 내용이 실행 되어

			// 파일을 write 함
			if (totalSize <= 104857600) {
				i = 0; // 배열 인덱스 초기화
				buf = new byte[totalSize]; // 100MB보다 같거나 작으므로 totalSize로 배열크기 다시 생성
				while (i < totalSize) {
					buf[i] = (byte) bufferdInput.read();
					i++; // 배열인덱스 이동
				} // while문
				fileOutput.write(buf); // 파일에 write
			} // if문

			fileOutput.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferdInput != null)
					bufferdInput.close();
				if (dataInput != null)
					dataInput.close();
				if (fileOutput != null)
					fileOutput.close();
				if (clientSocket != null)
					clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} // finally

	}// main

}// ClientListening 클래스
