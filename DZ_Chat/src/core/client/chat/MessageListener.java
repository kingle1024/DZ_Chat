package core.client.chat;

import java.io.IOException;
import java.io.ObjectInputStream;

public class MessageListener implements Runnable {
	private static final Monitor monitor = MessageQueue.getMonitor();
	private ObjectInputStream is;

	public MessageListener(ObjectInputStream is) {
		this.is = is;
		System.out.println("MessageListener 생성 완료");
	}

	public void setIs(ObjectInputStream is) {
		this.is = is;
	}
	
	@Override
	public void run() {
		while (true) {
			if (Thread.currentThread().isInterrupted())
				return;
			try {
				String message = (String) is.readObject();
				System.out.println(message);
			} catch (ClassNotFoundException | IOException e) {
				try {
					synchronized (monitor) {
						if (monitor.equalsStatus("end")) return;
						System.out.println("서버와 연결이 끊겼습니다.");
						monitor.setStatus("close");
						monitor.notifyAll();
					}
					Thread.sleep(500);
				} catch (InterruptedException e1) {
				}
			}
		}
	}
}
