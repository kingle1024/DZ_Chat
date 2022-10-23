package log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import property.Property;


public class LogConsumer implements Runnable {
	private static final LogQueue logQueue = LogQueue.getInstance();
	private static final Object monitor = logQueue.getMonitor();
	private static final String filePath = "./"+ Property.server().get("CHAT_LOG_FILE");
	BufferedOutputStream out = null;
	
	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					appendInfo(logQueue.poll());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
			
		} 
	}
	
	public void appendInfo(Log log) throws IOException {
		System.out.println("Log > appendInfo");
		try {
//			String filePath = log.getPath();
			File file = new File(filePath);
			
			if(!file.exists()) {
				file.createNewFile();
//				file.mkdirs();
			}
			
			FileOutputStream f = new FileOutputStream(file, true);
			String lineToAppend = log.getTimeLog() + "\n";
			out =  new BufferedOutputStream(f);
			byte[] byteArr = lineToAppend.getBytes();
			out.write(byteArr);
//			f.write(byteArr);
//			f.close();
		}  finally {
            if (out != null) {
                out.close();
            }
        }
//		catch (Exception e) {}
		
	} 
	
	public void consumeAllLog() {
		synchronized (monitor) {
			monitor.notify();
		}
	}
}
