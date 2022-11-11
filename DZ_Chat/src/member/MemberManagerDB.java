package member;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Types;

import property.DBProperties;

public class MemberManagerDB implements MemberManagerInterface {
	private DBProperties dbProperties;
	private Connection conn;
	private PreparedStatement pstmt;
	private CallableStatement cstmt;
	
	public MemberManagerDB(String dbProperties) {
		this.dbProperties = DBProperties.getInstance(dbProperties);
	}
	
	
	private void open() {
		try {
			Class.forName(dbProperties.getDriverClass());
			System.out.println("JDBC 드라이버 로딩");
			conn = DriverManager.getConnection(
					dbProperties.getDbServerConn()
					, dbProperties.getDbUser()
					, dbProperties.getDbPasswd());
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
				if (cstmt != null) {
					cstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public boolean register(Member tmpMember, String pwChk) {
		if (!tmpMember.getPassword().equals(pwChk)) return false;
		try {
			open();
			pstmt = conn.prepareStatement(dbProperties.getInsertMemberQuery());
			pstmt.setString(1, tmpMember.getUserId());
			pstmt.setString(2, tmpMember.getPassword());
			pstmt.setString(3, tmpMember.getName());
			pstmt.setString(4, tmpMember.getBirth());
			return pstmt.executeUpdate() == 1;
		} catch (SQLIntegrityConstraintViolationException x) {
			// 이미 존재하는 회원
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();			
		}
		return false;
	}
	
	public Member login(String id, String pw) {
		try {
			open();
			pstmt = conn.prepareStatement(dbProperties.getFindMemberByUserIdQuery());
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String userId = rs.getString("userId");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String birth = rs.getString("birth");
				if (!password.equals(pw)) return null;
				return new Member(userId, password, name, birth);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	public boolean deleteMember(Member member, String pw) {
		if (!member.getPassword().equals(pw)) return false;
		try {
			open();
			
			cstmt = conn.prepareCall(dbProperties.getInsertDeleteMemberQuery());
			cstmt.setString(1, member.getUserId());
			cstmt.setString(2, member.getPassword());
			cstmt.setString(3, member.getName());
			cstmt.setString(4, member.getBirth());
			cstmt.registerOutParameter(5, Types.INTEGER);
			cstmt.execute();
			cstmt.close();
			
			pstmt = conn.prepareStatement(dbProperties.getDeleteMemberByUserIdQuery());
			pstmt.setString(1, member.getUserId());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public String findPw(String id) {
		try {
			open();
			pstmt = conn.prepareStatement(dbProperties.getFindMemberPasswordByUserIdQuery());
			pstmt.setString(1, id);
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

	public boolean updatePw(Member me, String validatePw, String newPw) {
		try {
			open();
			pstmt = conn.prepareStatement(dbProperties.getUpdateMemberPasswordQuery());
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
