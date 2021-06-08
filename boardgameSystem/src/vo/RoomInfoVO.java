package vo;

public class RoomInfoVO {
	private int roomNum;
	private int roomPeople;
	private int roomPrice;
	public RoomInfoVO() {
	}
	public RoomInfoVO(int roomNum, int roomPeople, int roomPrice) {
		this.roomNum = roomNum;
		this.roomPeople = roomPeople;
		this.roomPrice = roomPrice;
	}
	public int getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	public int getRoomPeople() {
		return roomPeople;
	}
	public void setRoomPeople(int roomPeople) {
		this.roomPeople = roomPeople;
	}
	public int getRoomPrice() {
		return roomPrice;
	}
	public void setRoomPrice(int roomPrice) {
		this.roomPrice = roomPrice;
	}

}
