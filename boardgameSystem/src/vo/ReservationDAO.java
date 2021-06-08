package vo;

import java.util.ArrayList;
import java.util.List;


public class ReservationDAO extends DBConn{

	public ReservationDAO() {
		
	}
	// 해당 날짜에 해당 룸번호를 사용하여 , 예약 입실시간, 퇴실시간 확인하기. 
	public List<ReservationVO> TimeSet(String redate, int roomnum) {
		List<ReservationVO> lst = new ArrayList<ReservationVO>();
		try {
			getConn();
			sql = "select to_char(intime,'HH24:MI'), to_char(outtime,'HH24:MI') from reservation "
					+ " where redate=? and roomnum=? order by intime";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, redate);
			pstmt.setInt(2, roomnum);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReservationVO vo = new ReservationVO();
				vo.setInTime(rs.getString(1));
				vo.setOutTime(rs.getString(2));
				lst.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}
		return lst;
	}
	public int reservationSet(ReservationVO vo) {
		int result = 0;
		try {
			getConn();
			
			sql = "insert into reservation "
				+ "values(RESER_sq.nextval, ?, ?, ?, to_date( ? ,'YYYY-MM-DD HH24:MI'), "
				+ "to_date( ? ,'YYYY-MM-DD HH24:MI'), ?, 'N')";
				
			String inTime = vo.getReDate()+" "+vo.getInTime();
			String outTime = vo.getReDate()+" "+vo.getOutTime();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setInt(2, vo.getRoomNum());
			pstmt.setString(3, vo.getReDate());
			pstmt.setString(4, inTime);
			pstmt.setString(5, outTime);
			pstmt.setInt(6, vo.getPay());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	
	// 아이디,날짜 입력해서 예약사항 확인하기.  
	public List<ReservationVO> usingStateSet(String id, String redate) {
		List<ReservationVO> lst = new ArrayList<ReservationVO>();
		try {
			getConn();
			sql = "select roomrenum, id, roomnum, to_char(redate,'YYYY-MM-dd'), to_char(intime,'HH24:MI'), to_char(outtime,'HH24:MI'), pay, paycheck from reservation "
					+ " where id=? and redate =? and paycheck = 'N'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, redate);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReservationVO vo = new ReservationVO();
				vo.setRoomReNum(rs.getInt(1));
				vo.setId(rs.getString(2));
				vo.setRoomNum(rs.getInt(3));
				vo.setReDate(rs.getString(4));
				vo.setInTime(rs.getString(5));
				vo.setOutTime(rs.getString(6));
				vo.setPay(rs.getInt(7));
				vo.setPayCheck(rs.getString(8));
				lst.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}
		return lst;
	}
	//아이디 확인해서 결제가 안된 예약사항이 있는지 확인하기.  
	public int checkMemberDel(String id) {
		int result=0;
		try {
			getConn();
			sql = "select paycheck from reservation "
					+ " where id=? and paycheck = 'N'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	public int getRoomrenum(String id) {
		int result=0;
		try {
			getConn();
			sql = "select roomrenum from reservation "
					+ " where id=? and to_char(redate,'YYYY-MM-dd')=to_char(sysdate,'YYYY-MM-dd')";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	public int getRoomnum(String id) {
		int result=0;
		try {
			getConn();
			sql = "select roomnum from reservation "
					+ " where id=? and to_char(redate,'YYYY-MM-dd')=to_char(sysdate,'YYYY-MM-dd')";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	//현재시간 사이에 사용중인 룸에대한 정보 받아오기 
	public List<ReservationVO> getRoomInfo(int roomnum) {
		List<ReservationVO> lst = new ArrayList<ReservationVO>();
		try {
			getConn();
			sql = "select roomrenum, id, roomnum, to_char(redate, 'YYYY-MM-dd'), to_char(intime, 'HH24:MI'), "
					+ " to_char(outtime, 'HH24:MI'), pay, paycheck from reservation "
					+ " where roomnum=? and sysdate between intime and outtime";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, roomnum);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReservationVO vo = new ReservationVO();
				vo.setRoomReNum(rs.getInt(1));
				vo.setId(rs.getString(2));
				vo.setRoomNum(rs.getInt(3));
				vo.setReDate(rs.getString(4));
				vo.setInTime(rs.getString(5));
				vo.setOutTime(rs.getString(6));
				vo.setPay(rs.getInt(7));
				vo.setPayCheck(rs.getString(8));
				lst.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}
		return lst;
	}
	//다음타임 사용자 받아오기. 
	public List<ReservationVO> getNextReserveRoomInfo(int roomnum) {
		List<ReservationVO> lst = new ArrayList<ReservationVO>();
		try {
			getConn();
			sql = "select roomrenum, id, roomnum, to_char(redate, 'YYYY-MM-dd'), to_char(intime, 'HH24:MI'), "
					+ "	to_char(outtime, 'HH24:MI'), pay, paycheck from reservation "
					+ " where roomnum=? and sysdate <= intime order by intime asc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomnum);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReservationVO vo = new ReservationVO();
				vo.setRoomReNum(rs.getInt(1));
				vo.setId(rs.getString(2));
				vo.setRoomNum(rs.getInt(3));
				vo.setReDate(rs.getString(4));
				vo.setInTime(rs.getString(5));
				vo.setOutTime(rs.getString(6));
				vo.setPay(rs.getInt(7));
				vo.setPayCheck(rs.getString(8));
				lst.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}
		return lst;
	}
	public int getPay(int roomrenum) {
		int nowPay = 0;
		try {
			getConn();
			sql = "select pay from reservation where roomrenum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomrenum);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				nowPay = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}
		return nowPay;
	}
	public int setOutTimeChange(int roomrenum, int changePay, String outTime) {
		int result = 0;
		try {
			getConn();
			sql = "update reservation set outtime=to_date(?, 'YYYY-MM-dd HH24:MI'), pay=? where roomrenum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, outTime);
			pstmt.setInt(2, changePay);
			pstmt.setInt(3, roomrenum);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	public int setPayCheckYes(int roomrenum, int pay) {
		int result = 0;
		try {
			getConn();
			sql = "update reservation set pay=?, paycheck='Y' where roomrenum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pay);
			pstmt.setInt(2, roomrenum);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
}
