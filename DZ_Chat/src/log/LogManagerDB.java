package log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import property.DBProperties;

public class LogManagerDB implements LogManagerInterface{
	private DBProperties dbProperties;
	private Connection conn;
	private PreparedStatement pstmt;

	public LogManagerDB(String dbProperties) {
		this.dbProperties = DBProperties.getInstance(dbProperties);
	}

	private void open() {
		try {
			// TODO 클래스 계속 로딩(X) 리팩토링 필요 
			Class.forName(dbProperties.getDriverClass());
			System.out.println("JDBC 드라이버 로딩");
			conn = DriverManager.getConnection(dbProperties.getDbServerConn(), dbProperties.getDbUser(),
					dbProperties.getDbPasswd());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void logSave(Log log) {
		try {
			open();
			pstmt = conn.prepareStatement(dbProperties.getInsertLogQuery());

			pstmt.setString(1, log.getCreateDateStr());
			pstmt.setString(2, log.getLog());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
	}
}