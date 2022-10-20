package core.client.chat;

import java.io.IOException;
import java.io.ObjectOutputStream;

import message.chat.Message;

public class MessageConsumer implements Runnable {
	private static final MessageQueue messageQueue = MessageQueue.getInstance();
	private Monitor monitor = messageQueue.getMonitor();
	private ObjectOutputStream os;

	public MessageConsumer(ObjectOutputStream os) {
		this.os = os;
		System.out.println("MessageConsumer  생성 완료");
	}

	public void setOs(ObjectOutputStream os) {
		this.os = os;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				if (!messageQueue.isEmpty() && !monitor.equalsStatus("close")) {
					Message message = messageQueue.poll();
					os.writeObject(message);
					os.flush();
				} else {
					synchronized (monitor) {
						monitor.wait();
					}					
				}
			} catch (InterruptedException e) {
				
			} catch (IOException e) {
				System.out.println("서버와 연결이 끊겼습니다.");
				synchronized (monitor) {
					monitor.setStatus("close");
				}
			}
		}
	}
}
