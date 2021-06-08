package vo;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;


public class GameDAO extends DBConn{
	public GameDAO() {}
	//게임 전체 목록 가져오기 메서드
	public  List<GameVO>  getGameList() { 
		List<GameVO> lst= new ArrayList<GameVO>();  
		try {
			getConn();
			sql = "select gamename, gameprice, rentstock, sellstock, minpeople, maxpeople, playtime, gamepic"
					+" from Game order by gamename asc";
			pstmt = conn.prepareStatement(sql);
			
		 	rs = pstmt.executeQuery();
			while(rs.next()) { 
				GameVO vo = new GameVO(rs.getString(1), rs.getInt(2), rs.getInt(3),
						 rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getBlob(8));  
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
	// 레코드 검색
	public List<GameVO> getSearchRecord(String searchWord){
		List<GameVO> lst=new ArrayList<GameVO>();
		try {
			getConn();
			sql="select gamename, maxpeople, playtime, rentstock from Game where gamename like ?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, "%"+searchWord+"%");
			
			rs=pstmt.executeQuery();
			while(rs.next()) {
				GameVO vo=new GameVO();
				vo.setGamename(rs.getString(1));
				vo.setMaxpeople(rs.getInt(2));
				vo.setPlaytime(rs.getString(3));
				vo.setRentstock(rs.getInt(4));
				
				lst.add(vo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return lst;
	}

	//게임 등록 
	public int gameInsert(GameVO vo) {
		int result=0;
		try {
			getConn();
			sql = "insert into game (gamename, gameprice, rentstock, sellstock, minpeople, maxpeople, playtime) "
					+" values( ?, ?, ?, ?,?,?,?)";
			 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getGamename());
			pstmt.setInt(2, vo.getGameprice());
			pstmt.setInt(3, vo.getRentstock());
			pstmt.setInt(4, vo.getSellstock());
			pstmt.setInt(5, vo.getMinpeople());
			pstmt.setInt(6, vo.getMaxpeople());
			pstmt.setString(7, vo.getPlaytime()); 
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}return result;
	}
	//게임 수정	                     
	public int gameUpdate(GameVO vo) { 
		int result=0;
		try {
			getConn();
			sql = "update Game set gameprice=?, rentstock=?, sellstock=?, "
					+ " minpeople=?, maxpeople=?, playtime=? where gamename=?";
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, vo.getGameprice());
			pstmt.setInt(2, vo.getRentstock());
			pstmt.setInt(3, vo.getSellstock());
			pstmt.setInt(4, vo.getMinpeople());
			pstmt.setInt(5, vo.getMaxpeople());
			pstmt.setString(6, vo.getPlaytime());
			pstmt.setString(7, vo.getGamename());
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}		
		return result;	 
	}	
	//게임 삭제
	public int gameDelete(String game) {
		int result=0;
		try {
			getConn();
			sql = "delete from Game where gamename=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, game);
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		} return result;
	}
	//이미지 업로드
	public int imgUpload(String game, File SelFile) {
		int result=0;
		try {
		   	getConn();
		   	FileInputStream fis = new FileInputStream(SelFile);
			
		   	sql = "update Game set gamepic=? where gamename=?";
		    	
		   	pstmt = conn.prepareStatement(sql);
			pstmt.setBinaryStream(1, fis,(int)SelFile.length());
			pstmt.setString(2, game);
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		} return result;
	}
	//사진 삭제
	public int picDelete(String game) {
		int result=0;
	 	try {
			getConn();
			 	
			sql = "update Game set gamepic=? where gamename=?";
					    	
			pstmt = conn.prepareStatement(sql);
			pstmt.setBinaryStream(1, null);
			pstmt.setString(2, game);
						
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		} 
	 	return result;
	}			
	//DB에서 사진 가져오기
	public byte[] getpicDB(String game) {        
      byte[] imgBuffer = null;
      try {
         getConn();   
         sql = "select gamepic from Game where gamename=?";
         pstmt = conn.prepareStatement(sql);
          
          pstmt.setString(1, game);   
         rs = pstmt.executeQuery();   
          if(rs.next()) {  
             Blob blob = rs.getBlob("gamepic"); 
             if(blob!=null) {
             InputStream is = blob.getBinaryStream();
             imgBuffer = new byte[(int)blob.length()];
             is.read(imgBuffer, 0, (int)blob.length()); 
             is.close(); 
            
               conn.close();
          }}
        }catch(Exception e) {
         e.printStackTrace();    
       }finally {
         dbClose(); 
       } 
      return imgBuffer; 
      }
	// 구매하기 버튼 이벤트 발생 시
	public int SellUpdate(GameVO vo) { // 
		int result=0;
		try {
			getConn();
			
			sql="update game set sellstock=? where gamename=? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getSellstock());
			pstmt.setString(2, vo.getGamename());
			
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
	}
	// 지금 플레이 버튼 이벤트 발생 시
	public int RentUpdate(GameVO vo) {
		int result=0;
		try {
			getConn();
			
			sql="update game set rentstock=? where gamename=? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getRentstock());
			pstmt.setString(2, vo.getGamename());
			
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return result;
		
	}
	// 해당 레코드 출력
	public GameVO GameInfoSelect(String gamename){
		GameVO vo=new GameVO();
		try {
			getConn();
			sql="select gamename, rentstock, sellstock, minpeople, maxpeople, playtime, gameprice "
					+ "from Game where gamename = ?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, gamename);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setGamename(rs.getString(1));
				vo.setRentstock(rs.getInt(2));
				vo.setSellstock(rs.getInt(3));
				vo.setMinpeople(rs.getInt(4));
				vo.setMaxpeople(rs.getInt(5));
				vo.setPlaytime(rs.getString(6));
				vo.setGameprice(rs.getInt(7));
			}
					
		}catch(Exception e) {
			
		}finally {
			dbClose();
		}
		return vo;
	}
	
	// 레코드 전체선택
	public List<GameVO> GameAllSelect() {
		// 선택한 레코드를 보관할 컬렉션
		List<GameVO> lst=new ArrayList<GameVO>();
		
		try {
			getConn(); // db 연결
			sql="select gamename, maxpeople, playtime, rentstock "
					+ "from Game";
			pstmt=conn.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				// 레코드를 VO에 담고 VO를 List에 담는다
				GameVO vo=new GameVO(rs.getString(1), rs.getInt(2),rs.getString(3),rs.getInt(4)); // gamename, maxpeople, playtime, rentstock 
				lst.add(vo);
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbClose(); // db 종료
		}
		return lst;
	}

	
}	
					
					    
			 
			
			
			
			
			
	 
		
		
 
