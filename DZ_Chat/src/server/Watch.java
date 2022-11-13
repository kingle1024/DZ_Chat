package server;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Watch {
	private static class ReadThread implements Runnable {
		private BufferedReader br;

		ReadThread(InputStream is, String charSet) {
			try {
				this.br = new BufferedReader(new InputStreamReader(is, charSet));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try {
				String input = br.readLine();
				while (input != null) {
					System.out.println(input);
					input = br.readLine();
				}
			} catch (IOException e) {
			}
		}
	}

	private static class WatchProject implements Runnable {

		@Override
		public void run() {
			try {
				ProcessBuilder pb = new ProcessBuilder("java", "-cp", "./bin;./lib/*", "server.Main");
				WatchService watcher = FileSystems.getDefault().newWatchService();
				Path dir = FileSystems.getDefault().getPath("./bin");
				registerRecursive(dir, watcher);
				WatchKey key = dir.register(watcher, ENTRY_MODIFY);
				while (true) {
					Process process = pb.start();
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), "UTF-8"));

					Thread stdIn = new Thread(new ReadThread(process.getInputStream(), "UTF-8"));
					Thread errIn = new Thread(new ReadThread(process.getErrorStream(), "UTF-8"));
					stdIn.setDaemon(true);
					errIn.setDaemon(true);
					stdIn.start();
					errIn.start();
					try {
						System.out.println("변경 대기");
						key = watcher.take();
						for (WatchEvent<?> event : key.pollEvents()) {
							if (event.kind() == ENTRY_MODIFY) {
								System.out.println("변경 감지");
								bw.write("q\n");
								bw.flush();
								stdIn.interrupt();
								errIn.interrupt();
								process.waitFor(1000, TimeUnit.MILLISECONDS);
								process.destroy();
								break;
							}
						}
						key.reset();
					} catch (InterruptedException e) {
						stdIn.interrupt();
						errIn.interrupt();
						try {
							System.out.println("waitFor process");
							process.waitFor(1000, TimeUnit.MILLISECONDS);
						} catch (InterruptedException e1) {
						
						}
						System.out.println("process.destroy");
						process.destroy();
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void registerRecursive(final Path root, WatchService watchService) throws IOException {
			Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					dir.register(watchService, ENTRY_MODIFY);
					return FileVisitResult.CONTINUE;
				}
			});
		}
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("[Watch Service 시작] 종료하려면 q 입력");
		Thread watchProject = new Thread(new WatchProject());
		watchProject.setDaemon(true);
		watchProject.start();
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();
		while (!input.equals("q")) {
			System.out.println(input);
			input = scanner.next();
		}
		scanner.close();
		watchProject.interrupt();
		Thread.sleep(3000);
	}
}
