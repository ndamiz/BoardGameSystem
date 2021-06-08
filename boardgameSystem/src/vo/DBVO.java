package vo;

public class DBVO {

    private String gamename;
    private int gameprice;
    private int ordercount;
    private int total;
    
    private int gameb;
    private int gamer;
	
    private String date;
    private int food1;
    private int food2;
    private int food3;
    private int food4;
   
  //내가 추가한거
    private String orderdate;
    private String foodname;
    private String redate;
    private int foodprice;
    private int foodtotal;
    public String getRedate() {
		return redate;
	}



	public void setRedate(String redate) {
		this.redate = redate;
	}



	public int getRoomtotal() {
		return roomtotal;
	}



	public void setRoomtotal(int roomtotal) {
		this.roomtotal = roomtotal;
	}



	private int gametotal;
    private int roomtotal;

//
    private int startYear;
    public int getStartYear() {
		return startYear;
	}



	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}



	public int getEndYear() {
		return endYear;
	}



	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}



	private int endYear;
    
    
  
 

	public DBVO() {
		
	}
	
	
	
     public DBVO(String gamename, int gameprice, int ordercount, int total, String date) {
		this.gamename=gamename;
		this.gameprice=gameprice;
		this.ordercount=ordercount;
		this.total=total;
		this.date=date;
		

		
	}
     
     public DBVO(String gamename, int gameprice, int ordercount, int total) {
 		this.gamename=gamename;
 		this.gameprice=gameprice;
 		this.ordercount=ordercount;
 		this.total=total;
 		
 		

 		
 	}
      
   


	public DBVO(String gamename, int gamer, int gameb) {
 		this.gamename=gamename;
 		this.gamer=gamer;
 		this.gameb=gameb;

 		
 	}
     
     public DBVO(String date, int food1, int food2, int food3 ,int food4, int total) {
  		this.date=date;
  		this.food1=food1;
  		this.food2=food2;
  		this.food3=food3;
  		this.food4=food4;
        this.total=total;
  		
  	}
  
     
     public DBVO(int startYear, int endYear) {
    	 this.startYear=startYear;
    	 this.endYear=endYear;		 
     }




	public int getTotal() {
		return total;
	}



	public void setTotal(int total) {
		this.total = total;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public int getFood1() {
		return food1;
	}



	public void setFood1(int food1) {
		this.food1 = food1;
	}



	public int getFood2() {
		return food2;
	}



	public void setFood2(int food2) {
		this.food2 = food2;
	}



	public int getFood3() {
		return food3;
	}



	public void setFood3(int food3) {
		this.food3 = food3;
	}



	public int getFood4() {
		return food4;
	}



	public void setFood4(int food4) {
		this.food4 = food4;
	}



	public String getGamename() {
		return gamename;
	}

	public void setGamename(String gamename) {
		this.gamename = gamename;
	}

	public int getGameprice() {
		return gameprice;
	}

	public void setGameprice(int gameprice) {
		this.gameprice = gameprice;
	}

	public int getOrdercount() {
		return ordercount;
	}

	public void setOrdercount(int ordercount) {
		this.ordercount = ordercount;
	}
	





	public int getGameb() {
		return gameb;
	}



	public void setGameb(int gameb) {
		this.gameb = gameb;
	}



	public int getGamer() {
		return gamer;
	}



	public void setGamer(int gamer) {
		this.gamer = gamer;
	}



	public String getOrderdate() {
		return orderdate;
	}



	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}



	public String getFoodname() {
		return foodname;
	}



	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}



	public int getFoodprice() {
		return foodprice;
	}



	public void setFoodprice(int foodprice) {
		this.foodprice = foodprice;
	}



	public int getFoodtotal() {
		return foodtotal;
	}



	public void setFoodtotal(int foodtotal) {
		this.foodtotal = foodtotal;
	}



	public int getGametotal() {
		return gametotal;
	}



	public void setGametotal(int gametotal) {
		this.gametotal = gametotal;
	}




	







     
     

}
