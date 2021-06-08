package vo;

import java.util.ArrayList;
import java.util.List;

public class RoomInfoDAO extends DBConn{

	public RoomInfoDAO() {
		
	}
	//룸인원수 받아와서 -> 룸정보 리턴 
	public List<RoomInfoVO> setRoomInfo(int roomPeople){
		List<RoomInfoVO> lst = new ArrayList<RoomInfoVO>();
		try {
			getConn();
			sql = "select * from roominfo where roompeople = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomPeople);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RoomInfoVO vo = new RoomInfoVO();
				vo.setRoomNum(rs.getInt(1));
				vo.setRoomPeople(rs.getInt(2));
				vo.setRoomPrice(rs.getInt(3));
				lst.add(vo);
			}
		} catch (Exception e) {	e.printStackTrace();
		}finally {dbClose();}
		
		return lst;
	}
	//룸번호 받아와서 -> 룸정보 리턴 
	public List<RoomInfoVO> setRoomPay(int roomNum){
		List<RoomInfoVO> lst = new ArrayList<RoomInfoVO>();
		try {
			getConn();
			sql = "select * from roominfo where roomnum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNum);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RoomInfoVO vo = new RoomInfoVO();
				vo.setRoomNum(rs.getInt(1));
				vo.setRoomPeople(rs.getInt(2));
				vo.setRoomPrice(rs.getInt(3));
				lst.add(vo);
			}
		} catch (Exception e) {	e.printStackTrace();
		}finally {dbClose();}
		
		return lst;
	}

}
