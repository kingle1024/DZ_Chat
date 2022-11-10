package client.chat;

import static client.Main.*;

import java.io.IOException;

import org.json.JSONObject;

public class MessageProducer implements Runnable {
	private static final MessageQueue messageQueue = MessageQueue.getInstance();
	private static final Monitor monitor = MessageQueue.getMonitor();
	private CommandParser commandParser;

	public MessageProducer(CommandParser commandParser) {
		this.commandParser = commandParser;
	}

	@Override
	public void run() {
		while (getScanner().hasNext()) {
			try {
				String msg = getScanner().nextLine();
				JSONObject msgJson = commandParser.createJSONObject(msg);
				messageQueue.add(msgJson);
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
