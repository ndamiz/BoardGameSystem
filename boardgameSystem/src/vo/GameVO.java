package vo;

import java.sql.Blob;

public class GameVO {
	private String gamename;
	private int gameprice;
	private int rentstock;
	private int sellstock;
	private int minpeople;
	private int maxpeople;
	private String playtime; 
	private Blob  gamepic;
	
	public GameVO() {
	}

	public GameVO(String gamename, int gamePrice, int rentStock, int sellStock, int minpeople,int maxpeople,String playTime) {
		this.gamename = gamename;
		this.gameprice = gamePrice;
		this.rentstock = rentStock;
		this.sellstock = sellStock;
		this.minpeople = minpeople;
		this.maxpeople = maxpeople;
		this.playtime = playTime; 
	}
	
	public GameVO(String gamename, int gamePrice, int rentStock, int sellStock, int minpeople,int maxpeople,String playTime, Blob gamepic) {
		this(gamename, gamePrice, rentStock, sellStock, minpeople,maxpeople, playTime);  
	 	 this.gamepic = gamepic; 
	} 
	public GameVO(String gamename, int maxpeople, String playtime, int rentstock) {
		this.gamename=gamename;
		this.maxpeople=maxpeople;
		this.playtime=playtime;
		this.rentstock=rentstock;
	}
	public GameVO(String gamename, int minpeople, int maxpeople, String playtime, int gameprice) {
		this.gamename=gamename;
		this.minpeople=minpeople;
		this.maxpeople=maxpeople;
		this.playtime=playtime;
		this.gameprice=gameprice;
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

	public int getRentstock() {
		return rentstock;
	}

	public void setRentstock(int rentstock) {
		this.rentstock = rentstock;
	}

	public int getSellstock() {
		return sellstock;
	}

	public void setSellstock(int sellstock) {
		this.sellstock = sellstock;
	}

	public int getMinpeople() {
		return minpeople;
	}

	public void setMinpeople(int minpeople) {
		this.minpeople = minpeople;
	}

	public int getMaxpeople() {
		return maxpeople;
	}

	public void setMaxpeople(int maxpeople) {
		this.maxpeople = maxpeople;
	}

	public String getPlaytime() {
		return playtime;
	}

	public void setPlaytime(String playtime) {
		this.playtime = playtime;
	}

	public Blob getGamepic() {
		return gamepic;
	}

	public void setGamepic(Blob gamepic) {
		this.gamepic = gamepic;
	}
}
