package userSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

import vo.ReservationDAO;
import vo.ReservationVO;
import vo.RoomInfoDAO;
import vo.RoomInfoVO;


public class RoomReservePage extends JPanel implements ItemListener,ActionListener{
	Test test;
	JPanel askPane = new JPanel();
	JButton backBtn = new JButton("⇦ 뒤로");
	JButton askBtn = new JButton("관리자문의");
	
	JPanel datePane = new JPanel();
		JLabel dateLbl = new JLabel("예약일");
		JComboBox<String> dateCombo = new JComboBox<String>();
	JPanel roomNumPane = new JPanel();
		JLabel roomNumLbl = new JLabel("룸 선택");
		JRadioButton roomRb1 = new JRadioButton("1~2인실");
		JRadioButton roomRb2 = new JRadioButton("3~4인실");
		JRadioButton roomRb3 = new JRadioButton("5~6인실");
		ButtonGroup bg = new ButtonGroup();
		JComboBox<String> roomChoice = new JComboBox<String>();
	String time[] = { "9:00", "10:00", "11:00", "12:00", "13:00","14:00","15:00","16:00",
						"17:00","18:00","19:00","20:00","21:00"};	
	
	JPanel inTimePane = new JPanel();
		JLabel inTimeLbl = new JLabel("입실 시간");
		JPanel inTimeSelectPane = new JPanel(new GridLayout(3,4,10,10));
		JButton inTimeBtn[] = new JButton[12];
	JPanel outTimePane = new JPanel();
		JLabel outTimeLbl = new JLabel("퇴실 시간");
		JPanel outTimeSelectPane = new JPanel(new GridLayout(3,4,10,10));
		JButton outTimeBtn[] = new JButton[12];
	JPanel btnPane = new JPanel();
		JButton reserveBtn = new JButton("예약하기");
		JButton cancelBtn = new JButton("취소");
	ReserveInfoDialog reDial;
	//달력관련 데이터 
	Calendar date; // 달력관련 데이터 객체 생성 
	int year, month, day; //월을 저장할 객체 생성
	String id;
	String choiceDate = "";
	String chDate[] = new String[3];
	int choiceRoomPeople = 2, choiceRoomNum = 1;
	int roomRePay = 0, roomPay=0;
	String choiceInTime ="", choiceOutTime ="";

