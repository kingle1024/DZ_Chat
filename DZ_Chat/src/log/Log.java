package log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Log {
	private String path;
	private String log;
	private static SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Date createDate;

	public Log(String path, String log) {
		this.path = path;
		this.log = log;
		this.createDate = new Date(Calendar.getInstance().getTime().getTime());
	}

	public String getTimeLog() {
		return "[" + getCreateDateStr() + "]" + log;
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
	
	public String getCreateDateStr() {
		return pattern.format(createDate);
	}


}