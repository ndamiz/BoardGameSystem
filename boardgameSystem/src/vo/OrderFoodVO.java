package vo;
import javax.swing.ImageIcon;
public class OrderFoodVO {
	//food table
	private String foodName;
	private String foodCategory;
	private int foodPrice;
	private int foodStock;
	private ImageIcon foodpic;
	
	
	//ordernum table
	private int ordernum;
	private int roomrenum;
	private String ordertime;
	
	
	//orderdetail table
	private int ordercount;
	private String orderdate;
	
	public OrderFoodVO() {
		
	}

	
	
	public String getFoodName() {
		return foodName;
	}



	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}



	public String getFoodCategory() {
		return foodCategory;
	}



	public void setFoodCategory(String foodCategory) {
		this.foodCategory = foodCategory;
	}



	public int getFoodPrice() {
		return foodPrice;
	}



	public void setFoodPrice(int foodPrice) {
		this.foodPrice = foodPrice;
	}



	public int getFoodStock() {
		return foodStock;
	}



	public void setFoodStock(int foodStock) {
		this.foodStock = foodStock;
	}



	public ImageIcon getFoodpic() {
		return foodpic;
	}



	public void setFoodpic(ImageIcon foodpic) {
		this.foodpic = foodpic;
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



	public String getOrdertime() {
		return ordertime;
	}



	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
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
