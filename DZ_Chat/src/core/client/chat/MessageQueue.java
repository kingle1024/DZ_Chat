package core.client.chat;

import java.util.concurrent.ConcurrentLinkedQueue;

import message.chat.Message;

public class MessageQueue {
	private static final Monitor monitor = new Monitor("open");
	private final static MessageQueue messageQueue = new MessageQueue();
	private final static ConcurrentLinkedQueue<Message> que = new ConcurrentLinkedQueue<>();
	
	private MessageQueue() { }
	
	public static MessageQueue getInstance() {
		return messageQueue;
	}
	
	public void add(Message message) {
		synchronized (monitor) {
			que.add(message);
			monitor.notifyAll();
		}
	}
	
	public Message poll() throws InterruptedException {
		synchronized (monitor) {
			if (que.isEmpty()) monitor.wait();
			Message message = que.poll();
			monitor.notifyAll();
			return message;
		}
	}
	
	public boolean isEmpty() {
		return que.isEmpty();
	}
	
	public static Monitor getMonitor() {
		return monitor;
	}
}
