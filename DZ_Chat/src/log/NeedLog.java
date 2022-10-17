package log;

public interface NeedLog {
	static final LogQueue logQueue = LogQueue.getInstance();
	Log toLog();
}
