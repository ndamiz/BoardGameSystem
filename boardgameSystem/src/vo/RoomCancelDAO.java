package vo;

import java.util.ArrayList;
import java.util.List;

public class RoomCancelDAO extends DBConn{

	public RoomCancelDAO() {
		
	}
	
	public List<RoomCancelVO> SelectRoom(String id) {
		List<RoomCancelVO> lst=new ArrayList<RoomCancelVO>();
		
		try { 
			getConn();
			
			sql="select roomrenum, to_char(intime,'YYYY-MM-DD'), roomnum, "
					+ " to_char(intime,'HH24:MI'), to_char(outtime,'HH24:MI') "
					+ " from reservation where id=? and intime>sysdate order by intime";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				RoomCancelVO vo=new RoomCancelVO();
				vo.setRoomrenum(rs.getInt(1));
				vo.setInday(rs.getString(2));
				vo.setRoomnum(rs.getInt(3));
				vo.setIntime(rs.getString(4));
				vo.setOuttime(rs.getString(5));
				
				lst.add(vo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return lst;
	}
	
	
	public int ReservationCancel(String id, int roomrenum) {
		int result=0;
		try {
			getConn();
			
			sql="delete from reservation where id=? and roomrenum=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, roomrenum);
			
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	
}
