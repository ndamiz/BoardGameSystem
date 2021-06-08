package vo;

public class HistoryVO {
	//룸예약정보테이블
	private int romomrenum;
	private String id;
	private int roomnum;
	private String redate;
	private String intime;
	private String outtime;
	private int pay;
	private String paycheck;
	//ordergametable
	private String gamename;

	public HistoryVO() {
		
	}
	public int getRomomrenum() {
		return romomrenum;
	}

	public void setRomomrenum(int romomrenum) {
		this.romomrenum = romomrenum;
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

	public String getRedate() {
		return redate;
	}

	public void setRedate(String redate) {
		this.redate = redate;
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



	public String getGamename() {
		return gamename;
	}



	public void setGamename(String gamename) {
		this.gamename = gamename;
	}

	
}
