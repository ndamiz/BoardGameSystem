package vo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConn {
	//1. 드라이브로딩 
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch (Exception e) {
			System.out.println("드라이브 로딩 에러 발생" + e.getMessage());
		}
	}
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	String url = "jdbc:oracle:thin:@bit.cooxhzpbmubk.ap-northeast-2.rds.amazonaws.com:1521:BITDB";
	String userid = "admin";
	String userpwd = "master1234";
	
	public DBConn() {}
	//DB연결 
	public void getConn() {
		try {
			conn = DriverManager.getConnection(url, userid, userpwd);
		}catch (Exception e) {
			System.out.println("DB연결 에러 발생 -> " + e.getMessage());
		}
	}
	//DB접속종료 
	public void dbClose() {
		try {
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
			if(conn!=null) conn.close();
		}catch (Exception e) {
			System.out.println("DB종료 에러 ->" + e.getMessage());
		}
	}
}
