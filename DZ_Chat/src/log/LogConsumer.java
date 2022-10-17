package log;

import java.io.FileOutputStream;

public class LogConsumer implements Runnable {
	private static final LogQueue logQueue = LogQueue.getInstance();
	private static final Object monitor = logQueue.getMonitor();
	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				appendInfo(logQueue.poll());
			}
		} catch (Exception e) {
			
		}
	}
	
	//추후 interface로 뺄 수 있음??(appendinfo interface)
	public void appendInfo(Log log) {
		// 파일을 열고
		// 파일에 내용 추가
		// 파일 저장
		System.out.println("Log > appendInfo");
		try {
			String filePath = log.getPath();
			FileOutputStream f = new FileOutputStream(filePath, true);
//            String lineToAppend = "\r\nThe quick brown fox jumps over the lazy dog";  
			String lineToAppend = log.getLog();
			byte[] byteArr = lineToAppend.getBytes(); // converting string into byte array
			f.write(byteArr);
			f.close();

			// 채팅시간, 유저아디@ip, 채팅내용
			//
		} catch (Exception e) {
			System.out.println(e);
		}
		// 회원정보, 채팅로그,

	}
	
	public void consumeAllLog() {
		synchronized (monitor) {
			monitor.notify();
		}
	}
}
