package log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
	private String path;
	private String log;
	private String time;
	private DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
	
	public Log(String path, String log) {
		this.path = path;
		this.log = log;
		 LocalDateTime now = LocalDateTime.now();
		this.time = now.format(pattern);
	}

	public String getTimeLog() {
		return "[" + time + "]" + log;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

}
