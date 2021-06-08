package vo;

public class RoomCancelVO {
	private int roomrenum;
	private String id;
	private int roomnum;
	private String inday;
	private String intime;
	private String outtime;
	private int pay;
	private String paycheck;
	
	public RoomCancelVO() {
		
	}
	
	public RoomCancelVO(int roomrenum, String id, int roomnum, String inday, String intime, String outtime, int pay, String paycheck) {
		this.roomrenum=roomrenum;
		this.id=id;
		this.roomnum=roomnum;
		this.inday=inday;
		this.intime=intime;
		this.outtime=outtime;
		this.pay=pay;
		this.paycheck=paycheck;
	}

	public int getRoomrenum() {
		return roomrenum;
	}

	public void setRoomrenum(int roomrenum) {
		this.roomrenum = roomrenum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRoomnum() {
		return roomnum;
	}

	public void setRoomnum(int roomnum) {
		this.roomnum = roomnum;
	}

	public String getInday() {
		return inday;
	}

	public void setInday(String inday) {
		this.inday = inday;
	}

	public String getIntime() {
		return intime;
	}

	public void setIntime(String intime) {
		this.intime = intime;
	}

	public String getOuttime() {
		return outtime;
	}

	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}

	public int getPay() {
		return pay;
	}

	public void setPay(int pay) {
		this.pay = pay;
	}

	public String getPaycheck() {
		return paycheck;
	}

	public void setPaycheck(String paycheck) {
		this.paycheck = paycheck;
	}

	
}
