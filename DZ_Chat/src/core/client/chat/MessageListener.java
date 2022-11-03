package core.client.chat;

import java.io.IOException;

import org.json.JSONObject;

public class MessageListener implements Runnable {
	private static final Monitor monitor = MessageQueue.getMonitor();
	private ChatClient chatClient;

	public MessageListener(ChatClient chatClient) {
		this.chatClient = chatClient;
		System.out.println("MessageListener 생성 완료");
	}

	public void setChatClient(ChatClient chatClient) {
		this.chatClient = chatClient;
	}

	@Override
	public void run() {
		while (true) {
			if (Thread.currentThread().isInterrupted())
				return;
			JSONObject json = chatClient.receive();
			System.out.println(json.get("message"));
			try {
				synchronized (monitor) {
					if (monitor.equalsStatus("end"))
						return;
					System.out.println("서버와 연결이 끊겼습니다.");
					monitor.setStatus("close");
					monitor.notifyAll();
				}
				Thread.sleep(500);
			} catch (InterruptedException e) {
				
			}
		}
	}
}
