package vo;

public class CallVO {
	private int roomrenum;
	private String calldate;
	private String callcheck;
	private String id;
	private int roomnum;
	
	
	public CallVO(int roomrenum,String calldate, String callcheck ) {
		this.roomrenum=roomrenum;  this.calldate=calldate; this.callcheck=callcheck;
	}
	public CallVO() {
		
	}
	public CallVO(int roomrenum, String id, int roomnum) {
		this.roomrenum=roomrenum;
		this.id=id;
		this.roomnum=roomnum;
	}

	public int getRoomrenum() {
		return roomrenum;
	}

	public void setRoomrenum(int roomrenum) {
		this.roomrenum = roomrenum;
	}

	public String getCalldate() {
		return calldate;
	}

	public void setCalldate(String calldate) {
		this.calldate = calldate;
	}

	public String getCallcheck() {
		return callcheck;
	}

	public void setCallcheck(String callcheck) {
		this.callcheck = callcheck;
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

}
