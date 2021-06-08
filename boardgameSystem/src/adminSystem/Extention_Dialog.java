package adminSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import vo.ReservationDAO;
import vo.ReservationVO;
import vo.RoomInfoDAO;
import vo.RoomInfoVO;

public class Extention_Dialog extends JDialog implements ActionListener, ItemListener{
	JPanel mainPane = new JPanel();
	JLabel extentionTimeLbl = new JLabel("이용시간 연장");
	JLabel nowOutTime = new JLabel("현재 퇴실시간");
	JLabel nowOutTimeLbl = new JLabel();
	JLabel changeOutTime = new JLabel("변경 퇴실시간");
	JComboBox<String> choiceOutTime = new JComboBox<String>();
	String time[] = {"11:00", "12:00", "13:00","14:00","15:00","16:00",
			"17:00","18:00","19:00","20:00","21:00"};
	JLabel addPrice = new JLabel("추가금액");
	JLabel addPriceLbl = new JLabel();
	JLabel wonLbl = new JLabel("원");
	JButton addTimeBtn = new JButton("연장하기");
	JButton exitBtn = new JButton("닫기");
	Color co = Color.white;
	Font fnt1 = new Font("돋움체", Font.BOLD, 20);
	Font fnt2 = new Font("돋움체", Font.BOLD, 15);;
	Font fnt3 = new Font("돋움체", Font.PLAIN, 15);;
	int roomnum = 0;
	int roomrenum = 0;
	int plusPay = 0;
	int nowPay=0;
	int start = 0, choiceArrayIndex = 0;
	public Extention_Dialog() { }
	public Extention_Dialog(int roomnum, int roomrenum, String outTime, String outTimeSet) {
		Extention_Dialog_Pane();
		this.roomnum=roomnum;
		this.roomrenum = roomrenum;
		nowOutTimeLbl.setText(outTime +"("+outTimeSet+")");
		timeComboSet(roomnum, roomrenum, outTime);
		
		addTimeBtn.addActionListener(this);
		exitBtn.addActionListener(this);
		choiceOutTime.addItemListener(this);
	}
	@Override
	public void itemStateChanged(ItemEvent ie) {
		Object obj = ie.getSource();
		if(obj==choiceOutTime) {
			String choice = (String)choiceOutTime.getSelectedItem(); 
			for (int i = 0; i < time.length; i++) {
				if(time[i].equals(choice)) {
					choiceArrayIndex = i;
				}
			}
			plusPaySet(roomnum, choiceArrayIndex);
		}
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		if(obj==addTimeBtn) {
			//룸예약번호 가지구 -> DAO퇴실시간 변경, pay 변경 
			nowPayGet(roomrenum);
			outTimeChangeSet(roomrenum, nowPay ,plusPay);
		}else if(obj==exitBtn) {
			Detail_Info_Dialog did = new Detail_Info_Dialog(roomnum);
			setVisible(false);
		}
	}
	public void plusPaySet(int roomnum, int choiceArrayIndex) {
		addPriceLbl.removeAll();
		//룸넘버로 금액을 구해야함 
		RoomInfoDAO dao = new RoomInfoDAO();
		List<RoomInfoVO> lst = dao.setRoomPay(roomnum);
		RoomInfoVO vo = lst.get(0);
		plusPay =(choiceArrayIndex-start) *vo.getRoomPrice();
		DecimalFormat dFmt = new DecimalFormat("#,###");
		addPriceLbl.setText(dFmt.format(plusPay));
	}
	public void nowPayGet(int roomrenum) {
		//현재 금액 받아오기. 
		ReservationDAO dao = new ReservationDAO();
		nowPay = dao.getPay(roomrenum);
	}
	public void outTimeChangeSet(int roomrenum, int nowPay, int plusPay) {
		//DAO퇴실시간 변경, pay 변경 
		int changePay = nowPay + plusPay;
		Calendar now = Calendar.getInstance();
		SimpleDateFormat nowFmt = new SimpleDateFormat("YYYY-MM-dd");
		String nowStr = nowFmt.format(now.getTime());
		String changeTime = nowStr + " " + time[choiceArrayIndex];
		System.out.println(changeTime + changePay);
		
		ReservationDAO dao = new ReservationDAO();
		int result = dao.setOutTimeChange(roomrenum, changePay, changeTime);
		if(result>0) {
			JOptionPane.showMessageDialog(this, "이용시간 연장되었습니다.");
			setVisible(false);
		}else {
			JOptionPane.showMessageDialog(this, "이용시간 연장이 불가합니다.");
			setVisible(false);
		}
			Detail_Info_Dialog did = new Detail_Info_Dialog(roomnum);
		//디테일 인포의 내용이 변해야한다.  시간변경. .. 그럼 다시생성자를 불러줘야하는데,
	}
	public void timeComboSet(int roomnum, int roomrenum, String outTime) {
		//현재 퇴실시간부터콤보박스에 넣기. //다음입실시간 사용자확인하여 거기서부터는 콤보박스에 넣지않기. 
		ReservationVO nextReVo = nextIn(roomnum);
			int stop = 10;
		for (int i = 0; i < time.length; i++) {
			if(time[i].equals(outTime)) {
				start = i;
			}
			if(time[i].equals(nextReVo.getInTime())) {
				stop = i;
			}
		}
		if(start==stop) { 
			//연장 불가 
			choiceOutTime.addItem("연장불가"); 
		}else if(start==10){  //퇴실시간이 21시 일경우 
			choiceOutTime.addItem("연장불가"); 
		}else { //연장가능 
			for (int i = start+1; i <= stop; i++) {
				choiceOutTime.addItem(time[i]); 
			}
			choiceOutTime.setSelectedIndex(0);
			choiceArrayIndex = start+1;
			addTimeBtn.setEnabled(true);
			plusPaySet(roomnum, choiceArrayIndex);
		}
	}
	public ReservationVO nextIn(int roomnum) {
		ReservationDAO dao1 = new ReservationDAO();
		List<ReservationVO> nextReLst = new ArrayList<ReservationVO>();
		nextReLst =  dao1.getNextReserveRoomInfo(roomnum);
		ReservationVO nextReVo = new ReservationVO();
		if(nextReLst.size()>0) { //다음예약있습니다~
			nextReVo = nextReLst.get(0);
		}
		return nextReVo;
	}
	public void Extention_Dialog_Pane() {
		mainPane.setBackground(co);
		
		extentionTimeLbl.setBounds(30, 10, 200, 50);	extentionTimeLbl.setFont(fnt1);
		nowOutTime.setBounds(40, 70, 100, 40);	nowOutTime.setFont(fnt2);
		nowOutTimeLbl.setBounds(140, 70, 150, 40); nowOutTimeLbl.setFont(fnt3);
		changeOutTime.setBounds(40, 110, 100, 40);	changeOutTime.setFont(fnt2);
		choiceOutTime.setBounds(140, 110, 150, 40);	choiceOutTime.setFont(fnt3);
		addPrice.setBounds(40, 170, 100, 40); 		addPrice.setFont(fnt2);
		addPriceLbl.setBounds(140, 180, 120, 20);	addPriceLbl.setFont(fnt3);
		addPriceLbl.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		wonLbl.setBounds(262, 170, 20, 40); 		wonLbl.setFont(fnt3);
		addTimeBtn.setBounds(100, 220, 90, 40); exitBtn.setBounds(200, 220, 90, 40);
		addPriceLbl.setHorizontalAlignment(JLabel.RIGHT);
		mainPane.setLayout(null);
		mainPane.add(extentionTimeLbl);
		mainPane.add(nowOutTime);	mainPane.add(nowOutTimeLbl);
		mainPane.add(changeOutTime);	mainPane.add(choiceOutTime);
		mainPane.add(addPrice);			mainPane.add(addPriceLbl);	mainPane.add(wonLbl);
		mainPane.add(addTimeBtn);	mainPane.add(exitBtn);
		addTimeBtn.setEnabled(false);
		add(mainPane);	
		setSize(320, 300);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}
