package vo;

import java.util.ArrayList;
import java.util.List;


public class CallDAO extends DBConn{

	public CallDAO() {}
	
	public int InsertCall(int roomrenum) { // 사용자가 관리자 호출
		int result=0;
		
		try {
			getConn();
			
			sql="insert into admincall(roomrenum) values(?) ";
			
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
	
	public int UpdateCall(int roomrenum) { // 관리자가 완료 여부를 N -> Y로 변경
		int result=0;
		
		try {
			getConn();
			
			sql="update admincall set callcheck='Y' where roomrenum=? and callcheck='N'";
			
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, roomrenum);
			
			result=pstmt.executeUpdate();
		}catch(Exception e) {
		
		}finally {
			dbClose();
		}
		return result;
	}
	
	public  List<CallVO> getCallList() { // 관리자 호출 목록
		List<CallVO> lst= new ArrayList<CallVO>();  
		try {
			getConn();
			sql = "select ac.roomrenum, rv.id, rv.roomnum from admincall ac join reservation rv on ac.roomrenum=rv.roomrenum "
					+ " where to_char(calldate, 'YYYY-MM-dd')=to_char(sysdate, 'YYYY-MM-dd') and callcheck='N' order by calldate";
			pstmt = conn.prepareStatement(sql);
			
		 	rs = pstmt.executeQuery();
			while(rs.next()) { 
				CallVO vo = new CallVO(rs.getInt(1), rs.getString(2), rs.getInt(3));  
				lst.add(vo);
			} 
		}
		catch(Exception e) {
		e.printStackTrace();	
		}finally {
			dbClose();
		}
		return lst;
	}

}
