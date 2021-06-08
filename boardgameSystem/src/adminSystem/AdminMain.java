package adminSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import vo.CallDAO;
import vo.CallVO;
import vo.OrderDetailDAO;
import vo.OrderDetailVO;
import vo.OrderFoodDAO;
import vo.OrderGameDAO;
import vo.ReservationDAO;
import vo.ReservationVO;

public class AdminMain extends JPanel implements MouseListener{	
	MainFrame mainFrm;
	JPanel mainPane = new JPanel();
	JSplitPane sp1,sp2;
		JPanel roomInfoPane=new JPanel(new BorderLayout());
			JPanel roomTitlePane=new JPanel(new BorderLayout());
				JLabel titleLbl=new JLabel("룸 이용 정보 확인");
				JLabel timeLbl=new JLabel();
				
			JPanel roomStatePane=new JPanel(new GridLayout(3,3));
				JTextArea ta[] = new JTextArea[9];
		JPanel orderPane=new JPanel(new BorderLayout());
			JPanel orderTitlePane=new JPanel(new BorderLayout());
			
				JLabel orderTitleLbl=new JLabel("주문 이력(음식, 게임");
				JTable orderNumTable=new JTable();
				JScrollPane orderSp = new JScrollPane(orderNumTable); 
				String orderTitle[] = {"주문번호", "룸번호"};
				DefaultTableModel orderModel;
				
		JPanel callPane=new JPanel(new BorderLayout());
			JPanel callTitlePame=new JPanel(new BorderLayout());
				JLabel callTitleLbl=new JLabel("실시간 호출 목록");
				JTable callNumTable=new JTable();
				JScrollPane callSp = new JScrollPane(callNumTable); 
				String callTitle[] = {"룸번호", "유저아이디"};
				DefaultTableModel callModel;
				
		JPanel mainPane1 = new JPanel(new BorderLayout());
		ClockSet cl = new ClockSet();
		//RoomSetTh rsTh = new RoomSetTh();
		//NewOrderSetTh nOrdersTh = new NewOrderSetTh();
		//NewCallSetTh nCallsTh = new NewCallSetTh();
		String userId="";
		
