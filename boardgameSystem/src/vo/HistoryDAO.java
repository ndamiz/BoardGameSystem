package vo;


import java.util.ArrayList;
import java.util.List;

public class HistoryDAO extends DBConn{

	public HistoryDAO() {		}

	public List<HistoryVO> getMemberHistory(String id) {
		List<HistoryVO> lst = new ArrayList<HistoryVO>();	
		try {
			getConn();
			
			sql = "select to_char(redate,'yyyy-mm-dd'), roomnum, intime, "
					+ " to_char(outtime,'HH24:MI') from reservation "
					+ " where id =? and paycheck = 'Y' order by intime desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id); 
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				HistoryVO vo = new HistoryVO();
				vo.setRedate(rs.getString(1));
				vo.setRoomnum(rs.getInt(2));
				vo.setIntime(rs.getString(3));
				vo.setOuttime(rs.getString(4));
				lst.add(vo);
			}
		}catch(Exception e) {				          	         	         
	         e.printStackTrace();
		}finally {
			dbClose();
		}
		return lst;
	}
	
	public List<HistoryVO> getSelectedMemberHistory(String id, String date) {
		List<HistoryVO> lst = new ArrayList<HistoryVO>();	
		
		try {
			
			getConn();
			sql = "select roomnum, to_char(intime, 'HH24:MI'), to_char(outtime, 'HH24:MI'), gamename, pay from reservation r join ordergame o "
					+ " on r.roomrenum=o.roomrenum "
					+ " where id= ? and substr(to_char(intime,'yyyy-mm-dd'),1,10) = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, date);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				HistoryVO vo = new HistoryVO();			
				vo.setRoomnum(rs.getInt(1));
				vo.setIntime(rs.getString(2));
				vo.setOuttime(rs.getString(3));
				vo.setGamename(rs.getString(4));
				vo.setPay(rs.getInt(5));
				lst.add(vo);
			}
			
		}catch(Exception e) {
			try { // conn.roll이 예외가 발생하므로 예외처리를 해준다	           	      
	        e.printStackTrace();
		}finally {
			dbClose();
		}	
	}
		return lst;
	}
}





