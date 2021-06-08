package vo;

public class MemberInfoVO {
	private String id;
	private String pw;
	private String name;
	private String tel;
	private String birth;
	private String joindate;
	
	public MemberInfoVO() {
	}
	public MemberInfoVO(String id, String pw, String name, String tel, String birth, String joindate) {
		this.id = id;	this.pw = pw;	this.name = name;	this.tel = tel;
		this.birth = birth;		this.joindate = joindate;
	}
	public MemberInfoVO(String id, String pw, String name, String tel, String birth) {
		this.id = id;	this.pw = pw;	this.name = name;	this.tel = tel;
		this.birth = birth;
	}
	public MemberInfoVO(String id, String name, String tel, String birth) {
		this.id = id; this.name = name;	this.tel = tel;	this.birth = birth;
	}
	public MemberInfoVO(String name, String tel, String birth) {
		this.name = name;	this.tel = tel;	this.birth = birth;
	}
	public MemberInfoVO(String tel, String pw) {
		this.pw = pw;
		this.tel = tel;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getJoindate() {
		return joindate;
	}

	public void setJoindate(String joindate) {
		this.joindate = joindate;
	}

}
