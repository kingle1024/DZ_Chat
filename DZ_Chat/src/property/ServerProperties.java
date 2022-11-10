package property;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ServerProperties {
	private static Properties serverProperties = new Properties();

	static {
		try {
			serverProperties.load(new FileInputStream("resources/server.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getDirLogFile() {
		return serverProperties.getProperty("DIR_LOG_FILE");
	}
	
	public static String getChatLogFile() {
		return serverProperties.getProperty("CHAT_LOG_FILE");
	}
	
	public static String getIP() {
		return serverProperties.getProperty("IP");
	}
	
	public static int getFTPPort() {
		return Integer.parseInt(serverProperties.getProperty("FTP_PORT"));
	}
	
	public static String getDownloadPath() {
		return serverProperties.getProperty("DOWNLOAD_PATH");
	}
	
	public static int getThreadPool() {
		return Integer.parseInt(serverProperties.getProperty("THREAD_POOL"));
	}
	
	public static int getServerPort() {
		return Integer.parseInt(serverProperties.getProperty("SERVER_PORT"));
	}
	
	public static String getMemberFile() {
		return serverProperties.getProperty("MEMBER_FILE");
	}

	public static String getConnectProperties() {
		return serverProperties.getProperty("connect.properties");
	}
	
    public static void main(String[] args) {
        File file = new File("resource");
        System.out.println(file.getAbsolutePath());
    }
}
