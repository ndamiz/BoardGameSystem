package vo;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO extends DBConn{

	public OrderDetailDAO() {	}
	//음식구매
	public List<OrderDetailVO> getOrderFood(int roomrenum){
		List<OrderDetailVO> lst = new ArrayList<OrderDetailVO>();
		
		try {
			getConn();
			sql="select od.roomrenum, od.foodname, od.ordercount, f.foodprice, (od.ordercount * f.foodprice) "
					+ " from orderdetail od join food f on od.foodname = f.foodname where roomrenum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomrenum);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderDetailVO vo = new OrderDetailVO();
				vo.setRoomrenum(rs.getInt(1));
				vo.setFoodname(rs.getString(2));
				vo.setOrdercount(rs.getInt(3));
				vo.setFoodprice(rs.getInt(4));
				vo.setOrderprice(rs.getInt(5));
				lst.add(vo);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
	//게임 구매
	public List<OrderDetailVO> getOrderGame(int roomrenum){
		List<OrderDetailVO> lst = new ArrayList<OrderDetailVO>();
		
		try {
			getConn();
			sql="select od.roomrenum, od.gamename, od.ordercount, g.gameprice, (od.ordercount * g.gameprice) "
					+ " from orderdetail od join game g on od.gamename = g.gamename where roomrenum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomrenum);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderDetailVO vo = new OrderDetailVO();
				vo.setRoomrenum(rs.getInt(1));
				vo.setGamename(rs.getString(2));
				vo.setOrdercount(rs.getInt(3));
				vo.setGameprice(rs.getInt(4));
				vo.setOrderprice(rs.getInt(5));
				lst.add(vo);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
	//게임구매금액 합계 
	public int getOrderGamePrice(int roomrenum) {
		int price = 0;
		
		try {
			getConn();
			sql="select sum(od.ordercount * g.gameprice) from orderdetail od join game g "
					+ " on od.gamename = g.gamename where roomrenum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomrenum);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				price = rs.getInt(1); 
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return price;
	}
	//음식 구매금액 합계 
	public int getOrderFoodPrice(int roomrenum) {
		int price = 0;
		
		try {
			getConn();
			sql="select sum(od.ordercount * f.foodprice) from orderdetail od "
					+ " join food f on od.foodname = f.foodname where roomrenum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomrenum);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				price = rs.getInt(1); 
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return price;
	}
	//주문목록 띄우기위한, 오더넘버, 룸넘버,주문자id, 주문룸번호 받아오기 
	public List<OrderDetailVO> getOrderNum(){
		List<OrderDetailVO> lst = new ArrayList<OrderDetailVO>();
		
		try {
			getConn();
			sql="select od.ordernum, od.roomrenum, rv.id, rv.roomnum from ordernum od join reservation rv "
					+ " on od.roomrenum=rv.roomrenum "
					+ " where to_char(od.ordertime, 'YYYY-MM-dd')=to_char(sysdate, 'YYYY-MM-dd') order by od.ordernum desc";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderDetailVO vo = new OrderDetailVO();
				vo.setOrdernum(rs.getInt(1));
				vo.setRoomrenum(rs.getInt(2));
				vo.setId(rs.getString(3));
				vo.setRoomnum(rs.getInt(4));
				lst.add(vo);
			}
		}catch(Exception e) {
			//e.printStackTrace();
		}
		return lst;
	}
	public int gameOrderCheck(int ordernum) {
		int result=0;
		
		try {
			getConn();
			sql="select gamename, ordercount from orderdetail where ordernum=? "
					+ " and to_char(orderdate, 'YYYY-MM-dd') =to_char(sysdate, 'YYYY-MM-dd') ";
			
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
}
