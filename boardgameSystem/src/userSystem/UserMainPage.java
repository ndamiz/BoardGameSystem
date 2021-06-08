package userSystem;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import vo.CallDAO;
import vo.GameDAO;
import vo.OrderDetailDAO;
import vo.OrderDetailVO;
import vo.OrderGameDAO;
import vo.ReservationDAO;
import vo.ReservationVO;

public class UserMainPage extends JPanel implements ActionListener{
	Test test;
	JPanel askPane = new JPanel();
	JButton backBtn = new JButton("⇦ 뒤로");
	JButton askBtn = new JButton("관리자 호출");
	
	JPanel presentPane = new JPanel();
	JLabel presentLbl = new JLabel();
		JScrollPane presentSp = new JScrollPane(presentLbl);
	
	JPanel btnPane = new JPanel();
		JButton roomBtn = new JButton();
		JButton boardgameBtn = new JButton("보드게임");
		JButton foodBtn = new JButton("음식주문");
		JButton personDataBtn = new JButton("개인정보");
	Calendar date;
	String id ="";
	String checkDate[] = new String[3]; //0, 1, 2
	String reserveDate="";
	String btnCheck = "1";
	public UserMainPage() {}
	public UserMainPage(Test test, String userId) {
		this.test = test;
		id = userId;
		userMainPageFrame();
		usingState(id);
		
		personDataBtn.addActionListener(this);
		roomBtn.addActionListener(this);
		backBtn.addActionListener(this);
		askBtn.addActionListener(this);
		boardgameBtn.addActionListener(this);
		foodBtn.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		String eveStr = ae.getActionCommand();
		if(obj == personDataBtn) {
			test.change("personInfo", id, btnCheck);
		}else if(obj == roomBtn) {
			//조건넣을 것. 해당 아이디에 오늘날짜 이후로 예약되어있는게 없는지.  
			//현재는 룸예약을 넣어놀것
			if(eveStr.equals("룸예약")) {
				test.change("reserve", id);
			}else if(eveStr.equals("룸예약확인")){
				test.change("cancel", id);
			}else if(eveStr.equals("룸이용내역")){
				test.change("history", id);
			}
		}else if(foodBtn == obj) {
			test.change("orderFood", id);
		}else if(backBtn == obj) {
			test.change("login");
		}else if(askBtn == obj) {
			adminCall();
		}else if(boardgameBtn == obj) {
			test.change("gameList", id, btnCheck);
		}
	}
	public void usingState(String id) {
		date = Calendar.getInstance();
		int lastDay = date.getActualMaximum(Calendar.DATE);
		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH)+1;
		int day = date.get(Calendar.DATE);
		
