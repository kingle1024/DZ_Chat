package core.server;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import log.LogConsumer;
import message.ftp.FtpServer;

public class Watch {
	public static void main(String[] args) throws UnknownHostException {
		Server server = new MainServer(50_001);
		LogConsumer logConsumer = new LogConsumer();
		Thread logConsumerThread = new Thread(logConsumer);
		while (true) {
			try {
				server.start();
				logConsumerThread.start();
				FtpServer.startServer();
				
				WatchService watcher = FileSystems.getDefault().newWatchService();
				Path dir = FileSystems.getDefault().getPath("./bin");
				WatchKey key = dir.register(watcher, ENTRY_MODIFY);
				
				while (true) {
					key = watcher.take();
					for (WatchEvent<?> event : key.pollEvents()) {
						if (event.kind() == ENTRY_MODIFY) {
							FtpServer.stopServer();
							logConsumer.consumeAllLog();
							server.stop();
						}
					}
				}	
			} catch (InterruptedException e) {
				
			} catch (IOException e) {
				
			} catch (Exception e) {
				
			}
		}
		
	}
}
