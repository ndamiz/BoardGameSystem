package vo;
import java.util.ArrayList;
import java.util.List;

public class DBDAO extends DBConn {

	







	public DBDAO() {
		
	}
	
	
	
	
	
    //레코드 전체 선택//////////////보드게임 판매///////////////////////////////////////////////////// 123
	public List<DBVO> DBAllSelect(String start,String end) {
		//선택한 레코드를 담을 수 있는 컬렉션 
		List<DBVO> lst = new ArrayList<DBVO>();
	
		
		try {
			getConn();
			sql= " select g.gamename, gameprice, ordercount, (gameprice*ordercount)e from game g join orderdetail o on g.gamename=o.gamename " 
					 + " where to_char(orderdate, 'yyyy-mm-dd') between to_date(?, 'yyyy-mm-dd')and to_date(?, 'yyyy-mm-dd') order by e desc " ;
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, start);
			pstmt.setString(2, end);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				//레코드vo에 담고 vo를 리스트에 담고
				DBVO vo=new DBVO(rs.getString(1),
						rs.getInt(2)
						,rs.getInt(3)
						,rs.getInt(4)
					
						
						);
				
			    
				
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

	
	//레코드 전체 선택
		public int[] DBAllSelect2() {
			//선택한 레코드를 담을 수 있는 컬렉션 
			int lst2[] = new int[2];
			
			
			try {
				getConn();
				sql= " select sum(ordercount),sum(gameprice*ordercount) " + 
				" from game g join orderdetail o on g.gamename=o.gamename where o.gamename is not null " ;
				pstmt = conn.prepareStatement(sql);
				
				rs=pstmt.executeQuery();
				
				while(rs.next()) {
					lst2[0]=rs.getInt(1);
					lst2[1]=rs.getInt(2);
					
					
					
				}
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}finally {
				dbClose();
			}
			return lst2;
			
		}
	
		
		
		
		
		
		
		///////////////////////////////////////////////////////////////////////////////음식 아이템//////////////////456
		
		
		
		
		
		 //레코드 전체 선택
		public List<DBVO> DBAllSelect4(String start, String end) {
			//선택한 레코드를 담을 수 있는 컬렉션 
			List<DBVO> lst4 = new ArrayList<DBVO>();
			
			try {
				getConn();
				sql= " select f.foodname, foodprice, ordercount, (foodprice*ordercount)e, o.orderdate from food f join orderdetail o on f.foodname=o.foodname "
						+ " where to_char(orderdate, 'yyyy-mm-dd') between to_date(?, 'yyyy-mm-dd')and to_date(?, 'yyyy-mm-dd') order by e desc " ;
				pstmt = conn.prepareStatement(sql);
				
				
				pstmt.setString(1, start);
				pstmt.setString(2, end);
				
				rs=pstmt.executeQuery();
				
				while(rs.next()) {
					//레코드vo에 담고 vo를 리스트에 담고
					DBVO vo4=new DBVO(rs.getString(1),
							rs.getInt(2)
							,rs.getInt(3)
							,rs.getInt(4)
							,rs.getString(5));
					
					lst4.add(vo4);
					}
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}finally {
				dbClose();
			}
			return lst4;
			
		}
		
		
		//레코드 전체 선택
			public int[] DBAllSelect5() {
				//선택한 레코드를 담을 수 있는 컬렉션 
				int lst5[] = new int[2];
				
				
				try {
					getConn();
					sql= " select sum(ordercount),sum(foodprice*ordercount) " + 
					" from food f join orderdetail o on f.foodname=o.foodname where o.foodname is not null " ;
					pstmt = conn.prepareStatement(sql);
					
					rs=pstmt.executeQuery();
					
					while(rs.next()) {
						lst5[0]=rs.getInt(1);
						lst5[1]=rs.getInt(2);
						
						
						
					}
					
				}
				catch(Exception e) {
					e.printStackTrace();
				}finally {
					dbClose();
				}
				return lst5;
				
			}
		
			
		/////////////////////////보드게임 대여//////////////////////////////////////////789
			
			public List<DBVO> DBAllSelect7(String start, String end) {
	               //선택한 레코드를 담을 수 있는 컬렉션 
	               List<DBVO> lst7 = new ArrayList<DBVO>();
	               
	               try {
	            	   getConn();
	                  sql= "select g.gamename,(select count(o2.gamename) from ordergame o2 where o2.gamecheck='예약') e, (count(o.gamecheck)) c "
	                        + "from ordergame o join game g on g.gamename=o.gamename "
	                        + "where (gamecheck='반납' or gamecheck='대여') and to_char(gamerenttime,'yyyy-mm-dd') between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd') "
	                        + "group by g.gamename order by c desc" ;
	                  pstmt = conn.prepareStatement(sql);
	                  
	                  pstmt.setString(1, start);
	                  pstmt.setString(2, end);
	                  rs=pstmt.executeQuery();
	                  while(rs.next()) {
	                     //레코드vo에 담고 vo를 리스트에 담고
	                     DBVO vo7=new DBVO(rs.getString(1),
	                           rs.getInt(2)
	                           ,rs.getInt(3)
	                           );
	                     
	                     lst7.add(vo7);
	                     }
	                  
	               }
	               catch(Exception e) {
	                  e.printStackTrace();
	               }finally {
	                  dbClose();
	               }
	               return lst7;
	               
	            }
	
	////////////////////////////음식 카테고리/////////////////////10 11 12

			 //레코드 전체 선택
			public List<DBVO> DBAllSelect10(String start, String end) {
				//선택한 레코드를 담을 수 있는 컬렉션 
				
				
				List<DBVO> lst10 = new ArrayList<DBVO>();
				
				try {
					getConn();
					sql= " select b.orderdate,b.result, c.result, d.result, e.result,(nvl(b.result,0)+nvl(c.result,0)+nvl(d.result,0)+nvl(e.result,0)) total from " +
							" (select a.orderdate, sum(a.ordercount*a.foodprice) result from (select to_char(o.orderdate, 'YYYY/MM/DD') orderdate, foodcategory, f.foodname, ordercount, foodprice from food f join orderdetail o on f.foodname=o.foodname where f.foodcategory='분식') a group by a.orderdate ) b full outer join (select a.orderdate, sum(a.ordercount*a.foodprice) result from (select to_char(o.orderdate, 'YYYY/MM/DD') orderdate, foodcategory, f.foodname, ordercount, foodprice from food f join orderdetail o on f.foodname=o.foodname where f.foodcategory='식사') a group by a.orderdate) c on b.orderdate=c.orderdate full outer join (select a.orderdate, sum(a.ordercount*a.foodprice) result from (select to_char(o.orderdate, 'YYYY/MM/DD') orderdate, foodcategory, f.foodname, ordercount, foodprice from food f join orderdetail o on f.foodname=o.foodname where f.foodcategory='스낵') a group by a.orderdate) d on b.orderdate=d.orderdate full outer join (select a.orderdate, sum(a.ordercount*a.foodprice) result from (select to_char(o.orderdate, 'YYYY/MM/DD') orderdate, foodcategory, f.foodname, ordercount, foodprice from food f left outer join orderdetail o on f.foodname=o.foodname where f.foodcategory='음료') a group by a.orderdate) e on b.orderdate=e.orderdate " ;               
							
					pstmt = conn.prepareStatement(sql);
					//pstmt.setString(1, start);
					//pstmt.setString(2, end);
					rs=pstmt.executeQuery();
							
				
					while(rs.next()) {
								//레코드vo에 담고 vo를 리스트에 담고
								DBVO vo10=new DBVO(rs.getString(1),
										rs.getInt(2)
										,rs.getInt(3)
										,rs.getInt(4)
										,rs.getInt(5)
										,rs.getInt(6));
								
								lst10.add(vo10);
								
							
					}
							
				}
				catch(Exception e) {
							e.printStackTrace();
				}finally {
							dbClose();
						}
					return lst10;
						
			}
			
/////////////////////////////////////전체통계////////////////
		
		    public List<DBVO> getRoomTotal(String startDay, String endDay){
	            List<DBVO> lst = new ArrayList<DBVO>();
	               
	            try {
	            	getConn();
	               
	               sql = "select sum(roomprice) , to_date(redate, 'yyyy-mm-dd') " +
	                     " from roominfo r join reservation re on r.roomnum=re.roomnum " +
	                     " where to_char(redate, 'yyyy-mm-dd') <= ? or to_char(redate, 'yyyy-mm-dd') >= ? " +
	                     " group by to_date(redate, 'yyyy-mm-dd')";
	               
	               pstmt = conn.prepareStatement(sql);
	               pstmt.setString(1, startDay);
	               pstmt.setString(2, endDay);
	               
	               rs = pstmt.executeQuery();
	               while(rs.next()) {
	                  DBVO vo = new DBVO();
	                  vo.setRoomtotal(rs.getInt(1));
	                  vo.setRedate(rs.getString(2));
	                  lst.add(vo);
	               }
	               
	               
	            }catch(Exception e) {
	               e.printStackTrace();
	            }
	            return lst;
	         }
		    
		    
	         public List<DBVO> getGameTotal(String startDay, String endDay){
	            List<DBVO> lst = new ArrayList<DBVO>();
	               
	            try {
	            	getConn();
	               
	               sql = "select sum(gameprice * ordercount) gametotal, to_date(orderdate, 'yyyy-mm-dd') " +
	                     " from game g join orderdetail o on g.gamename=o.gamename " +
	                     " where to_char(orderdate, 'yyyy-mm-dd') <= ? or to_char(orderdate, 'yyyy-mm-dd') >= ? " +
	                     " group by to_date(orderdate, 'yyyy-mm-dd')";
	               
	               pstmt = conn.prepareStatement(sql);
	               pstmt.setString(1, startDay);
	               pstmt.setString(2, endDay);
	               
	               rs = pstmt.executeQuery();
	               while(rs.next()) {
	                  DBVO vo = new DBVO();
	                  vo.setGametotal(rs.getInt(1));
	                  vo.setOrderdate(rs.getString(2));
	                  lst.add(vo);
	               }
	               
	               
	            }catch(Exception e) {
	               e.printStackTrace();
	            }
	            return lst;
	         }
	         
	         public List<DBVO> getFoodTotal(String startDay, String endDay){
	            List<DBVO> lst = new ArrayList<DBVO>();
	               
	            try {
	               sql = "select sum(foodprice * ordercount) foodtotal, to_date(orderdate, 'yyyy-mm-dd') " +
	                     " from food f join orderdetail o on f.foodname=o.foodname " +
	                     " where to_char(orderdate, 'yyyy-mm-dd') <= ? or to_char(orderdate, 'yyyy-mm-dd') >= ? " +
	                     " group by to_date(orderdate, 'yyyy-mm-dd')";
	               
	               pstmt = conn.prepareStatement(sql);
	               pstmt.setString(1, startDay);
	               pstmt.setString(2, endDay);
	               
	               rs = pstmt.executeQuery();
	               while(rs.next()) {
	                  DBVO vo = new DBVO();
	                  vo.setFoodtotal(rs.getInt(1));
	                  vo.setOrderdate(rs.getString(2));
	                  lst.add(vo);
	               }
	               
	            }catch(Exception e) {
	               e.printStackTrace();
	            }
	            
	            
	            
	            return lst;
	         }


	
}
