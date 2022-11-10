package client.chat;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.json.JSONObject;

import message.chat.Message;

public class MessageConsumer implements Runnable {
	private static final MessageQueue messageQueue = MessageQueue.getInstance();
	private Monitor monitor = MessageQueue.getMonitor();
	private ChatClient chatClient;

	public MessageConsumer(ChatClient chatClient) {
		this.chatClient = chatClient;
		System.out.println("MessageConsumer  생성 완료");
	}

	public void setChatClient(ChatClient chatClient) {
		this.chatClient = chatClient;
	}
	
	@Override
	public void run() {
		while (true) {
			if (Thread.currentThread().isInterrupted()) {
				return;
			}
			try {
				if (!messageQueue.isEmpty() && !monitor.equalsStatus("close")) {
					JSONObject message = messageQueue.poll();
					chatClient.send(message);
				} else {
					synchronized (monitor) {
						monitor.wait();
					}					
				}
			} catch (InterruptedException e) {
				
			} catch (IOException e) {

			}
		}
	}
}
