package core.client.chat;

import java.io.IOException;
import java.util.Scanner;

import message.MessageFactory;
import static core.client.Main.*;

public class MessageProducer implements Runnable {
	private static final MessageQueue messageQueue = MessageQueue.getInstance();
	private static final Monitor monitor = MessageQueue.getMonitor();
	private MessageFactory messageFactory;
	
	public MessageProducer(MessageFactory messageFactory) {
		this.messageFactory = messageFactory;
	}
	
	@Override
	public void run() {
		while (getScanner().hasNext()) {
			String chat = getScanner().nextLine();
			try {
				// TODO
				messageFactory.createMessage(chat);
				messageQueue.add("");
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
