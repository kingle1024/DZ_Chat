package core.server;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ProcessBuilder.Redirect;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import log.LogConsumer;
import property.Property;

public class Watch {
	private static final String HOST = Property.server().get("IP");
	private static final int PORT_NUMBER = Integer.parseInt(Property.server().get("SERVER_PORT"));
	public static void main(String[] args) {
		try {
			ProcessBuilder pb = new ProcessBuilder("java", "-cp", "./bin", "core.server.Main");
			WatchService watcher = FileSystems.getDefault().newWatchService();
			Path dir = FileSystems.getDefault().getPath("./bin");
			registerRecursive(dir, watcher);
			WatchKey key = dir.register(watcher, ENTRY_MODIFY);

			while (true) {
				Process process = pb.start();
				InputStreamReader is = new InputStreamReader(process.getInputStream(), "EUC-KR");
				OutputStreamWriter os = new OutputStreamWriter(process.getOutputStream(), "EUC-KR");
				Thread thread = new Thread(() ->{
					while (true) {
						if (Thread.currentThread().isInterrupted()) return;
						try {
							char input = (char) is.read();
							if (input != -1) System.out.print(input);
						} catch (IOException e) {
						}
					}
				});
				thread.start();					
				try {
					key = watcher.take();
					System.out.println("Process RUN");
					for (WatchEvent<?> event : key.pollEvents()) {
						if (event.kind() == ENTRY_MODIFY) {
							System.out.println("변경 감지");
							os.write("q\n");
							os.flush();
							os.close();
							thread.interrupt();
							process.waitFor(3000, TimeUnit.MILLISECONDS);
							process.destroy();
						}
					}
					key.reset();
					System.out.println("key.reset()");
				} catch (InterruptedException e) {
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void registerRecursive(final Path root, WatchService watchService) throws IOException {
		Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				dir.register(watchService, ENTRY_MODIFY);
				return FileVisitResult.CONTINUE;
			}
		});
	}
}
