package vo;

public class AdminDAO extends DBConn{

	public AdminDAO() {
		
	}
	public int orderSqCreat() {
		int result=0;
		try {
			getConn();
			
			sql="create sequence order_sq "
					+ "start with 1 "
					+ "increment by 1 ";
			pstmt=conn.prepareStatement(sql);

			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}

	public int orderNumTblDel() {
		int result=0;
		try {
			getConn();
			
			sql="delete from ordernum";
			pstmt=conn.prepareStatement(sql);

			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	public int orderSqDel() {
		int result=0;
		try {
			getConn();
			
			sql="drop sequence order_sq";
			pstmt=conn.prepareStatement(sql);

			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	public int orderSqResetCheck() {
		int result=0;
		try {
			getConn();
			
			sql="select * from adminlogindate where "
					+ " to_char(logindate, 'YYYY-MM-dd') = to_char(sysdate, 'YYYY-MM-dd')";
			pstmt=conn.prepareStatement(sql);

			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	
	public int adminLoginDate() {
		int result=0;
		try {
			getConn();
			
			sql="insert into adminlogindate values(sysdate)";
			pstmt=conn.prepareStatement(sql);
		
			
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}

}
