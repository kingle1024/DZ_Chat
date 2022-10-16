package log;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LogQueue {
	private static LogQueue logQueue;
	private ConcurrentLinkedQueue<Log> que = new ConcurrentLinkedQueue<>();
	static final Object monitor  = new Object(); 
	private LogQueue() {}

	public static LogQueue getInstance() {
		if (logQueue == null) { 
			synchronized (monitor) {
				return logQueue = new LogQueue();
			}
		}
		return logQueue;
	}
	
	public void add(NeedLog log) {
		synchronized (monitor) {
			que.add(log.toLog());
		}
		if (que.size() > 5) {
			monitor.notify();
		}
	}
	
	public Log poll() throws InterruptedException {
		synchronized (monitor) {
			if (que.isEmpty()) {
				monitor.wait();
			}
			return que.poll();
		}
	}

}
