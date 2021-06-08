package vo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class FoodDAO extends DBConn{

	//음식 전체 목록 가져오기  
			public  List<FoodVO>  getFoodList() { 
				List<FoodVO> lst= new ArrayList<FoodVO>();  
				
				try {
					getConn();
					sql = "select foodname, foodcategory, foodprice, foodstock"
							+" from Food order by foodcategory asc";
					pstmt = conn.prepareStatement(sql);
					
				 	rs = pstmt.executeQuery();
					while(rs.next()) { 
						FoodVO vo = new FoodVO(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4));  
						lst.add(vo);
						
					} 
					
				}
				catch(Exception e) {
				e.printStackTrace();	
				}finally {}
				
				return lst;
				
			}
			 
		//음식 검색
			public List<FoodVO> getSearchRecord(String searchWord){
				List<FoodVO> lst = new ArrayList<FoodVO>();
				try {
					getConn();
					sql = "select foodname, foodcategory, foodprice, foodstock"
							+ " from Food where foodname like? order by foodname asc";
					                                   
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "%"+searchWord+"%");
					
					rs = pstmt.executeQuery();
				
					while(rs.next()) {
						FoodVO vo = new FoodVO();
						vo.setFoodName(rs.getString(1));
						vo.setFoodCategory(rs.getString(2));
						vo.setFoodPrice(rs.getInt(3));
						vo.setFoodStock(rs.getInt(4)); 
						lst.add(vo);
						
					}
					
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					dbClose();
				} 
				return lst;
			}
			
			//음식 등록  
			public int foodInsert(FoodVO vo) {
				int result=0;
				try {
					getConn();
					
					sql = "insert into Food(foodname, foodcategory, foodprice, foodstock)"
							+" values(?, ?, ?, ?)";
					 
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, vo.getFoodName());
					pstmt.setObject(2, vo.getFoodCategory());
					pstmt.setInt(3, vo.getFoodPrice());
					pstmt.setInt(4, vo.getFoodStock()); 
					
					result = pstmt.executeUpdate();
					
					
				}catch(Exception e) {
					e.printStackTrace();
					
					
				}finally {
					dbClose();
				}return result;
			}
		 	
			                     
			public int foodUpdate(FoodVO vo) { 
			
			int result=0;
			try {
				getConn();
				sql = "update Food set foodcategory=?,  foodprice=?, foodstock=?  where foodname=?";
				pstmt = conn.prepareStatement(sql);		
				pstmt.setObject(1, vo.getFoodCategory());
				pstmt.setInt(2, vo.getFoodPrice());
				pstmt.setInt(3, vo.getFoodStock());
				pstmt.setString(4, vo.getFoodName());
				
				result = pstmt.executeUpdate();
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				dbClose();
			}		
			return result;	
			}
			
			//음식 삭제
			public int foodDelete(String food) {
				int result=0;
				try {
					getConn();
					sql = "delete from Food where foodname=?";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, food);
					result = pstmt.executeUpdate();
					
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					dbClose();
				} return result;
			}
			
			
			//이미지 업로드
			public int imgUpload(String food, File SelFile) {
				int result=0;
				try {
					 getConn();
					 FileInputStream fis = new FileInputStream(SelFile);
						
					 sql = "update Food set foodpic=? where foodname=?";
					    	
					 pstmt = conn.prepareStatement(sql);
					 pstmt.setBinaryStream(1, fis,(int)SelFile.length());
					 pstmt.setString(2, food);
						
					 result = pstmt.executeUpdate();
					
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					dbClose();
				
				} return result;
			}
				
							
							
							
			//사진 삭제
			public int picDelete(String food) {
				int result=0;
				try {
				   	getConn();
								 	
					sql = "update Food set foodpic=? where foodname=?";
								    	
					pstmt = conn.prepareStatement(sql);
					pstmt.setBinaryStream(1, null);
					pstmt.setString(2, food);
									
					result = pstmt.executeUpdate();
								
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					dbClose();
				} return result;
			}
						
			//DB에서 사진 가져오기
			public byte[] getpicDB(String food) {
				BufferedImage bi = null ;
				Object object = null;
				ImageIcon getImage = null;
				String filePath = "C://Users/Public/Desktop/Photo.jpg";
				byte[] imgBuffer = null;
				try {
					getConn();	
					sql = "select foodpic from Food where foodname=?";
					pstmt = conn.prepareStatement(sql);
					 
				 	pstmt.setString(1, food);   
					rs = pstmt.executeQuery();   
				 	if(rs.next()) {  
				 		Blob blob = rs.getBlob("foodpic"); 
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
			
			
			
			
			
			
			
			
			
			 
}
