package vo;
 
 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class MemberDAO extends DBConn{
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	
	//회원 전체 목록 가져오기 메서드
			public  List<MemberVO>  getMemberList() { 
				List<MemberVO> lst= new ArrayList<MemberVO>();  
				
				try {
					getConn();
					sql = "select id, pw, name, tel, to_char(birth, 'YYYY-MM-dd'), to_char(joindate,'YYYY-MM-dd') "
							+" from memberinfo order by id asc";
					pstmt = conn.prepareStatement(sql);
					
				 	rs = pstmt.executeQuery();
					while(rs.next()) { 
						MemberVO vo = new MemberVO(rs.getString(1), rs.getString(2), rs.getString(3),
								 rs.getString(4), rs.getString(5), rs.getString(6));  
						lst.add(vo);						
					} 
					
				}
				catch(Exception e) {
				e.printStackTrace();	
				}finally {}
				
				return lst;				
			}
			 
			
		//회원 검색
			public List<MemberVO> getSearchRecord(String searchWord){
				List<MemberVO> lst = new ArrayList<MemberVO>();
				try {
					getConn();
					sql = "select id, pw, name, tel, to_char(birth, 'YYYY-MM-dd'), to_char(joindate,'YYYY-MM-dd') "
							+ " from memberinfo where name like? or id like? or "
							+ " pw like? or name like? or tel like? or birth like? or "
							+ " joindate like? order by name asc";
					                                   
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "%"+searchWord+"%");
					pstmt.setString(2, "%"+searchWord+"%");
					pstmt.setString(3, "%"+searchWord+"%");
					pstmt.setString(4, "%"+searchWord+"%");
					pstmt.setString(5, "%"+searchWord+"%");
					pstmt.setString(6, "%"+searchWord+"%");
					pstmt.setString(7, "%"+searchWord+"%");
					
					rs = pstmt.executeQuery();
				
					while(rs.next()) {
						MemberVO vo = new MemberVO();
						vo.setId(rs.getString(1));
						vo.setPw(rs.getString(2));
						vo.setName(rs.getString(3));
						vo.setTel(rs.getString(4));
						vo.setBirth(rs.getString(5));
						vo.setJoindate(rs.getString(6)); 
						
						lst.add(vo);						
					}
					
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					dbClose();
				} 
				return lst;
			}
			
			
			//아이디 중복확인
			public int userCheckId(String idCheck){
				int result = 0; 
				try {
					getConn();
					sql = "select id from memberinfo where id = ?";
					                                   
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
			
			
			//형변환
			public Date typeChange(String getDate) {
				java.util.Date utilDate = null;  
				java.sql.Date sqlDate = null;
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			 if(getDate.length()>9) {
				try { 	utilDate = transFormat.parse(getDate);	
						sqlDate = new java.sql.Date(utilDate.getTime());
			 		
				} catch (Exception e) { 
					e.printStackTrace();
				}  			
			}else {
				try { 
						String year = getDate.substring(0,4);
						String month = getDate.substring(4,6);
						String day = getDate.substring(6,8);
						String allDate = year+"-"+month+"-"+day;
						utilDate = transFormat.parse(allDate);	 
						sqlDate = new java.sql.Date(utilDate.getTime());
			 		
				} catch (Exception e) { 
					e.printStackTrace();
				} 
				
			}
				return sqlDate;	
			}
			
			
			//회원 신규 등록 
			public int memberInsert(MemberVO vo, String getBirth) {
				int result1=0; 
				Date join = null ;
			 	Date birth = null ;
			  
			 	if(!getBirth.equals("") || !vo.getBirth().equals("")) {
			 		birth = typeChange(vo.getBirth());
			  	}
			 
			 	if(vo.getJoindate()!=null) {
			 		join = typeChange(vo.getJoindate());
			 	}
				try {
					getConn();
					
					sql = "insert into memberinfo (id, pw, name, tel, birth, joindate)"
							+" values( ?, ?, ?, ?, ?, ?)";
					 
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, vo.getId());
					pstmt.setString(2, vo.getPw());
					pstmt.setString(3, vo.getName());
					pstmt.setString(4, vo.getTel());
					pstmt.setDate(5, birth);
					if(vo.getJoindate()!=null) {
						pstmt.setDate(6, join);
					
					}
					result1 = pstmt.executeUpdate();
						
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					dbClose();
				}return result1;
			}
		 	
		//회원정보 수정	                     
			public int memberUpdate(MemberVO vo) { 
			int result=0; 
			Date join = null ;
		 	Date birth = typeChange(vo.getBirth());
		 
		 	if(vo.getJoindate()!=null) {
		 		join = typeChange(vo.getJoindate());
		 	}
		 			
			try {
				getConn();
				sql = "update memberinfo set pw=?, name=?, tel=?, birth=?, joindate=? where id=?";
				pstmt = conn.prepareStatement(sql);	
				pstmt.setString(1, vo.getPw());
				pstmt.setString(2, vo.getName()); 	
				pstmt.setString(3, vo.getTel());
				pstmt.setDate(4, birth);
				if(vo.getJoindate()!=null) {
					pstmt.setDate(5, join);
				}
				
				pstmt.setString(6, vo.getId());
			 
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
				int result=0;
				try {
					getConn();
					sql = "delete from memberinfo where id=?";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, id);
					result = pstmt.executeUpdate();
					
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					dbClose();
				} return result;
			}
}
