package core.server;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

import log.LogConsumer;

public class Watch {
	private static final String HOST = "localhost";
	private static final int PORT_NUMBER = 50_001;
	public static ExecutorService threadPool;
	public static void main(String[] args) {
		try {
			System.out.println("CHANGE");
			ProcessBuilder pb = new ProcessBuilder("java", "-cp", "./bin", "core.server.Main");
			pb.inheritIO();
			WatchService watcher = FileSystems.getDefault().newWatchService();
			Path dir = FileSystems.getDefault().getPath("./bin");
			registerRecursive(dir, watcher);
			WatchKey key = dir.register(watcher, ENTRY_MODIFY);

			while (true) {
				Process process = pb.start();
				InputStream is = process.getInputStream();
				OutputStream os = process.getOutputStream();
				try {
					key = watcher.take();
					System.out.println("Process RUN");
					for (WatchEvent<?> event : key.pollEvents()) {
						if (event.kind() == ENTRY_MODIFY) {
							key.reset();
							os.write("save".getBytes());
							os.write("q".getBytes());
							os.flush();
//							process.destroy();
						}
					}
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
