package client.chat;

import static client.Main.*;

import java.io.*;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import client.Client;
import client.mapper.RequestType;
import message.MessageFactory;

public class ChatClient extends Client {
	private String chatRoomName;
	private boolean sendExit = false;
	private ThreadGroup threadGroup;
	private CommandParser commandParser;
	
	private static final Monitor monitor = MessageQueue.getMonitor();
	private boolean isMessageConsumerStarted = false;

	public ChatClient(String chatRoomName) {
		this.chatRoomName = chatRoomName;
		this.commandParser = new CommandParser(getMe(), chatRoomName, threadGroup);
	}

	public JSONObject run() {
		System.out.println("채팅 시작");
		MessageListener messageListener = new MessageListener(this);
		MessageProducer messageProducer = new MessageProducer(commandParser);
		MessageConsumer messageConsumer = new MessageConsumer(this);

		Thread listenerThread = new Thread(messageListener);
		Thread producerThread = new Thread(messageProducer);
		Thread consumerThread = new Thread(messageConsumer);
		listenerThread.setDaemon(true);
		producerThread.setDaemon(true);
		consumerThread.setDaemon(true);
		producerThread.start();

		while (true) {
			try {
				JSONObject initData = new JSONObject();
				initData.put("chatRoomName", chatRoomName);
				initData.put("me", getMe().getJSON());
			
				connect(new RequestType("chat.ChatService"));
				send(initData);
				
				messageListener.setChatClient(this);
				messageConsumer.setChatClient(this);
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