package log;

import java.time.LocalDateTime;

public class Log {
	private String path;
	private String log;
	private LocalDateTime time;

	public Log(String path, String log) {
		this.path = path;
		this.log = log;
		this.time = LocalDateTime.now();
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