		int callCount=0 , orderCount=0;
	public AdminMain() {  }
	public AdminMain(MainFrame mainFrm) {
		this.mainFrm = mainFrm;
		adminMainPane();
		getOrderList();
		getCallList();
		for (int i = 1; i <= ta.length; i++) {
			roomSet(i);
		}
		for (int i = 0; i < ta.length; i++) {
			ta[i].addMouseListener(this);
		}
		orderNumTable.addMouseListener(this);
		callNumTable.addMouseListener(this);
		
		
		Thread timeT = new Thread(cl); //시계 쓰레드
		timeT.start();
		/*
		 * // 룸 주문 받아오기 
		Thread roomRefreshT = new Thread(rsTh); //룸 새로고침 쓰레드 .. 
		roomRefreshT.start();
		Thread orderT = new Thread(nOrdersTh);
		orderT.start();
		Thread callT = new Thread(nCallsTh);
		callT.start();
		*/
	}
	@Override
	public void mouseClicked(MouseEvent me) {
		Object obj = me.getSource();
		if(obj == orderNumTable) {
			int row = orderNumTable.getSelectedRow(); 
			System.out.println(row);
			int ordernum = (int)orderNumTable.getValueAt(row, 0); //주문번호 
			int roomnum = (int)orderNumTable.getValueAt(row, 1);
			orderDetail(roomnum, ordernum);
			
			
		}else if(obj == callNumTable) {
			int row = callNumTable.getSelectedRow(); 
			int roomnum = (int)callNumTable.getValueAt(row, 0); //방번호
			String userId = (String)callNumTable.getValueAt(row, 1); //아이디 
			callComplete(userId, roomnum);
		}
		for (int i = 0; i < ta.length; i++) {
			if(ta[i]==obj) {
				new Detail_Info_Dialog(i+1);
			}
		}
	}
	public void orderDetail(int roomnum, int ordernum) {
		OrderGameDAO dao = new OrderGameDAO();
		OrderFoodDAO dao1 = new OrderFoodDAO();
		OrderDetailDAO dao2 = new OrderDetailDAO();
		ReservationDAO dao3 = new ReservationDAO();
		List<ReservationVO> lst = new ArrayList<ReservationVO>();
		ReservationVO vo = new ReservationVO();
		lst = dao3.getRoomInfo(roomnum);
		vo = lst.get(0);
		//대여 요청 인지확인 , 예약요청인지, 음식주문인지, 게임주문인지 확인하기. 
		int rentResult = dao.gameRentCheck(ordernum); //게임 대여요청인지 //오더넘버 사용해서 리절트값 있는지확인.  
		int reseveResult =dao.gameReserveCheck(ordernum); //예약인지 
		int foodOrderResult = dao1.foodOrderCheck(ordernum); //음식주문인지.
		int gameOrderResult = dao2.gameOrderCheck(ordernum); //게임구매 주문인지 
		if(rentResult>0) { //게임 대여요청일경우 
			String rentgame = dao.getRentOrReserveGame(ordernum, vo.getRoomReNum());
			String rent = "<게임 대여>";
			new Order_Dialog(roomnum ,ordernum, rentgame, rent); //유저아이디, 주문번호, 대여게임, 대여 
		}else if(reseveResult>0){ //게임예약 요청일경우 
			String reserveGame = dao.getRentOrReserveGame(ordernum, vo.getRoomReNum());
			String reserve = "<게임 예약>";
			new Order_Dialog(roomnum ,ordernum,reserveGame, reserve);
		}else if(foodOrderResult>0){ //음식주문일경우 
			//vo를 보내야함
			List<OrderDetailVO> lst2= dao2.getOrderFood(vo.getRoomReNum());
			String foodOrder = "<음식 주문>";
			new Order_Dialog(roomnum ,ordernum,lst2, foodOrder);
		}else if(gameOrderResult>0){ //게임구매 요청일 경우 
			//vo를 보내야함
			List<OrderDetailVO> lst2= dao2.getOrderGame(vo.getRoomReNum());
			String gameOrder = "<게임 구매>";
			new Order_Dialog(roomnum, ordernum,lst2, gameOrder);
		}
	}
	public void callComplete(String id, int roomnum) {
		ReservationDAO reDao = new ReservationDAO();
		int roomrenum = reDao.getRoomrenum(id);
		int i = JOptionPane.showConfirmDialog(this, roomnum +"번방 호출에 대한 건 완료시 'OK'클릭","호출완료 여부 확인",0);
		if (i==0) {
			CallDAO dao = new CallDAO();
			int result = dao.UpdateCall(roomrenum);
		}
	}
	
	public void getCallList() {
		CallDAO dao = new CallDAO();
		List<CallVO> lst= dao.getCallList();
		
		callModel.setRowCount(0);
		for (int i = 0; i < lst.size(); i++) {
			CallVO vo = lst.get(i);
			Object[] calllist = {vo.getRoomnum(), vo.getId()};
			
			callModel.addRow(calllist);
		}
	}
	
	
	public void getOrderList() {
		OrderDetailDAO dao = new OrderDetailDAO();
		List<OrderDetailVO> lst = dao.getOrderNum();
		
		orderModel.setRowCount(0);
		for (int i = 0; i < lst.size(); i++) {
			OrderDetailVO vo = lst.get(i);
			
			Object[] orderlist = {vo.getOrdernum(), vo.getRoomnum()};
			
			orderModel.addRow(orderlist);
		}
	}
	