		for (int i = 0; i < 3; i++) {
			checkDate[i] = (year +"-"+ month +"-"+ day);
			if(month == 12 && lastDay < day) {
				year ++;	month=1;
			}else if(lastDay < day) {
				day =1;		month ++;
			}	
			day++;
		}
		ReservationDAO dao = new ReservationDAO();
		List<ReservationVO> lst = new ArrayList<ReservationVO>();
		DecimalFormat dFmt = new DecimalFormat("#,###원");
		ReservationVO vo;
		String minusOutTime="";
		int enterDate = 0, enterTime =0;
		for (int i = 0; i < checkDate.length; i++) {
			lst = dao.usingStateSet(id, checkDate[i]);
			if(lst.size()>0) {
				for (int j = 0; j < lst.size(); j++) {
					vo = lst.get(j);
					minusOutTime = setOutTime(vo.getOutTime());
					enterDate = enterRoomDate(vo.getReDate());
					enterTime = enterRoomTime(vo.getInTime());
					test.roomrenum = vo.getRoomReNum();
					//시간이 지났는지 안지났는지 .. 
					if(enterDate<0) { //예약은 했으나, 아직 예약 날짜 안됨 
						askBtn.setEnabled(false);
						foodBtn.setEnabled(false);
						roomBtn.setText("룸예약확인");
						presentLbl.setText("<html>"+ vo.getReDate()+" [" +vo.getRoomNum()+"번 룸]<br>"+vo.getInTime()+" ~ "+
								minusOutTime+" 예약완료</html>");
						btnCheck = "1";//(플레이, 예약, 구매) 모두 비활성화 //예약은 했으나, 아직 예약날짜가 안됨. 
					}else if(enterDate==0) { //오늘날짜=예약날짜 
						if(enterTime<0) { //시간안되었음.
							askBtn.setEnabled(false);
							foodBtn.setEnabled(false);
							roomBtn.setText("룸예약확인");
							presentLbl.setText("<html>"+ vo.getReDate() +" [" +vo.getRoomNum()+"번 룸]<br>"+vo.getInTime()+" ~ "+
									minusOutTime+" 예약완료</html>");
							btnCheck = "1";//(플레이, 예약, 구매) 모두 비활성화// 예약은 했으나, 아직 예약시간이 안됨.
						}else if(enterTime>=0) {//시간이 지났거나, 같은시간이야. = 룸이용중이란 소리.
							int gamePrice = orderGamePriceGet(test.roomrenum);
							int foodPrice = orderFoodPriceGet(test.roomrenum);
							int totalPrice = vo.getPay() + gamePrice + foodPrice;
							nowGameGet(test.roomrenum);
							reserveGameGet(test.roomrenum);
							askBtn.setEnabled(true);
							foodBtn.setEnabled(true);
							roomBtn.setText("룸이용내역");
							presentLbl.setText("<html> - 룸 : " + vo.getRoomNum()+"번 방 <br>" + " -이용 시간 : "+vo.getInTime()+" ~ "+minusOutTime +"<br> - 현재 플레이 게임 : "+ test.nowgame + "<br> - 예약 게임 : " +test.resergame+ 
											"<br><br> - 결제 예정 금액 : " + dFmt.format(totalPrice));
							btnCheck = "2";//(플레이, 구매) 활성화// 예약시간이되어 입실 시간이 되었음. 
						}
					}
				}
				break;
			}else if(lst.size()==0) { //예약없어, 그럼 예약하는 버튼의 이름을 바꿔여해 . 활성화하고 음식버튼 비활성화 해야해. 
				askBtn.setEnabled(false);
				foodBtn.setEnabled(false);
				roomBtn.setText("룸예약");
				presentLbl.setText("현재 이용중(예약중)인 룸이 없습니다.");
			}
		}
	}
	// 관리자 호출
	public void adminCall() {
		CallDAO dao=new CallDAO();
		
		int result=dao.InsertCall(test.roomrenum);
		
		if(result>0) {
			JOptionPane.showMessageDialog(this, "관리자를 호출하였습니다","관리자 호출 완료",JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	public static int orderGamePriceGet(int roomrenum) {
		OrderDetailDAO dao = new OrderDetailDAO();
		int gamePrice = dao.getOrderGamePrice(roomrenum);
		return gamePrice;
	}
	public static int orderFoodPriceGet(int roomrenum) {
		OrderDetailDAO dao = new OrderDetailDAO();
		int foodPrice = dao.getOrderFoodPrice(roomrenum);
		return foodPrice;
	}
	public void nowGameGet(int roomrenum) { 
	//플레이중인 게임
		OrderGameDAO dao = new OrderGameDAO();
		String nowplayGame = dao.getNowGame(roomrenum);
		test.nowgame = nowplayGame;
	}
	public void reserveGameGet(int roomrenum) { 
	//예약중인 게임,  
		OrderGameDAO dao = new OrderGameDAO();
		String reserveGame = dao.getReserveGame(roomrenum);
		test.resergame = reserveGame;
	}
	public void orderGameGet(int roomrenum) { 
	//구매한 게임 내역  
		OrderDetailDAO dao = new OrderDetailDAO();
		List<OrderDetailVO> lst = new ArrayList<OrderDetailVO>();
		lst = dao.getOrderGame(roomrenum);
		OrderDetailVO vo = new OrderDetailVO();
		if(lst.size() > 0) { //구매한게 있다면 ~
			for (int i = 0; i < lst.size(); i++) {
				vo = lst.get(i);
			}
		}else { //구매한게 없다면 ~
			
		}
	}
	public void orderFoodSet(int roomrenum) { 
	//주문한 음식 내역 
		OrderDetailDAO dao = new OrderDetailDAO();
		List<OrderDetailVO> lst = new ArrayList<OrderDetailVO>();
		lst = dao.getOrderFood(roomrenum);
		OrderDetailVO vo = new OrderDetailVO();
		if(lst.size() > 0) { //구매한게 있다면 ~
			for (int i = 0; i < lst.size(); i++) {
				vo = lst.get(i);
			}
		}else { //구매한게 없다면 ~
			
		}
	}
	public int enterRoomDate(String reDate) {
		int enterDate = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		String systemDate = sdf.format(System.currentTimeMillis());
		enterDate = systemDate.compareTo(reDate);
		return enterDate; // 1: 시스템날짜는 , 예약날짜 이후의 날짜,  0 = 시스템날짜와, 예약날짜 같음,  -1 : 시스템날짜는 예약날짜 이전의 날짜  (아직예약날짜안됨)
	}
	public int enterRoomTime(String inTime) {
		int enterTime = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.KOREA);
		String systemTime= sdf.format(System.currentTimeMillis());
		enterTime = systemTime.compareTo(inTime);
		return enterTime;// 1: 현재시간은 , 예약시간 이후,  0 = 시스템날짜와, 예약시간과 같음,  -1 : 시스템시간는 예약시간 이전의 날짜 (아직 예약시간안됨)  
	}
	public String setOutTime(String outTime) {
		String minusOutTime="";
		if(outTime.substring(0, 2).equals("10")) {
			minusOutTime = "9:50";
		}else if(outTime.substring(0, 2).equals("20")){
			minusOutTime = "19:50";
		}else {
			minusOutTime = Integer.toString(Integer.parseInt(outTime.substring(0, 2)) - 1) + ":50";
		}
		return minusOutTime;
	}
	public void userMainPageFrame() {
		//상단 관리자 문의 
		askPane.setLayout(null);	
		askPane.setBackground(Color.white);
		askPane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
			backBtn.setBackground(Color.LIGHT_GRAY);
			backBtn.setBounds(10, 10, 100, 40); askPane.add(backBtn);
			askBtn.setBackground(Color.LIGHT_GRAY);
			askBtn.setBounds(330, 10, 100, 40);	askPane.add(askBtn);
		askPane.setBounds(0, 0, 460, 60);
		//현황판
		presentPane.setLayout(null);
		presentPane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
		presentPane.setBackground(Color.white);
			presentLbl.setVerticalAlignment(JLabel.TOP);
			presentSp.setBounds(60, 40, 340, 220);
			presentSp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			presentPane.add(presentSp);
		presentPane.setBounds(0, 60, 460, 300);
		//버튼 4개 
		btnPane.setLayout(null);
		btnPane.setBackground(Color.white);
			roomBtn.setBackground(Color.LIGHT_GRAY);
			roomBtn.setBounds(80, 20, 120, 120);	btnPane.add(roomBtn);
			boardgameBtn.setBackground(Color.LIGHT_GRAY);
			boardgameBtn.setBounds(260, 20, 120, 120);	btnPane.add(boardgameBtn);
			foodBtn.setBackground(Color.LIGHT_GRAY);
			foodBtn.setBounds(80, 180, 120, 120);	btnPane.add(foodBtn);
			personDataBtn.setBackground(Color.LIGHT_GRAY);
			personDataBtn.setBounds(260, 180, 120, 120);	btnPane.add(personDataBtn);
		btnPane.setBounds(0, 360, 460, 440);
		setLayout(null);
		add(askPane);
		add(presentPane);
		add(btnPane);
		setVisible(true);
		setSize(460, 800);
	}
}
