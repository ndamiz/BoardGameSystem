package vo;

import java.util.ArrayList;
import java.util.List;


public class OrderGameDAO extends DBConn{

	public OrderGameDAO() {
		
	}
	
	public int InsertOrder(int roomrenum) { // 주문 번호 생성
		int result=0;
		
		try {
			getConn();
			
			sql="insert into ordernum values(order_Sq.nextval, ?, sysdate) ";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, roomrenum);
			
			result=pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	
	public List<OrderGameVO> CheckOrder(int roomrenum) { // 주문번호 가져오기
		List<OrderGameVO> lst=new ArrayList<OrderGameVO>();
		
		try {
			getConn();
			
			sql="select ordernum from ordernum where roomrenum=? "
					+ " and to_char(ordertime,'HH24:MI:SS')=to_char(sysdate,'HH24:MI:SS')";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, roomrenum);
			
			rs=pstmt.executeQuery();
			while(rs.next()) {
				OrderGameVO vo=new OrderGameVO();
				vo.setOrdernum(rs.getInt(1));
				
				lst.add(vo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return lst;
	}
	
	public int InsertRentGame(int ordernum,int roomrenum, String gamename) { // 게임 상세정보 생성
		int result=0;
		
		try {
			getConn();
			
			sql="insert into ordergame values(?, ? ,?,sysdate, null,null,'대여')";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, ordernum);
			pstmt.setInt(2, roomrenum);
			pstmt.setString(3, gamename);
			
			result=pstmt.executeUpdate();
		}catch(Exception e) {
		//S	e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}

	public int UpdateRentGame(int ordernum,int roomrenum, String gamename) { // 반납 상세정보 변경
		int result=0;
		
		try {
			getConn();
			
			sql="update ordergame set gamerenttime=sysdate, gamecheck='반납' where roomrenum=? and gamecheck='대여'";
			
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, roomrenum);
			
			result=pstmt.executeUpdate();
		}catch(Exception e) {
		
		}finally {
			dbClose();
		}
		return result;
	}
	
	public int InsertReserveGame(int ordernum,int roomrenum, String gamename) {
		int result=0;
		
		try {
			getConn();
			sql="insert into ordergame values(?, ?, ?, sysdate, null, null, '예약')";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, ordernum);
			pstmt.setInt(2, roomrenum);
			pstmt.setString(3, gamename);
			
			result=pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	
	public int InsertBuyGame(int ordernum, int roomrenum, String gamename) {
		int result=0;
		
		try {
			getConn();
			sql="insert into orderdetail values(?, ?, ?,null,1,sysdate)";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, ordernum);
			pstmt.setInt(2, roomrenum);
			pstmt.setString(3,gamename);
			
			result=pstmt.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	public int gameRentCheck(int ordernum) {
		int result=0;
		
		try {
			getConn();
			sql="select gamename, gamecheck from ordergame where ordernum=? "
					+ " and to_char(gamerenttime, 'YYYY-MM-dd') =to_char(sysdate, 'YYYY-MM-dd') "
					+ " and gamecheck='대여'";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, ordernum);
			
			result=pstmt.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	public int gameReserveCheck(int ordernum) {
		int result=0;
		
		try {
			getConn();
			sql="select gamename, gamecheck from ordergame where ordernum=? "
					+ " and to_char(gamerenttime, 'YYYY-MM-dd') =to_char(sysdate, 'YYYY-MM-dd') "
					+ " and gamecheck='예약'";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, ordernum);
			
			result=pstmt.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	public OrderGameVO getOrderGame(int ordernum) {
		OrderGameVO vo = new OrderGameVO();
		try {
			getConn();
			sql="select ordernum, gamename, gamecheck from ordergame where ordernum=? "
					+ " and to_char(gamerenttime, 'YYYY-MM-dd') =to_char(sysdate, 'YYYY-MM-dd') ";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, ordernum);
			
			rs=pstmt.executeQuery();
			while (rs.next()) {
				vo.setOrdernum(rs.getInt(1));
				vo.setGamename(rs.getString(2));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return vo;
	}
	//대여 
	public String getNowGame(int roomrenum) {
		String nowGame = "";
		try {
			getConn();
			sql="select gamename from ordergame where roomrenum=? and gamecheck='대여'";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, roomrenum);
			
			rs=pstmt.executeQuery();
			while (rs.next()) {
				nowGame = rs.getString(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return nowGame;
	}
	//예약
	public String getReserveGame(int roomrenum) {
		String reserveGame = "";
		try {
			getConn();
			sql="select gamename from ordergame where roomrenum=? and gamecheck='예약'";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, roomrenum);
			
			rs=pstmt.executeQuery();
			while (rs.next()) {
				reserveGame = rs.getString(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return reserveGame;
	}
	public String getRentOrReserveGame(int ordernum, int roomrenum) {
		String gamename = "";
		try {
			getConn();
			sql="select gamename from ordergame where ordernum=? and roomrenum=? ";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, ordernum);
			pstmt.setInt(2, roomrenum);
			
			rs=pstmt.executeQuery();
			while (rs.next()) {
				gamename = rs.getString(1); 
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return gamename;
	}
	
}
