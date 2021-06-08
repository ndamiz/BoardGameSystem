package vo;

public class ReservationVO {
	private int roomReNum;
	private String id;
	private int roomNum;
	private String reDate;
	private String inTime;
	private String outTime;
	private int pay;
	private String payCheck;
	public ReservationVO() {
		
	}
	public ReservationVO(int roomReNum, String id, int roomNum, String reDate, String inTime, String outTime, int pay, String payCheck) {
	this.roomReNum = roomReNum;		this.id = id;	this.roomNum = roomNum;
	this.reDate = reDate; 		this.inTime = inTime;	this.outTime = outTime;
	this.pay = pay;		this.payCheck = payCheck;
	}
	public ReservationVO(String reDate, String inTime) {
		this.reDate = reDate; 		this.inTime = inTime;
	}
	public ReservationVO(String id, int roomNum, String reDate, String inTime, String outTime, int pay) {
		this.id = id;	this.roomNum = roomNum;
		this.reDate = reDate; 		this.inTime = inTime;	this.outTime = outTime;
		this.pay = pay;	
	}
	public int getRoomReNum() {
		return roomReNum;
	}
	public void setRoomReNum(int roomReNum) {
		this.roomReNum = roomReNum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	public String getReDate() {
		return reDate;
	}
	public void setReDate(String reDate) {
		this.reDate = reDate;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public int getPay() {
		return pay;
	}
	public void setPay(int pay) {
		this.pay = pay;
	}
	public String getPayCheck() {
		return payCheck;
	}
	public void setPayCheck(String payCheck) {
		this.payCheck = payCheck;
	}

}
