package core.client.chat;

import java.io.IOException;
import java.io.ObjectInputStream;

public class MessageListener implements Runnable {
	private ObjectInputStream is;

	public MessageListener(ObjectInputStream is) {
		this.is = is;
		System.out.println("MessageListener 생성 완료");
	}

	public void setIs(ObjectInputStream is) {
		this.is = is;
	}
	
	@Override
	public void run() {
		while (true) {
			if (Thread.currentThread().isInterrupted())
				return;
			try {
				String message = (String) is.readObject();
				System.out.println(message);
			} catch (ClassNotFoundException | IOException e) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
