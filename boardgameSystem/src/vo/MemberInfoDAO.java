package vo;

import java.util.ArrayList;
import java.util.List;

public class MemberInfoDAO extends DBConn {

	public MemberInfoDAO() {
		
	}
	//로그인 (id확인)
	public int userLoginIdCheck(String inputId) {
		int result = 0;
		try {
			getConn();
			
			sql = "select id from memberinfo where id = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, inputId);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	//로그인 (pw확인)
	public int userLoginPwCheck(String inputId, String inputPw) {
		int result = 0;
		try {
			getConn();
			
			sql = "select * from memberinfo where id=? and pw=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, inputId);
			pstmt.setString(2, inputPw);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	//중복확인
	public int userCheckId(String idCheck){
		int result = 0;
		try {
			getConn();
			sql = "select id from memberinfo where id like ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, idCheck);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	//회원가입
	public int userJoin(MemberInfoVO vo){
		int result = 0;
		try {
			getConn();
			
			sql = "insert into memberinfo(id, pw, name, tel, birth) "
					+ " values(?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getTel());
			pstmt.setString(5, vo.getBirth());
			
			result = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}
		return result;
	}
	//계정 아이디 찾기
	public List<MemberInfoVO> searchId(MemberInfoVO vo) {
		List<MemberInfoVO> lst = new ArrayList<MemberInfoVO>();
		try {
			getConn();
			sql = "select * from memberinfo where name=? and tel=? and birth=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getTel());
			pstmt.setString(3, vo.getBirth());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberInfoVO vo1 = new MemberInfoVO();
				vo1.setId(rs.getString(1));
				vo1.setPw(rs.getString(2));
				vo1.setName(rs.getString(3));
				vo1.setTel(rs.getString(4));
				vo1.setBirth(rs.getString(5));
				vo1.setJoindate(rs.getString(6));
				lst.add(vo1);
			}
		} catch (Exception e) {	e.printStackTrace();
		}finally {dbClose();}
		return lst;
	}
	//계정 입력값 맞는지 확인하기
	public List<MemberInfoVO> searchPw(MemberInfoVO vo) {
		List<MemberInfoVO> lst = new ArrayList<MemberInfoVO>();
		try {
			getConn();
			sql = "select * from memberinfo where id =? and name=? and tel=? and birth=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getTel());
			pstmt.setString(4, vo.getBirth());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberInfoVO vo1 = new MemberInfoVO();
				vo1.setId(rs.getString(1));
				vo1.setPw(rs.getString(2));
				vo1.setName(rs.getString(3));
				vo1.setTel(rs.getString(4));
				vo1.setBirth(rs.getString(5));
				vo1.setJoindate(rs.getString(6));
				lst.add(vo1);
			}
		} catch (Exception e) {	e.printStackTrace();
		}finally {dbClose();}
		return lst;
	}
	//계정 비밀번호 변경
	public int changePw(String id, String changePw) {
		int result = 0;
		try {
			getConn();
			sql = "update memberinfo set pw=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, changePw);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
		} catch (Exception e) {	e.printStackTrace();
		}finally {dbClose();}
		return result;
	}
	//개인정보를 수정할 회원정보 선택
	public List<MemberInfoVO> setModifyPersonalInfo(String id) {
		List<MemberInfoVO> lst = new ArrayList<MemberInfoVO>();
		try {
			getConn();
			
			sql = "select name, birth, tel, id, pw from MemberInfo where id=? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberInfoVO vo = new MemberInfoVO();
				vo.setName(rs.getString(1));
				vo.setBirth(rs.getString(2));
				vo.setTel(rs.getString(3));
				vo.setId(rs.getString(4));
				vo.setPw(rs.getString(5));
				lst.add(vo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return lst;
	}
	//연락처 변경 
	public int telChange(String id, String tel) {
		int result = 0;
		try {
			getConn();
			
			sql = "update memberinfo set tel=? where id=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tel);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
		// 회원 개인정보 수정
		public int memberPersonlInfoUpdate(MemberInfoVO vo) {
			int result = 0;
			try {
				getConn();
				
				sql = "update MemberInfo set tel = ?, pw = ? where id = ? ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getTel());
				pstmt.setString(2, vo.getPw());
				pstmt.setString(3, vo.getId());
				
				result = pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				dbClose();
			}
			return result;
		}
		
		//회원 삭제
		public int memberDelete(String id) {
			int result = 0;
			try {
				getConn();
				
				sql = "delete from memberinfo where ID=? ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				
				result = pstmt.executeUpdate();
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				dbClose();
			}
			return result;
		}
}
