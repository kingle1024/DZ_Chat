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
		} catch (InterruptedException e) {
			
		}
	}
	
	public void appendInfo(Log log) {
		System.out.println("Log > appendInfo");
		try {
			String filePath = log.getPath();
			FileOutputStream f = new FileOutputStream(filePath, true);
			String lineToAppend = log.getTimeLog();
			byte[] byteArr = lineToAppend.getBytes();
			f.write(byteArr);
			f.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void consumeAllLog() {
		synchronized (monitor) {
			monitor.notify();
		}
	}
}
