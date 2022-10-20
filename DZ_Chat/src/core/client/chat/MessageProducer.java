package core.client.chat;

import java.io.IOException;
import java.util.Scanner;

import message.MessageFactory;

public class MessageProducer implements Runnable {
	private static final MessageQueue messageQueue = MessageQueue.getInstance();
	private static final Monitor monitor = messageQueue.getMonitor();
	private MessageFactory messageFactory;
	
	public MessageProducer(MessageFactory messageFactory) {
		this.messageFactory = messageFactory;
	}
	
	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			String chat = scanner.nextLine();
			try {
				messageQueue.add(messageFactory.createMessage(chat));
			} catch (ChatRoomExitException e) {
				System.out.println("MessageProducer ChatRoomExitException");
				synchronized (monitor) {
					monitor.setStatus("end");
					monitor.notifyAll();
				}
				return;
			} catch (IOException e) {
				
			}
		}
	}
	
}