	public void roomSet(int roomnum) {
		//1 현재시간을 이용해서  사용중인 룸을 알아내기 룸예약번호. // 룸결제비용같이 받기
		ReservationDAO dao = new ReservationDAO();
		List<ReservationVO> lst = new ArrayList<ReservationVO>();
		lst = dao.getRoomInfo(roomnum);
		ReservationVO reVO = new ReservationVO();
		if(lst.size()>0) { //어짜피 1개임  1개여야함. 
			reVO = lst.get(0);
			//userId = reVO.getId();
			String outTime = setOutTime(reVO.getOutTime());
			//2 게임 주문 금액받아오기 (합계) 
			int gamePrice = 0;
			gamePrice =orderGamePriceGet(reVO.getRoomReNum());  
			//3 음식 주문 금액 받아오기 (합계)
			int foodPrice = 0;
			foodPrice=	orderFoodPriceGet(reVO.getRoomReNum());  
			//4 현재 플레이중인 게임 이름 받아오기 
			String nowGame ="";
			nowGame= nowGameGet(reVO.getRoomReNum());
			//5 현재 예약중인 게임 이름 받아오기 
			String reserveGame="";
			reserveGame= reserveGameGet(reVO.getRoomReNum());
			DecimalFormat dFmt = new DecimalFormat("#,###원");
			int totalPrice = reVO.getPay() +  gamePrice + foodPrice;
			
			//6 다음 사용자 확인
			ReservationDAO dao1 = new ReservationDAO();
			List<ReservationVO> nextReLst = new ArrayList<ReservationVO>();
			nextReLst =  dao1.getNextReserveRoomInfo(roomnum);
			ReservationVO nextReVo = new ReservationVO();
				if(nextReLst.size()>0) { //다음예약있습니다~
					nextReVo = nextReLst.get(0);
					if(reVO.getPayCheck().equals("Y")) { //결제는 완료되었다면  (퇴실했다)
						ta[roomnum-1].setText(roomnum+"번룸 [사용중] "+ reVO.getId() +"\n입실시간 = "+ reVO.getInTime()+"\n퇴실시간 = "+outTime
								+"\n플레이 = \n예약중 = \n"+ "결제 완료 : "
								+"\n\n[룸예약] : "+nextReVo.getId() +"\n"+ nextReVo.getInTime()+ " ~ "+setOutTime(nextReVo.getOutTime()) );
					}else if(reVO.getPayCheck().equals("N")) { //결제완료는 안했다면 
						ta[roomnum-1].setText(roomnum+"번룸 [사용중] "+ reVO.getId() +"\n입실시간 = "+ reVO.getInTime()+"\n퇴실시간 = "+outTime
								+"\n플레이 = "+nowGame+"\n예약중 = "+reserveGame+"\n"+ "결제 예정 금액 : "+dFmt.format(totalPrice) 
								+"\n\n[룸예약] : "+nextReVo.getId() +"\n"+ nextReVo.getInTime()+ " ~ "+setOutTime(nextReVo.getOutTime()) );
					}
				}else {
					//다음예약 없습니다~
					if(reVO.getPayCheck().equals("Y")) { //결제는 완료되었다면 (퇴실했다)
						ta[roomnum-1].setText(roomnum+"번룸 [사용중] "+ reVO.getId() +"\n입실시간 = "+ reVO.getInTime()+"\n퇴실시간 = "+outTime
								+"\n플레이 = \n예약중 = \n"+ "결제 완료 \n\n[룸예약] : " );
					}else if(reVO.getPayCheck().equals("N")) { 
						ta[roomnum-1].setText(roomnum+"번룸 [사용중] "+ reVO.getId() +"\n입실시간 = "+ reVO.getInTime()+"\n퇴실시간 = "+outTime
								+"\n플레이 = "+nowGame+"\n예약중 = "+reserveGame+"\n"+ "결제 예정 금액 : "+dFmt.format(totalPrice) +"\n\n[룸예약] : " );
					}
				}
		}else {
			// 사용중이아니면 [미사용]
			// 다음 사용자 확인
			ReservationDAO dao1 = new ReservationDAO();
			List<ReservationVO> nextReLst = new ArrayList<ReservationVO>();
			nextReLst =  dao1.getNextReserveRoomInfo(roomnum);
			ReservationVO nextReVo = new ReservationVO();
			if(nextReLst.size()>0) { //다음예약있습니다~
				nextReVo = nextReLst.get(0);
				ta[roomnum-1].setText(roomnum+"번룸 [미사용]\n\n\n\n\n\n\n[룸예약] : "+nextReVo.getId() +"\n"+ nextReVo.getInTime()+ " ~ "+setOutTime(nextReVo.getOutTime()) );
			}else {
				ta[roomnum-1].setText(roomnum+"번룸 [미사용]\n\n\n\n\n\n\n[룸예약] : ");
			}
		}	
	}
	public static String setOutTime(String outTime) {
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
	public static String nowGameGet(int roomrenum) { 
		//플레이중인 게임
		OrderGameDAO dao = new OrderGameDAO();
		String nowplayGame = dao.getNowGame(roomrenum);
		return nowplayGame;
	}
	public static String reserveGameGet(int roomrenum) { 
	//예약중인 게임,  
		OrderGameDAO dao = new OrderGameDAO();
		String reserveGame = dao.getReserveGame(roomrenum);
		return reserveGame;
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
	public void adminMainPane() {
		mainPane.setLayout(null);
		setLayout(null);
		// 위쪽 패널
		sp2=new JSplitPane(JSplitPane.VERTICAL_SPLIT, orderPane, callPane);
		sp2.setDividerLocation(300);
		orderPane.add("North",orderTitleLbl);
		orderModel = new DefaultTableModel(orderTitle, 0);
		orderNumTable = new JTable(orderModel);
		orderSp=new JScrollPane(orderNumTable);
		orderPane.add(orderSp);
		// 아래쪽 패널
		callPane.add("North",callTitleLbl);
		callModel = new DefaultTableModel(callTitle, 0);
		callNumTable = new JTable(callModel);
		callSp=new JScrollPane(callNumTable);
		
		callPane.add(callSp);
		
		// sp1 왼쪽 패널
		sp1=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,roomInfoPane,sp2);
		sp1.setDividerLocation(500);
		sp1.setBounds(0, 0, 680, 530);
		// 룸 타이틀
		roomTitlePane.add("West", titleLbl);
		roomTitlePane.add("East", timeLbl);
		
		roomInfoPane.add(roomTitlePane);
		// 룸 바둑판
		for (int i = 0; i < ta.length; i++) {
			ta[i] = new JTextArea();
			roomStatePane.add(ta[i]);
			ta[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			ta[i].setEditable(false);
		}
		
		roomInfoPane.add("North", roomTitlePane);
		roomInfoPane.add(roomStatePane);
		roomInfoPane.setBounds(0, 0, 550, 550);

		mainPane.add(sp1);
		mainPane.setBounds(0, 0, 700, 600);
		add(mainPane);
		
		setSize(700,600);
		setVisible(true);
	}
	/*
	class NewCallSetTh implements Runnable{
		public NewCallSetTh() {	
		}
		@Override
		public synchronized void run() {
			while(true) {
				CallDAO dao = new CallDAO();
				List<CallVO> lst= dao.getCallList();
				
				callModel.setRowCount(0);
				for (int i = 0; i < lst.size(); i++) {
					CallVO vo = lst.get(i);
					Object[] calllist = {vo.getRoomnum(), vo.getId()};
					
					callModel.addRow(calllist);
				}
				try { Thread.sleep(5000); }catch(Exception e) {}
			}
		}
	}
	*/
	/*
	class NewOrderSetTh implements Runnable{
		public NewOrderSetTh() {
		}
		@Override
		public synchronized void run() {
			while(true) {
				OrderDetailDAO dao = new OrderDetailDAO();
				List<OrderDetailVO> lst = dao.getOrderNum();
				orderModel.setRowCount(0);
				for (int i = 0; i < lst.size(); i++) {
					OrderDetailVO vo = lst.get(i);
					Object[] orderlist = {vo.getOrdernum(), vo.getRoomnum()};
					orderModel.addRow(orderlist);
				}
				try { Thread.sleep(5310); }catch(Exception e) {}
			}
		}
	}
	*/
	class ClockSet extends JLabel implements Runnable{
		public ClockSet() {
			//roomTitlePane.add("East", timeLbl);
		}
		@Override
		public synchronized void run() {
			while(true) {
				Calendar now = Calendar.getInstance();
				SimpleDateFormat nowFmt = new SimpleDateFormat("YYYY-MM-dd  HH:mm:ss");
				String nowStr = nowFmt.format(now.getTime());
				timeLbl.setText(nowStr);
				try { Thread.sleep(1102); }catch(Exception e) {}
			}
			
		}
	}
	/*
	class RoomSetTh implements Runnable{
		public RoomSetTh() {
			
		}
		@Override
		public synchronized void run() {
			while(true) {
				for (int i = 1; i <= ta.length; i++) {
					roomSet(i);
				}
				try { Thread.sleep(11345); }catch(Exception e) {}
			}
		}
		
	}
	*/
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
