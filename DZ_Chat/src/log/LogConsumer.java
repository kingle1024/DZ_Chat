package log;

public class LogConsumer implements Runnable {

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				LogManager.logSave(LogQueue.poll());
			}
		} catch (InterruptedException e) {
		}
	}
}