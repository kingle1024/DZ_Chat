package core.client.chat;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import core.client.ObjectStreamClient;
import core.mapper.ServiceResolver;
import member.Member;
import message.MessageFactory;
import message.chat.*;

public class ChatClient extends ObjectStreamClient {
	private Member me;
	private String chatRoomName;
	private boolean sendExit = false;
	private ThreadGroup threadGroup;
	private MessageFactory messageFactory;

	
	private static final MessageQueue messageQueue = MessageQueue.getInstance();
	private static final Monitor monitor = messageQueue.getMonitor();
	private boolean isMessageConsumerStarted = false;
	
	public ChatClient(String chatRoomName, Member me) {
		this.chatRoomName = chatRoomName;
		this.me = me;
		this.messageFactory = new MessageFactory(me, chatRoomName, threadGroup);
	}


	public void run() {
		System.out.println("채팅 시작");
		Scanner scanner = new Scanner(System.in);
		
		MessageListener messageListener = new MessageListener(is);
		MessageProducer messageProducer = new MessageProducer(messageFactory);
		MessageConsumer messageConsumer = new MessageConsumer(os);
		
		
		Thread listenerThread = new Thread(messageListener);
		Thread producerThread = new Thread(messageProducer);
		Thread consumerThread = new Thread(messageConsumer);
		
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
				synchronized (monitor) {
					System.out.println("ChatClient monitor");
					monitor.setStatus("open");
					monitor.notify();
					while (true) {
						if (monitor.equalsStatus("open")) {
							monitor.wait();
						} else if (monitor.equalsStatus("end")) {
							System.out.println("End monitor");
							sendExit = true;
							monitor.notifyAll();
							throw new IOException();
						} else {
							break;
						}
					}
				}
			} catch (IOException e) {
				if (sendExit) {
					System.out.println("채팅 종료");
					producerThread.interrupt();
					consumerThread.interrupt();
					listenerThread.interrupt();
					return;
				} else {
					try {
						Thread.sleep(1000);
						System.out.println("서버 재접속 시도");
					} catch (InterruptedException e1) {
					}
				}
			} catch (InterruptedException e) {
			}
		}
	}

	public boolean getSendExit() {
		return sendExit;
	}
}