package vo;

public class FoodVO {

	private String foodName;
	private Object foodCategory;
	private int foodPrice;
	private int foodStock; 
	
	public FoodVO() {
	}

	
	public FoodVO(String foodName, Object foodCategory, int foodPrice, int foodStock) {
		this.foodName = foodName;
		this.foodCategory = foodCategory;
		this.foodPrice = foodPrice;
		this.foodStock = foodStock; 
	}


	public String getFoodName() {
		return foodName;
	}


	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}


	public Object getFoodCategory() {
		return foodCategory;
	}


	public void setFoodCategory(Object foodCategory) {
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
	 

}
