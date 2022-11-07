package property;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ClientProperties {
	private static Properties clientProperties = null;
	
	static {
		try {
			clientProperties.load(new FileInputStream("resources/client.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getChatLogFile() {
		return clientProperties.getProperty("CHAT_LOG_FILE");
	}

	public static String getDefaultBufferSize() {
		return clientProperties.getProperty("DEFAULT_BUFFER_SIZE");
	}
}
