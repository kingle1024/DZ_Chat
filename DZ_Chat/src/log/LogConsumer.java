package log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import property.Property;

public class LogConsumer implements Runnable {
	private static final LogQueue logQueue = LogQueue.getInstance();
	private static final Object monitor = logQueue.getMonitor();
	private static final String filePath = "./" + Property.server().get("CHAT_LOG_FILE");
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	BufferedOutputStream out = null;
	Log logPoll;

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					logPoll = logQueue.poll();
					logFileSave(logPoll);
					logDBSave(logPoll);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e) {

		}
	}

	public void logFileSave(Log log) throws IOException {
		System.out.println("Log > logFileSave");
		try {
			File file = new File(filePath);

			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream f = new FileOutputStream(file, true);
			String lineToAppend = log.getTimeLog() + "\n";
			out = new BufferedOutputStream(f);
			byte[] byteArr = lineToAppend.getBytes();
			out.write(byteArr);
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	public void logDBSave(Log log) throws IOException {
		System.out.println("Log > logDBSave");

		try {
			open();
			pstmt = conn.prepareStatement(Property.server().get("INSERT_LOG"));

			pstmt.setDate(1, log.getCreateDate());
			pstmt.setString(2, log.getLog());
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

	}

	private void open() {
		try {
			Class.forName(Property.server().get("driverClass"));
			System.out.println("JDBC 드라이버 로딩 성공");

			conn = DriverManager.getConnection(Property.server().get("dbServerConn"), Property.server().get("dbUser"),
					Property.server().get("dbPasswd"));
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void close() {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void consumeAllLog() {
		synchronized (monitor) {
			monitor.notify();
		}
	}
}