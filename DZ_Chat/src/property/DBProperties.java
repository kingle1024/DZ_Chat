package property;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBProperties {
	private Properties queryProperties = new Properties();
	
	public DBProperties(String propertiesPath) {
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
