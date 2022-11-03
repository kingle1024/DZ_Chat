package core.client.chat;

import java.io.*;
import java.util.Arrays;

import org.json.JSONObject;

import core.client.ObjectStreamClient;
import core.mapper.ServiceResolver;
import member.Member;
import message.MessageFactory;

public class ChatClient extends ObjectStreamClient {
	private Member me;
	private String chatRoomName;
	private boolean sendExit = false;
	private ThreadGroup threadGroup;
	private MessageFactory messageFactory;

	private static final Monitor monitor = MessageQueue.getMonitor();
	private boolean isMessageConsumerStarted = false;

	public ChatClient(String chatRoomName, Member me) {
		this.chatRoomName = chatRoomName;
		this.me = me;
		this.messageFactory = new MessageFactory(me, chatRoomName, threadGroup);
	}

	public JSONObject run() {
		System.out.println("채팅 시작");
		MessageListener messageListener = new MessageListener(is);
		MessageProducer messageProducer = new MessageProducer(messageFactory);
		MessageConsumer messageConsumer = new MessageConsumer(os);

		Thread listenerThread = new Thread(messageListener);
		Thread producerThread = new Thread(messageProducer);
		Thread consumerThread = new Thread(messageConsumer);
		listenerThread.setDaemon(true);
		producerThread.setDaemon(true);
		consumerThread.setDaemon(true);
		producerThread.start();

		while (true) {
			try {
				connect(new ServiceResolver("chat.ChatService", chatRoomName, me));
				messageListener.setIs(is);
				messageConsumer.setOs(os);
				if (!isMessageConsumerStarted) {
					listenerThread.start();
					consumerThread.start();
					isMessageConsumerStarted = true;
				}

				System.out.println("채팅방 입장");
				monitorControl();
				
			} catch (IOException e) {
				if (sendExit) {
					exit(producerThread, consumerThread, listenerThread);
					return null;
				} else {
					connectTry(1000);
				}
			} catch (InterruptedException e) {
				
			}
		}
	}

	private void monitorControl() throws InterruptedException, IOException {
		synchronized (monitor) {
			monitor.setStatus("open");
			monitor.notify();
			while (true) {
				if (monitor.equalsStatus("open")) {
					monitor.wait();
				} else if (monitor.equalsStatus("end")) {
					sendExit = true;
					monitor.notifyAll();
					throw new IOException();
				} else {
					throw new IOException();
				}
			}
		}
	}
	
	private void exit(Thread...threads) {
		System.out.println("채팅 종료");
		Arrays.asList(threads).forEach(Thread::interrupt);
		try {
			unconnect();
		} catch (IOException e) {

		}
	}
	
	private void connectTry(int time) {
		try {
			Thread.sleep(time);
			System.out.println("서버 재접속 시도");
		} catch (InterruptedException e) {
		}
	}
	
	public boolean getSendExit() {
		return sendExit;
	}
}