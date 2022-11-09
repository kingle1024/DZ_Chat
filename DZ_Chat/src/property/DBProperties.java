package property;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DBProperties {
	private static final Map<String, DBProperties> cache = new HashMap<>();
	private Properties queryProperties = new Properties();
	
	public static DBProperties getInstance(String propertiesPath) {
		if (cache.containsKey(propertiesPath)) return cache.get(propertiesPath);
		DBProperties db = new DBProperties(propertiesPath);
		cache.put(propertiesPath, db);
		return db;
	}
	
	private DBProperties(String propertiesPath) {
		try {
			queryProperties.load(new FileInputStream(propertiesPath));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public String getDriverClass() {
		return queryProperties.getProperty("driverClass");
	}
	
	public String getDbServerConn() {
		return queryProperties.getProperty("dbServerConn");
	}
	
	public String getDbPasswd() {
		return queryProperties.getProperty("dbPasswd");
	}
	
	public String getDbUser() {
		return queryProperties.getProperty("dbUser");
	}
	
	public String getInsertLogQuery() {
		return queryProperties.getProperty("INSERT_LOG");
	}
	
	public String getInsertMemberQuery() {
		return queryProperties.getProperty("INSERT_MEMBER");
	}
	
	public String getFindMemberByUserIdQuery() {
		return queryProperties.getProperty("FIND_MEMBER_BY_USERID");
	}
	
	public String getDeleteMemberByUserIdQuery() {
		return queryProperties.getProperty("DELETE_MEMBER_BY_USERID");
	}
	
	public String getFindMemberPasswordByUserIdQuery() {
		return queryProperties.getProperty("FIND_MEMBER_PASSWORD_BY_USERID");
	}
	
	public String getUpdateMemberPasswordQuery() {
		return queryProperties.getProperty("UPDATE_MEMBER_PASSWORD");
	}
}
