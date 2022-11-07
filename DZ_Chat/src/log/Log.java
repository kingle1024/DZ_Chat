package log;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Log {
	private String path;
	private String log;
	private String time;
	private DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private Date createDate;

	public Log(String path, String log) {
		this.path = path;
		this.log = log;
		LocalDateTime now = LocalDateTime.now();
		this.time = now.format(pattern);
		this.createDate = new Date(Calendar.getInstance().getTime().getTime());
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
	
	public Date getCreateDate() {
		return createDate;
	}


}
