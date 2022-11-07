package core.client.chat;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.json.JSONObject;

import message.chat.Message;

public class MessageQueue {
	private static final Monitor monitor = new Monitor("open");
	private final static MessageQueue messageQueue = new MessageQueue();
	private final static ConcurrentLinkedQueue<JSONObject> que = new ConcurrentLinkedQueue<>();
	// TODO 클라이언트 메세지 타입 만들기
	// Queue, 생성자 - 소비자 패턴 monitor 사용하지 않고 만들기
	
	private MessageQueue() { }
	
	public static MessageQueue getInstance() {
		return messageQueue;
	}
	
	public void add(JSONObject message) {
		synchronized (monitor) {
			que.add(message);
			monitor.notifyAll();
		}
	}
	
	public JSONObject poll() throws InterruptedException {
		synchronized (monitor) {
			if (que.isEmpty()) monitor.wait();
			JSONObject message = que.poll();
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
