package vo;

public class OrderGameVO {
	private int ordernum;
	private int roomrenum;
	private String ordertime;
	private String gamename;
	private String gamerenttime;
	private String gameretime;
	private String returntime;
	private String gamecheck;
	private String foodname;
	private int ordercount;
	private String orderdate; 
	
	public OrderGameVO() {
		
	}
	
	public OrderGameVO(int ordernum, int roomrenum, String gamename, String gamerenttime, String gameretime, String returntime, String gamecheck) {
		this.ordernum=ordernum;
		this.roomrenum=roomrenum;
		this.gamename=gamename;
		this.gamerenttime=gamerenttime;
		this.gameretime=gamerenttime;
		this.returntime=returntime;
		this.gamecheck=gamecheck;
	}
	
	public OrderGameVO(int ordernum, String gamename, String gamerenttime, String gameretime, String returntime, String gamecheck) {
		this.ordernum=ordernum;
		this.gamename=gamename;
		this.gamerenttime=gamerenttime;
		this.gameretime=gamerenttime;
		this.returntime=returntime;
		this.gamecheck=gamecheck;
	}
	
	public int getRoomrenum() {
		return roomrenum;
	}

	public void setRoomrenum(int roomrenum) {
		this.roomrenum = roomrenum;
	}

	public String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public int getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(int ordernum) {
		this.ordernum = ordernum;
	}

	public String getGamename() {
		return gamename;
	}

	public void setGamename(String gamename) {
		this.gamename = gamename;
	}

	public String getGamerenttime() {
		return gamerenttime;
	}

	public void setGamerenttime(String gamerenttime) {
		this.gamerenttime = gamerenttime;
	}

	public String getGameretime() {
		return gameretime;
	}

	public void setGameretime(String gameretime) {
		this.gameretime = gameretime;
	}

	public String getReturntime() {
		return returntime;
	}

	public void setReturntime(String returntime) {
		this.returntime = returntime;
	}

	public String getGamecheck() {
		return gamecheck;
	}

	public void setGamecheck(String gamecheck) {
		this.gamecheck = gamecheck;
	}
	
}
