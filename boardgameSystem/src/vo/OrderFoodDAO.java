package vo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class OrderFoodDAO extends DBConn {

	public OrderFoodDAO() {
		
	}

	public List<OrderFoodVO> Foodset(String category) {
		List<OrderFoodVO> lst = new ArrayList<OrderFoodVO>();
			
		try {
			getConn();
			
			sql = "select foodname, foodprice, foodpic from food where foodcategory = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OrderFoodVO vo = new OrderFoodVO();
				vo.setFoodName(rs.getString(1));
				vo.setFoodPrice(rs.getInt(2));
				
				Blob blob = rs.getBlob("foodpic");
				int bloblength = (int)blob.length();
				byte[] bytes = blob.getBytes(1, bloblength);
				blob.free();
				BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
				ImageIcon icon = new ImageIcon(img);
				vo.setFoodpic(icon);
				
				lst.add(vo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return lst;
	}
	
	public int InsertOrder(int roomrenum) { // 주문 번호 생성
		int result=0;
		
		try {
			getConn();
			
			sql="insert into ordernum values(order_sq.nextval, ?, sysdate) ";
			
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
	
	
	public List<OrderFoodVO> getOrdernum(int roomrenum){
		List<OrderFoodVO> lst = new ArrayList<OrderFoodVO>();
		
		try {
			getConn();
			sql="select ordernum from ordernum where roomrenum=? "
					+ " and to_char(ordertime,'HH24:MI:SS')=to_char(sysdate,'HH24:MI:SS')";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomrenum);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderFoodVO vo = new OrderFoodVO();
				vo.setOrdernum(rs.getInt(1));
				lst.add(vo);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
	
	public int InsertBuyFood(int ordernum, int roomrenum, String foodName, int foodStock) {
		int result=0;
		
		try {
			getConn();
			sql="insert into orderdetail values(?, ?, null,?,?,sysdate)";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, ordernum);
			pstmt.setInt(2, roomrenum);
			pstmt.setString(3,foodName);
			pstmt.setInt(4, foodStock);
			
			result=pstmt.executeUpdate();
	
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	
	public int updateFoodStock( int foodStock, String foodName ) {
		int result = 0;
		
		try {
			
			getConn();
			
			sql = "update food set foodstock = ? where foodname = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, foodStock);
			pstmt.setString(2, foodName);
			
			
			result = pstmt.executeUpdate();
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
	
		}
		return result;
	}
	
	public List<OrderFoodVO> getOrderFoodStock(String foodname){
		List<OrderFoodVO> lst = new ArrayList<OrderFoodVO>();
		
		try {
			getConn();
			
			sql = "select foodstock from food where foodname = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, foodname);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderFoodVO vo = new OrderFoodVO();
				vo.setFoodStock(rs.getInt(1));
				lst.add(vo);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		
		
		return lst;
	}
	
	public int orderNumDelete(int orderNum) {
		int result = 0;
		
		try {
			
			getConn();
			
			sql = "delete from ordernum where ordernum = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, orderNum);
			
			result = pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
			
		}
		
		return result;
	}
	
	public int orderDetailDelete(int orderNum) {
		int result = 0;
		
		try {
			
			getConn();
			
			sql = "delete from orderdetail where ordernum = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, orderNum);
			
			result = pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
			
		}
		
		return result;
	}
	
	public int returnFoodStock( int foodStock, String foodName ) {
		int result = 0;
		
		try {
			
			getConn();
			
			sql = "update food set foodstock = ? where foodname = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, foodStock);
			pstmt.setString(2, foodName);
			
			
			result = pstmt.executeUpdate();
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
	
		}
		return result;
	}
	public int foodOrderCheck(int ordernum) {
		int result=0;
		
		try {
			getConn();
			sql="select foodname, ordercount from orderdetail where ordernum=? "
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
