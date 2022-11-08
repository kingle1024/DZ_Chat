package log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import property.ServerProperties;

public class LogManagerFile implements LogManagerInterface {
	private static final String filePath = "./" + ServerProperties.getChatLogFile();
	private BufferedOutputStream out = null;

	@Override
	public void logSave(Log log) {
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
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
	}
}
