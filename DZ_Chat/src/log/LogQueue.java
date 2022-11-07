package log;

import java.util.concurrent.LinkedBlockingQueue;

public class LogQueue {
	private static LinkedBlockingQueue<Log> que = new LinkedBlockingQueue<>();

	private LogQueue() {
	}

	public static void add(Log log) {
		que.offer(log);
	}

	public static Log poll() throws InterruptedException {
		return que.take();
	}

}