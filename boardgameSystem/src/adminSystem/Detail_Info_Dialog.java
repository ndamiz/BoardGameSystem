package adminSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vo.OrderDetailDAO;
import vo.OrderDetailVO;
import vo.ReservationDAO;
import vo.ReservationVO;
							
public class Detail_Info_Dialog extends JDialog implements ActionListener{
	JPanel titlePane = new JPanel();
		JLabel titleLbl = new JLabel("번 룸 상세정보");
	JPanel infoPane = new JPanel();
		JLabel infoLbl = new JLabel("<현재 정보>");
		JPanel infoPane2 = new JPanel(new GridLayout(6,1));
			JLabel idLbl = new JLabel("이용 회원");
			JLabel inTimeLbl = new JLabel("입실시간");
			JLabel outTimeLbl = new JLabel("퇴실시간");
			JLabel playGameLbl = new JLabel("플레이중 게임");
			JLabel reGameLbl = new JLabel("예약중 게임");
			JLabel buyGameLbl = new JLabel("구매 게임");
		JPanel infoPane3 = new JPanel(new GridLayout(6,1));
			JButton extentionBtn = new JButton("<html>룸 이용 시간 <br>&nbsp;&nbsp;연장하기 </html>");
	JPanel orderPane = new JPanel();	
		JLabel orderLbl = new JLabel("<결제 예정 금액>");
		JPanel orderPane2 = new JPanel(new GridLayout(4,1));	
			JLabel roomPriceLbl = new JLabel("룸 대여");
			JLabel orderFoodLbl = new JLabel("음식 주문");
			JLabel orderGameLbl = new JLabel("게임 주문");
			JLabel priceLbl = new JLabel("합 계");
		JPanel orderPane3 = new JPanel(new GridLayout(4,1));
			JButton payBtn = new JButton("결제하기");
	JPanel nextPane = new JPanel();
		JLabel nextroomLbl = new JLabel("<룸 예약 정보>");
		JPanel nextPane2 = new JPanel(new GridLayout(3,1));
			JLabel nextIdLbl = new JLabel("예약 회원");
			JLabel nextInTimeLbl = new JLabel("입실시간");
			JLabel nextOutTimeLbl = new JLabel("퇴실시간");
		JPanel nextPane3 = new JPanel(new GridLayout(3,1));
	JPanel btnPane = new JPanel();
		JButton exitBtn = new JButton("닫기");
	Color co = Color.white;
	Font fnt1 = new Font("돋움체", Font.BOLD, 25);
	Font fnt2 = new Font("돋움체", Font.BOLD, 20);;
	int roomnum = 0;
	int roomrenum = 0;
	int totalPay = 0;
	String userId = "";
	String outTime = "", inTime="";
	String outTimeSet = "";
	String payCheck = "";
	public Detail_Info_Dialog() { }
	public Detail_Info_Dialog(int roomnum) {
		this.roomnum = roomnum;
		Detail_Info_Pane();
		nowInfo(roomnum);
		titleLbl.setText(roomnum +"번 룸 상세정보");
		System.out.println(payCheck);
		if(outTime.equals("21:00")) { //퇴실시간이 21시일경우. 
			//연장불가, 버튼 비활성화. 
			extentionBtn.setEnabled(false);
		}else if(roomrenum==0) { //사용중이 아닐경우.
			extentionBtn.setEnabled(false);
			payBtn.setEnabled(false);
		}
		if(payCheck.equals("Y")) {
			payBtn.setEnabled(false);
			extentionBtn.setEnabled(false);
			orderLbl.setText("결제완료");
			orderPane3.removeAll();
			infoPane3.removeAll();
			infoPane3.add(new JLabel(userId));
			infoPane3.add(new JLabel(inTime));
			infoPane3.add(new JLabel(outTimeSet));
		}
		extentionBtn.addActionListener(this);
		payBtn.addActionListener(this);
		exitBtn.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		if(obj==extentionBtn) {
			//연장하기 다이얼로그 ..우웩.. 
			new Extention_Dialog(roomnum, roomrenum, outTime, outTimeSet);
			setVisible(false);
		}else if(obj==payBtn) {
			//결제하기 다이얼로그
			new Pay_Dialog(roomnum, roomrenum, totalPay);
			setVisible(false);
		}else if(obj == exitBtn) {
			setVisible(false);
		}
	}
	public void nowInfo(int roomnum) {
		//1 현재시간을 이용해서  사용중인 룸을 알아내기 룸예약번호. // 룸결제비용같이 받기
		ReservationDAO dao = new ReservationDAO();
		List<ReservationVO> lst = new ArrayList<ReservationVO>();
		lst = dao.getRoomInfo(roomnum);
		ReservationVO reVO = new ReservationVO();
		if(lst.size()>0) { //어짜피 1개임  1개여야함. 
			reVO = lst.get(0);
			roomrenum = reVO.getRoomReNum();
			outTime = reVO.getOutTime();
			payCheck = reVO.getPayCheck();
			outTimeSet = AdminMain.setOutTime(reVO.getOutTime());
			userId = reVO.getId();
			inTime = reVO.getInTime();
			//2 게임 주문 금액받아오기 (합계) 
			int gamePrice = 0;
			gamePrice =AdminMain.orderGamePriceGet(reVO.getRoomReNum());  
			//3 음식 주문 금액 받아오기 (합계)
			int foodPrice = 0;
			foodPrice=	AdminMain.orderFoodPriceGet(reVO.getRoomReNum());  
			//4 현재 플레이중인 게임 이름 받아오기 
			String nowGame ="";
			nowGame= AdminMain.nowGameGet(reVO.getRoomReNum());
			//5 현재 예약중인 게임 이름 받아오기 
			String reserveGame="";
			reserveGame= AdminMain.reserveGameGet(reVO.getRoomReNum());
			//구매한게임 확인 
			OrderDetailVO orderGamelist[] = orderGameGet(reVO.getRoomReNum());
			String buyGame[] = new String[orderGamelist.length];
			for (int i = 0; i < orderGamelist.length; i++) {
				buyGame[i] = orderGamelist[i].getGamename();
			}
			//6 다음 사용자 확인
			ReservationDAO dao1 = new ReservationDAO();
			List<ReservationVO> nextReLst = new ArrayList<ReservationVO>();
			nextReLst =  dao1.getNextReserveRoomInfo(roomnum);
			ReservationVO nextReVo = new ReservationVO();
				if(nextReLst.size()>0) { //다음예약있습니다~
					nextReVo = nextReLst.get(0);
					//룸예약정보 등록
					nextPane3.add(new JLabel(nextReVo.getId()));
					nextPane3.add(new JLabel(nextReVo.getInTime()));
					nextPane3.add(new JLabel(outTimeSet));
				}else {
					//다음예약 없습니다~
				}
				//현재정보 등록.  
				infoPane3.add(new JLabel(reVO.getId()));	
				infoPane3.add(new JLabel(reVO.getInTime()));
				infoPane3.add(new JLabel(outTimeSet));	
				infoPane3.add(new JLabel(nowGame));
				infoPane3.add(new JLabel(reserveGame));
				infoPane3.add(new JLabel(Arrays.toString(buyGame)));

				//결제예정금액 등록
				DecimalFormat wonFmt = new DecimalFormat("#,###원");
				totalPay = reVO.getPay()+foodPrice+gamePrice;
				orderPane3.add(new JLabel(wonFmt.format(reVO.getPay())));	
				orderPane3.add(new JLabel(wonFmt.format(foodPrice)));
				orderPane3.add(new JLabel(wonFmt.format(gamePrice)));	
				orderPane3.add(new JLabel(wonFmt.format(totalPay)));

		}else {
			// 사용중이아니면 [미사용]
			//6 다음 사용자 확인
			ReservationDAO dao1 = new ReservationDAO();
			List<ReservationVO> nextReLst = new ArrayList<ReservationVO>();
			nextReLst =  dao1.getNextReserveRoomInfo(roomnum);
			ReservationVO nextReVo = new ReservationVO();
			if(nextReLst.size()>0) { //다음예약있습니다~
				nextReVo = nextReLst.get(0);
				nextPane3.add(new JLabel(nextReVo.getId()));
				nextPane3.add(new JLabel(nextReVo.getInTime()));
				nextPane3.add(new JLabel(AdminMain.setOutTime(nextReVo.getOutTime())));
			}
		}
	}
	// 룸버튼 누른뒤에 필요한거임. 
	public OrderDetailVO[] orderGameGet(int roomrenum) {  //게임 구매목록확인. 
		//구매한 게임 내역  
		OrderDetailDAO dao = new OrderDetailDAO();
		List<OrderDetailVO> lst = new ArrayList<OrderDetailVO>();
		lst = dao.getOrderGame(roomrenum);
		OrderDetailVO orderGamevo[] = new OrderDetailVO[lst.size()];
		if(lst.size() > 0) { //구매한게 있다면 ~
			for (int i = 0; i < lst.size(); i++) {
				orderGamevo[i] = lst.get(i);
			}
		}
		return orderGamevo;
	}
	public void Detail_Info_Pane() {
		titleLbl.setFont(fnt1); titlePane.setBackground(co); 
		titlePane.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		titlePane.add(titleLbl);	titlePane.setBounds(0, 0, 450, 40);
		//현재룸 정보 
		infoPane.setBackground(co);
		infoPane.setLayout(null); infoPane.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		infoPane.add(infoLbl);	infoLbl.setBounds(20, 10, 400, 30);	infoLbl.setFont(fnt2); 

		infoPane2.setBounds(40, 40, 100, 165); infoPane2.setBackground(co);
		infoPane2.add(idLbl); infoPane2.add(inTimeLbl);	infoPane2.add(outTimeLbl);
		infoPane2.add(playGameLbl);	infoPane2.add(reGameLbl);	
		infoPane2.add(buyGameLbl);
		
		//infoPane3 는 정보를 받아와서 for문으로 lable을 넣을것
		infoPane3.setBounds(150, 40, 250, 165); infoPane3.setBackground(co);
		
		
		extentionBtn.setBounds(310, 70, 120, 60);
		infoPane.add(extentionBtn);	infoPane.add(infoPane2);	infoPane.add(infoPane3);
		infoPane.setBounds(0, 40, 450, 210);
		//결제 예정 금액 
		orderPane.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		orderPane.setLayout(null);	orderPane.setBounds(0, 250, 450, 150);	orderPane.setBackground(co);
		orderPane.add(orderLbl);	orderLbl.setBounds(20, 10, 400, 30);	orderLbl.setFont(fnt2); 
		orderPane2.setBounds(40, 40, 100, 105); orderPane2.setBackground(co);
		orderPane2.add(roomPriceLbl); orderPane2.add(orderFoodLbl);	orderPane2.add(orderGameLbl);
		orderPane2.add(priceLbl);
		
		//orderPane3 은 정보를 받아와서 for문으로 laber을 넣을것 
		orderPane3.setBounds(150, 40, 155, 105); orderPane3.setBackground(co);
		
		payBtn.setBounds(310, 70, 120, 60);
		orderPane.add(payBtn);	orderPane.add(orderPane2);	orderPane.add(orderPane3);
		
		// 룸예약 정보
		nextPane.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		nextPane.setLayout(null);	nextPane.setBounds(0, 400, 450, 130);	nextPane.setBackground(co);
		nextPane.add(nextroomLbl);	nextroomLbl.setBounds(20, 10, 400, 30);	nextroomLbl.setFont(fnt2);
		nextPane2.setBounds(40, 40, 100, 85); nextPane2.setBackground(co);
		nextPane2.add(nextIdLbl); nextPane2.add(nextInTimeLbl);	nextPane2.add(nextOutTimeLbl);
		nextPane.add(nextPane2);	nextPane.add(nextPane3);
		//nextPane3 은 정보를 받아와서 for문으로 laber을 넣을것 
		nextPane3.setBounds(150, 40, 250, 85); nextPane3.setBackground(co);
		
		//닫기버튼 
		btnPane.setBounds(0, 530, 450, 600);	btnPane.setBackground(co);
		btnPane.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		
		btnPane.add(exitBtn);
		add(titlePane);	add(infoPane);	add(orderPane);	add(nextPane);	add(btnPane);
		
		setLayout(null);
		setSize(450,600);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