	Font fnt = new Font("굴림", Font.BOLD, 15);
	public RoomReservePage() { }
	public RoomReservePage(Test test, String userId) {
		this.test = test;
		id = userId;
		roomReservePageFrame();
		reDial = new ReserveInfoDialog();
		setDate();
		int close = closeReserveTime("20:00");
		if (close >=0) {
			dateCombo.setSelectedIndex(1);
			choiceDate = chDate[1];
		}
		
		askBtn.setEnabled(false);
		backBtn.addActionListener(this);
		askBtn.addActionListener(this);
		dateCombo.addItemListener(this);
		roomRb1.addItemListener(this);	roomRb2.addItemListener(this);	roomRb3.addItemListener(this);
		roomChoice.addItemListener(this);
		for (int i = 0; i < inTimeBtn.length; i++) {
			inTimeBtn[i].addActionListener(this);
			outTimeBtn[i].addActionListener(this);
		}
		reserveBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
	}
	@Override
	public void itemStateChanged(ItemEvent ie) {
		Object obj = ie.getSource();
		if(obj ==dateCombo) {
			choiceDate = (String)dateCombo.getSelectedItem();
			setRoomNum();
		}else if(obj==roomRb1 || obj==roomRb2 || obj==roomRb3) {
			if(obj==roomRb1) {//2  //RoomInfoDAO 랑 연결 
				choiceRoomPeople = 2;
				setRoomNum();
			}else if(obj == roomRb2) {//4
				choiceRoomPeople = 4;
				setRoomNum();
			}else if(obj==roomRb3) {//6 //RoomInfoDAO 랑 연결
				choiceRoomPeople = 6;
				setRoomNum();
			}
		}else if(obj == roomChoice) {
			choiceRoomNum = Integer.parseInt(((String)roomChoice.getSelectedItem()).substring(0, 1));
			choiceOutTime ="";
			//룸넘버를 입력하면.. Reservation 으로 가서, 선택한날짜에 맞는 룸넘버를 찾아서 있으면 예약시간 가져오기. 
			// 해당예약시간은 예약불가로 버튼 처리 
			setInTime();
		}
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		for (int i = 0; i < inTimeBtn.length; i++) {
			if(inTimeBtn[i] == obj) {
				choiceInTime=inTimeBtn[i].getText(); 
				setOutTime(i);
			}else if(outTimeBtn[i] == obj){
				choiceOutTime = outTimeBtn[i].getText();
			}
		}
		if(reserveBtn == obj) { 
			for (int i = 0; i < chDate.length; i++) {
				ReservationDAO dao = new ReservationDAO();
				ReservationVO vo = new ReservationVO();
				List<ReservationVO> lst = dao.usingStateSet(id, chDate[i]);
				if(lst.size()>0) {
					JOptionPane.showMessageDialog(this, "예약중입니다.");
					break;
				}else {
					boolean boo = bg.isSelected(null);
					if(bg.isSelected(null)==true){ 
						JOptionPane.showMessageDialog(this, "몇인실을 이용할 것인지 선택해 주세요");
					}else if(choiceInTime.equals("") || choiceOutTime.equals("")) {
						JOptionPane.showMessageDialog(this, "예약할 입실시간과, 퇴실시간을 모두 선택해 주세요");
					}else {
						setPay();
					} 
					break;
				}
			}	
		}else if(backBtn == obj || cancelBtn == obj) {
			test.change("main", id);
		}else if(askBtn == obj) {
			//관리자문의 
		}
	}
	public int closeReserveTime(String time) {
		int close = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.KOREA);
		String systemTime= sdf.format(System.currentTimeMillis());
		close = systemTime.compareTo(time);
		return close;// 1: 현재시간은 ,예약안받는시간 이후,  0 = 시스템날짜와, 예약안받는시간과 같음,  -1 : 시스템시간는 예약시간 이전의 날짜 (아직 예약시간안됨)  
	} // >=0 조건.  
	public void setPay() { //선택한 시간에 맞는 룸 비용 계산
		RoomInfoDAO dao = new RoomInfoDAO();
		List<RoomInfoVO> lst =  dao.setRoomPay(choiceRoomNum);
		RoomInfoVO vo = lst.get(0);
		int in = 0;
		if(choiceInTime.substring(0, 1).equals("9")) {
			in = Integer.parseInt((choiceInTime.substring(0, 1)));
		}else {
			in = Integer.parseInt((choiceInTime.substring(0, 2))); //10:00이면 10 만 잘러 //9면어떸캐?
		}
		int out = Integer.parseInt((choiceOutTime.substring(0, 2)));
		roomPay =vo.getRoomPrice();
		roomRePay = (out - in) * roomPay;
		reDial.ReserveInfoDialog(id, choiceDate,choiceRoomPeople ,choiceRoomNum, roomPay, roomRePay, choiceInTime, choiceOutTime );
		
	}
	public void setOutTime(int index) {
		for (int i = 0; i < outTimeBtn.length; i++) {
			outTimeBtn[i].setEnabled(true); 
			inTimeBtn[i].setFont(getFont());
		}
		inTimeBtn[index].setFont(new Font("돋움", Font.BOLD, 15));
		ReservationDAO dao = new ReservationDAO();
		List<ReservationVO> lst = dao.TimeSet(choiceDate, choiceRoomNum);
		for (int i = 0; i < index; i++) {
			outTimeBtn[i].setEnabled(false);
		}
		if(lst.size()==0) {
		}else if(lst.size()>0) {
			for (int i = 0; i < lst.size(); i++) {
				ReservationVO vo = lst.get(i);
				for (int j = index; j < outTimeBtn.length; j++) {
					if(outTimeBtn[j].getText().equals(vo.getInTime())){
						for (int k = j+1; k < outTimeBtn.length; k++) {
						outTimeBtn[k].setEnabled(false); 
						}
					}
				}
			}
		}
	}
	public void setInTime() {
		for (int i = 0; i < inTimeBtn.length; i++) {
			inTimeBtn[i].setEnabled(true); 
			inTimeBtn[i].removeActionListener(this);
			inTimeBtn[i].setFont(getFont());
			outTimeBtn[i].setEnabled(false);
		}
		if(choiceDate.equals(date.get(Calendar.YEAR)+"-"+(date.get(Calendar.MONTH)+1)+"-"+date.get(Calendar.DATE))) {	
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.KOREA);
			String preTime= sdf.format(System.currentTimeMillis());
			int noreserve = closeReserveTime(preTime);
			if(noreserve>=0) { //같거나 시간이 지났으면,  
				for (int i = 0; i < inTimeBtn.length; i++) {
					//System.out.println(inTimeBtn[0].getText() +" " + ((preTime.substring(0, 1)) + ":00") );
					if((0+inTimeBtn[0].getText()).equals(((preTime.substring(0, 2)) + ":00"))) {
						System.out.println("if" + ( (preTime.substring(0, 2)) + ":00"  ));
						System.out.println(preTime);
						inTimeBtn[0].setEnabled(false);
						break;
					}else if(inTimeBtn[i].getText().equals(((preTime.substring(0, 2)) + ":00"))) {
						System.out.println("else" + ( (preTime.substring(0, 2)) + ":00"  ));
						System.out.println(preTime);
						
						for (int j = 0; j <= i; j++) {
							inTimeBtn[j].setEnabled(false);
						}
						break; 
					}
				}
			}
		}
		ReservationDAO dao = new ReservationDAO();
		List<ReservationVO> lst = dao.TimeSet(choiceDate, choiceRoomNum);
		if(lst.size()==0) {
		}else if(lst.size()>0) {
			for (int i = 0; i < lst.size(); i++) {
				ReservationVO vo = lst.get(i);
				int startNo = 0, stopNo = 0;
				for (int j = 0; j < inTimeBtn.length; j++) {
					if(inTimeBtn[j].getText().equals(vo.getInTime())) { //버튼에 텍스트가. in이랑 같으면.. 
						//여기에 들어오는 J는 비활성화 시작 인덱스. 
						startNo = j;
					}else if(outTimeBtn[j].getText().equals(vo.getOutTime())) {
						stopNo = j+1;
					}
				} //1 ~5  (12:00 ~ 15:00)  예약할때 15시는 가능하다. 
				for (int k = startNo; k < stopNo; k++) {
					inTimeBtn[k].setEnabled(false); 
				}
			}	
		}
		for (int i = 0; i < inTimeBtn.length; i++) {
			inTimeBtn[i].addActionListener(this);
		}
	}
	public void setRoomNum() {
		choiceInTime ="";	choiceOutTime ="";
		roomChoice.removeItemListener(this);
		roomChoice.removeAllItems();
		RoomInfoDAO dao = new RoomInfoDAO();
		List<RoomInfoVO> lst = dao.setRoomInfo(choiceRoomPeople);
		for (int i = 0; i < lst.size(); i++) {
			RoomInfoVO vo = lst.get(i);
			roomChoice.addItem(vo.getRoomNum() +"번 방");
			if(i ==0) {
				choiceRoomNum = vo.getRoomNum();
			}
		}
		roomChoice.addItemListener(this);
		setInTime();
	}
	public void setDate() {
		date = Calendar.getInstance();
		int lastDay = date.getActualMaximum(Calendar.DATE);
		year = date.get(Calendar.YEAR);
		month = date.get(Calendar.MONTH)+1;
		day = date.get(Calendar.DATE);
		choiceDate = year +"-"+ month +"-"+ day;
		for (int i = 0; i < 3; i++) {
			chDate[i] = year +"-"+ month +"-"+ day;
			if(month == 12 && lastDay < day) {
				year ++;	month=1;
			}else if(lastDay < day) {
				day =1;		month ++;
			}
			dateCombo.addItem(year +"-"+ month +"-"+ day);		
			day++;
		}
	}
	public void roomReservePageFrame() {
		//상단 관리자 문의 
		askPane.setLayout(null);	
		askPane.setBackground(Color.white);
		askPane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
			backBtn.setBackground(Color.LIGHT_GRAY);
			backBtn.setBounds(10, 10, 100, 40); askPane.add(backBtn);
			askBtn.setBackground(Color.LIGHT_GRAY);
			askBtn.setBounds(330, 10, 100, 40);	askPane.add(askBtn);
		askPane.setBounds(0, 0, 460, 60);
		
		//예약일 선택
		datePane.setLayout(null);
		datePane.setBackground(Color.white);
			dateLbl.setFont(fnt);
			dateLbl.setBounds(60, 10, 70, 40);	datePane.add(dateLbl);
			dateCombo.setBounds(140, 10, 200, 30); datePane.add(dateCombo);
		datePane.setBounds(0, 60, 460, 60);
		//룸선택
		roomNumPane.setLayout(null);
		roomNumPane.setBackground(Color.white);
		roomNumPane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
			roomNumLbl.setFont(fnt);
			roomNumLbl.setBounds(60, 10, 70, 40);	roomNumPane.add(roomNumLbl);
			roomRb1.setBounds(60, 50, 100, 40);		roomNumPane.add(roomRb1);
			roomRb2.setBounds(180, 50, 100, 40);		roomNumPane.add(roomRb2);
			roomRb3.setBounds(300, 50, 100, 40);		roomNumPane.add(roomRb3);
			roomRb1.setBackground(Color.white);  	roomRb2.setBackground(Color.white);
			roomRb3.setBackground(Color.white);
			bg.add(roomRb1); 	bg.add(roomRb2);	bg.add(roomRb3);
			roomChoice.setBounds(130, 90, 200, 30);	roomNumPane.add(roomChoice);	
		roomNumPane.setBounds(0, 120, 460, 130);
		
		//입실시간 선택
		inTimePane.setLayout(null);
		inTimePane.setBackground(Color.white);	
			inTimeLbl.setFont(fnt);
			inTimeLbl.setBounds(60, 10, 100, 40);
			inTimeSelectPane.setBackground(Color.white);
			inTimeSelectPane.setBounds(30, 60, 400, 140);
			for (int i = 0; i < time.length-1; i++) {
				inTimeBtn[i] =new JButton(time[i]);
				inTimeSelectPane.add(inTimeBtn[i]);
				inTimeBtn[i].setEnabled(false); 
			}
		inTimePane.add(inTimeLbl);	inTimePane.add(inTimeSelectPane);
		inTimePane.setBounds(0, 250, 460, 220);
		//퇴실시간선택 
		outTimePane.setLayout(null);
		outTimePane.setBackground(Color.white);
		outTimePane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
			outTimeLbl.setFont(fnt);
			outTimeLbl.setBounds(60, 10, 100, 40);
			outTimeSelectPane.setBackground(Color.white);
			outTimeSelectPane.setBounds(30, 60, 400, 140);
			for (int i = 1; i < time.length; i++) {
				outTimeBtn[i-1] =new JButton(time[i]);
				outTimeSelectPane.add(outTimeBtn[i-1]);
				outTimeBtn[i-1].setEnabled(false); 
			}
		outTimePane.add(outTimeLbl);	outTimePane.add(outTimeSelectPane);
		outTimePane.setBounds(0, 470, 460, 220);
		//선택
		btnPane.setLayout(null);
		btnPane.setBackground(Color.white);
		btnPane.setBounds(0, 690, 460, 800);
		reserveBtn.setBackground(Color.LIGHT_GRAY);
		cancelBtn.setBackground(Color.LIGHT_GRAY);
		reserveBtn.setBounds(100, 20, 100, 40); btnPane.add(reserveBtn);
		cancelBtn.setBounds(250, 20, 100, 40); btnPane.add(cancelBtn);
		
		setLayout(null);
		add(askPane);	add(datePane);	add(roomNumPane);	add(inTimePane);
		add(outTimePane);	add(btnPane);
		
		setVisible(true);
		setSize(460, 800);
	}
	class ReserveInfoDialog extends JDialog implements ActionListener{
		JPanel pane = new JPanel();
		JLabel confirmLbl = new JLabel("예약사항 확인");
		JLabel coDateLbl = new JLabel("예약일");
		JLabel coRoomNum = new JLabel("룸");
		JLabel coInTimeLbl = new JLabel("입실시간");
		JLabel coOutTimeLbl = new JLabel("퇴실시간");
		JLabel outNoticeLbl = new JLabel("<html>룸 청소시간으로 인하여, <br>선택하신 퇴실시간 10분전에 방을 비워주셔야 합니다.</html>");
		JLabel coPayLbl = new JLabel("룸 이용비용");
		JLabel coNoticeLbl = new JLabel();
		
		JLabel reDateLbl = new JLabel();
		JLabel reRoomNum = new JLabel();
		JLabel reInTimeLbl = new JLabel();
		JLabel reOutTimeLbl = new JLabel();
		JLabel rePayLbl = new JLabel();
		JButton okBtn = new JButton("예약완료");
		JButton ccBtn = new JButton("취소");
		
		Font cofirmFnt = new Font("돋움체", Font.BOLD, 20);
		Font coFnt = new Font("돋움체", Font.BOLD, 16);
		Font reFnt = new Font("돋움체", Font.PLAIN, 15);
		Font noticeFnt = new Font("돋움체", Font.PLAIN, 11);
		
		DecimalFormat dFmt = new DecimalFormat("#,###원");
		String minusOutTime="", roomRePayFmt="", roomPayFmt="";
		String uId="", redate="", intime="", outtime=""; 
		int roomnum=0, pay=0;
		public ReserveInfoDialog() { }
		public void ReserveInfoDialog(String userId, String date, int roomPeople, int roomNum, int roomPay,int roomrePay, String in, String out) {	
			uId = userId;	roomnum=roomNum;	redate=date;	intime=in;	outtime=out;	pay=roomrePay;
			roomPayFmt=dFmt.format(roomPay);
			roomRePayFmt=dFmt.format(roomrePay);
			
			coNoticeLbl.setText("<html>" + (roomPeople-1) +"~"+roomPeople+"인 실은 시간당 " + roomPayFmt +"이며,<br> 비용은 이용후에 합산하여 계산합니다.</html>");
			reDateLbl.setText(date);
			reRoomNum.setText(roomNum+"번 룸 ("+(roomPeople-1)+"~"+roomPeople+"인 실)");
			reInTimeLbl.setText(in);
			if(out.substring(0, 2).equals("10")) {
				minusOutTime = "9:50";
			}else if(out.substring(0, 2).equals("20")){
				minusOutTime = "19:50";
			}else {
				minusOutTime = Integer.toString(Integer.parseInt(out.substring(0, 2)) - 1) + ":50";
			}
			reOutTimeLbl.setText(out+" ("+minusOutTime+")");
			rePayLbl.setText(roomRePayFmt);
			
			
			dialFrame();
			okBtn.addActionListener(this);
			ccBtn.addActionListener(this);
		}	
		@Override
		public void actionPerformed(ActionEvent ae) {
			Object obj = ae.getSource();
			if(obj == okBtn) {
				//예약된 DB있는지 확인.  
				setInsertReservation();
				// 예약완 DB연결, 인설트 
			}else if(obj == ccBtn) {
				// 예약취소 창닫기 .
				setVisible(false);
			}
		}
		public void setInsertReservation() {
			ReservationVO vo = new ReservationVO(uId, roomnum, redate, intime, outtime, pay);
			ReservationDAO dao = new ReservationDAO();
			int result = dao.reservationSet(vo);
			if(result>0) {
				JOptionPane.showMessageDialog(this, "룸 예약 완료 되었습니다.");
				test.change("main", uId);
				setVisible(false);
			}else {
				JOptionPane.showMessageDialog(this, "룸 예약에 실패하였습니다.");
				setVisible(false);
			}
		}
		public void dialFrame() {
			setLayout(new BorderLayout());
			pane.setLayout(null);	pane.setBackground(Color.white);
			confirmLbl.setBounds(40, 20, 300, 40);	confirmLbl.setFont(cofirmFnt);
			coDateLbl.setBounds(50, 70, 100, 30);	coDateLbl.setFont(coFnt);
			coRoomNum.setBounds(50, 110, 100, 30);	coRoomNum.setFont(coFnt);
			coInTimeLbl.setBounds(50, 150, 100, 30);	coInTimeLbl.setFont(coFnt);
			coOutTimeLbl.setBounds(50, 180, 100, 30);	coOutTimeLbl.setFont(coFnt);
			outNoticeLbl.setBounds(50, 210, 300, 30);	outNoticeLbl.setFont(noticeFnt);
			coPayLbl.setBounds(50, 250, 100, 30);	coPayLbl.setFont(coFnt);
			coNoticeLbl.setBounds(50, 280, 300, 30); 	coNoticeLbl.setFont(noticeFnt);
			reDateLbl.setBounds(150, 70, 200, 30);	reDateLbl.setFont(reFnt);
			reRoomNum.setBounds(150, 110, 200, 30);	reRoomNum.setFont(reFnt);
			reInTimeLbl.setBounds(150, 150, 200, 30);	reInTimeLbl.setFont(reFnt);
			reOutTimeLbl.setBounds(150, 180, 200, 30);	reOutTimeLbl.setFont(reFnt);
			rePayLbl.setBounds(150, 250, 200, 30);	rePayLbl.setFont(reFnt);
			okBtn.setBounds(40, 340, 100, 40); 
			ccBtn.setBounds(180, 340, 100, 40); 	
			pane.add(confirmLbl); pane.add(coDateLbl); pane.add(coRoomNum);
			pane.add(coInTimeLbl);	pane.add(coOutTimeLbl);	pane.add(coPayLbl);
			pane.add(reDateLbl); pane.add(reRoomNum); 
			pane.add(reInTimeLbl);	pane.add(reOutTimeLbl); pane.add(rePayLbl);
			pane.add(outNoticeLbl);
			pane.add(coNoticeLbl); pane.add(okBtn);	pane.add(ccBtn);
			pane.setSize(340, 440);
			add(pane);
			setVisible(true);
			setSize(340, 440);
		}
	}
}
