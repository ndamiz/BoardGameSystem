package vo;

public class OrderDetailVO {
	private int ordernum;
	private int roomrenum;
	private String gamename;
	private String foodname;
	private int ordercount;
	private String orderdate; 
	private int orderprice;
	private int gameprice;
	private int foodprice;
	private String id;
	private int roomnum;
	public OrderDetailVO() {
		
	}
	public OrderDetailVO(int ordernum,int roomrenum, String gamename,String foodname, int ordercount , String orderdate) {
		this.ordernum=ordernum; 	this.roomrenum=roomrenum;	this.gamename=gamename;
		this.foodname=foodname;		this.ordercount=ordercount;	this.orderdate=orderdate;
	}
	//게임 구매주문사항 확인					// 음식들어가면 음식이다.  
	public OrderDetailVO(int roomrenum, String gamename, int ordercount,int gameprice, int orderprice) {
		this.roomrenum=roomrenum;	this.gamename=gamename; this.ordercount=ordercount;
		this.gameprice=gameprice;		this.orderprice=orderprice;	
	}
	public OrderDetailVO(int ordernum,int roomrenum, String id,int roomnum ) {
		this.ordernum=ordernum;	this.roomrenum=roomrenum;	this.id=id;	this.roomnum=roomnum;
	}
	
	public int getRoomnum() {
		return roomnum;
	}
	public void setRoomnum(int roomnum) {
		this.roomnum = roomnum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getOrderprice() {
		return orderprice;
	}
	public void setOrderprice(int orderprice) {
		this.orderprice = orderprice;
	}
	public int getGameprice() {
		return gameprice;
	}
	public void setGameprice(int gameprice) {
		this.gameprice = gameprice;
	}
	public int getFoodprice() {
		return foodprice;
	}
	public void setFoodprice(int foodprice) {
		this.foodprice = foodprice;
	}
	public int getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(int ordernum) {
		this.ordernum = ordernum;
	}
	public int getRoomrenum() {
		return roomrenum;
	}
	public void setRoomrenum(int roomrenum) {
		this.roomrenum = roomrenum;
	}
	public String getGamename() {
		return gamename;
	}
	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
	public String getFoodname() {
		return foodname;
	}
	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}
	public int getOrdercount() {
		return ordercount;
	}
	public void setOrdercount(int ordercount) {
		this.ordercount = ordercount;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

}
