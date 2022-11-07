package property;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBProperties {
	private static Properties queryProperties = null;

	static {
		try {
			queryProperties.load(new FileInputStream(ServerProperties.getDBInfo()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getInsertLog() {
		return queryProperties.getProperty("INSERT_LOG");
	}
	
	public static String getDriverClass() {
		return queryProperties.getProperty("driverClass");
	}
	
	public static String getDbServerConn() {
		return queryProperties.getProperty("dbServerConn");
	}
	
	public static String getDbPasswd() {
		return queryProperties.getProperty("dbPasswd");
	}
	
	public static String getDbUser() {
		return queryProperties.getProperty("dbUser");
	}
}
