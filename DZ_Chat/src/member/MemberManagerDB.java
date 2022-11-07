package member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberManagerDB {
	private static Connection conn;
	private static PreparedStatement pstmt;
	
	private MemberManagerDB() { }
	
	private static void open() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("JDBC 드라이버 로딩");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/xe"
					, "user1"
					, "passwd");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void close() {
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
	
	public static boolean register(Member tmpMember, String pwChk) {
		if (!tmpMember.getPassword().equals(pwChk)) return false;
		try {
			open();
			pstmt = conn.prepareStatement("insert into member (userid, password, name, birth) values (?, ?, ?, ?)");
			pstmt.setString(1, tmpMember.getUserId());
			pstmt.setString(2, tmpMember.getPassword());
			pstmt.setString(3, tmpMember.getName());
			pstmt.setString(4, tmpMember.getBirth());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();			
		}
		return false;
	}
	
	public static Member login(String id, String pw) {
		try {
			open();
			pstmt = conn.prepareStatement("select * from member where userid = ?");
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String userId = rs.getString("userId");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String birth = rs.getString("birth");
				return new Member(userId, password, name, birth);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	public static boolean deleteMember(Member member, String pw) {
		if (!member.getPassword().equals(pw)) return false;
		try {
			open();
			pstmt = conn.prepareStatement("delete from member where userId = ?");
			return pstmt.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public static String findPw(String id) {
		try {
			open();
			pstmt = conn.prepareStatement("select password from member where userId = id");
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	public static boolean updatePw(Member me, String validatePw, String newPw) {
		try {
			open();
			pstmt = conn.prepareStatement("update member set password = ? where userId = ?, password = ?");
			pstmt.setString(1, newPw);
			pstmt.setString(2, me.getUserId());
			pstmt.setString(3,  validatePw);
			return pstmt.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
}
