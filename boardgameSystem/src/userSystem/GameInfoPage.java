package userSystem;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import vo.CallDAO;
import vo.GameDAO;
import vo.GameVO;
import vo.OrderGameVO;
import vo.OrderGameDAO;

public class GameInfoPage extends JPanel implements ActionListener{
	Test test;
	
	JPanel askPane = new JPanel();
	JButton backBtn = new JButton("◀ 뒤로");
	JButton askBtn = new JButton("관리자 호출");
	
	String gamename, playtime, imgName;
	int rentstock, sellstock, minpeople, maxpeople, gameprice, ordernum;
	
	Font fnt=new Font("한컴 고딕",Font.PLAIN,15);
	
	JPanel infoPane = new JPanel();
	JLabel infoNameLbl;
	
	JPanel infoDetailLbl = new JPanel();
		JLabel infoCountLbl;
		JLabel infoTimeLbl;
		
		JPanel imgPane=new JPanel(new BorderLayout());
		MyCanvas mc = new MyCanvas(); 
		ImageIcon gameImg;
		JLabel imgLbl;
		
		JLabel infoRuleTitle;
		JLabel infoRuleLbl;
		JLabel infoPrice;
		JScrollPane infoSp = new JScrollPane(infoDetailLbl);
		JPanel rulePane = new JPanel(new BorderLayout());
		JScrollPane ruleSp = new JScrollPane(rulePane);
		
	JPanel gameBtnPane = new JPanel();
		JButton nowBtn = new JButton("지금 플레이");
		JButton rentBtn = new JButton("예약하기");
		JButton buyBtn = new JButton("구매하기");
	
	String id = "", btnCheck = "";
	public GameInfoPage(Test test, String userId, String gameName, String btnCheck) { // GameList페이지에서 넘겨받은 게임명이 매개변수로 들어간다
		this.test=test;
		id = userId;
		this.btnCheck = btnCheck;
		//상단 관리자 문의 
		askPane.setLayout(null);	
		askPane.setBackground(Color.white);
		askPane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
			backBtn.setBackground(Color.LIGHT_GRAY);
			backBtn.setBounds(10, 10, 100, 40); askPane.add(backBtn);
			askBtn.setBackground(Color.LIGHT_GRAY);
			askBtn.setBounds(330, 10, 100, 40);	askPane.add(askBtn); if(btnCheck.equals("1")) {askBtn.setEnabled(false);} else if(btnCheck.equals("2")) { askBtn.setEnabled(true); }
		askPane.setBounds(0, 0, 460, 60);
					
		add(askPane);
				
		GameDAO dao=new GameDAO();
		GameVO vo=dao.GameInfoSelect(gameName);
		
		// 변수에 db 데이터 저장
		gamename=vo.getGamename();
		rentstock=vo.getRentstock();
		sellstock=vo.getSellstock();
		minpeople=vo.getMinpeople();
		maxpeople=vo.getMaxpeople();
		playtime=vo.getPlaytime();
		gameprice=vo.getGameprice();
		
		// 이미지 띄우기
	//	imgSwitch(gamename);
		
		// 라벨 정의
		infoNameLbl=new JLabel(gamename);
		infoCountLbl=new JLabel("게임 인원 : "+minpeople+"~"+maxpeople+"명");
		infoTimeLbl=new JLabel("게임 플레이 타임 : "+playtime);
		ruleSwitch(gamename);
		infoPrice=new JLabel("게임 판매 금액 : "+gameprice+"원");
		
		//정보 
		infoPane.setLayout(null);
		infoPane.setBounds(0, 60, 460, 620);
		infoPane.setBackground(Color.white);
			infoNameLbl.setBounds(20, 20, 300, 40);
			infoNameLbl.setFont(new Font("함초롬바탕", Font.BOLD, 30)); infoPane.add(infoNameLbl);
			
		infoSp.setLayout(null);
		infoSp.setBackground(Color.white);
		infoSp.setBounds(5, 80, 428, 565); infoPane.add(infoSp);
		
		infoDetailLbl.setLayout(null);
		infoDetailLbl.setBackground(Color.white);
		infoDetailLbl.setBounds(0,0,428,560); infoSp.add(infoDetailLbl);
			imgPane.setBounds(8,10,160,162); infoDetailLbl.add(imgPane); imgPane.setBorder(new LineBorder(Color.WHITE));
			imgPane.add(mc);
		//	imgLbl=new JLabel(); imgLbl.setOpaque(true);
		//	imgLbl.setBounds(0,0,160,160); //imgPane.add(imgLbl);
		//	imgPane.add(gameImg);
			infoCountLbl.setBounds(8,190,300,22); infoDetailLbl.add(infoCountLbl); infoCountLbl.setFont(fnt);
			infoTimeLbl.setBounds(8,220,300,22); infoDetailLbl.add(infoTimeLbl); infoTimeLbl.setFont(fnt);
			
			infoRuleTitle.setBounds(8,250,300,22); infoDetailLbl.add(infoRuleTitle); infoRuleTitle.setFont(fnt);
			
			
			
			ruleSp.setBounds(8,280,404,182);infoDetailLbl.add(ruleSp); ruleSp.setBorder(new LineBorder(Color.white));
			
			
			rulePane.setBounds(0,0,400,182);rulePane.setBackground(Color.WHITE);
			infoRuleLbl.setBounds(8,0,500,182); rulePane.add(infoRuleLbl); infoRuleLbl.setFont(fnt);
			infoPrice.setBounds(8,470,300,22); infoDetailLbl.add(infoPrice); infoPrice.setFont(new Font("한컴 고딕",Font.BOLD,17));
			
		//게임 버튼
		gameBtnPane.setLayout(null);
		gameBtnPane.setBackground(Color.white); 
		gameBtnPane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
		gameBtnPane.setBounds(0, 680, 460, 145);
			nowBtn.setBounds(20,22,120,40); gameBtnPane.add(nowBtn);
				if(btnCheck.equals("1")) {nowBtn.setEnabled(false);} // btnCheck가 1이면 지금 플레이 버튼 비활성화
				if(rentstock==0) {nowBtn.setEnabled(false);} // 대여 수량이 0이면 지금 플레이 버튼 비활성화
				nowBtn.setBorder(new LineBorder(Color.white));
			rentBtn.setBounds(160,22,120,40); gameBtnPane.add(rentBtn);
				if(btnCheck.equals("1")) {rentBtn.setEnabled(false);} // btnCheck가 1이면 대여 버튼 비활성화
				if(rentstock!=0) {rentBtn.setEnabled(false);} // 대여 수량이 0이 아니면 대여 버튼 비활성화
				rentBtn.setBorder(new LineBorder(Color.white));
			buyBtn.setBounds(300,22,120,40); gameBtnPane.add(buyBtn); 
				if(btnCheck.equals("1")) {buyBtn.setEnabled(false);} // btnCheck가 1이면 구매 버튼 비활성화 
				if(sellstock==0) {buyBtn.setEnabled(false);}
				buyBtn.setBorder(new LineBorder(Color.white));
		
		add(infoPane); add(gameBtnPane);
		
	//	setResizable(false); //창크기 고정
		setLayout(null);		
		setVisible(true);
		revalidate();
		setSize(460, 763);
	//	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		nowBtn.addActionListener(this);
		rentBtn.addActionListener(this);
		buyBtn.addActionListener(this);
		backBtn.addActionListener(this);
		askBtn.addActionListener(this);
		
	}
	
	// 버튼 이벤트
	public void actionPerformed(ActionEvent ae) {
		String eventBtn=ae.getActionCommand();
		if(eventBtn.equals("지금 플레이")) {
			if(test.nowgame.equals("")) { // 게임을 처음 빌릴 경우
				test.nowgame=gamename;
				test.nowstock=rentstock;
				if(test.nowstock==0) {
					nowBtn.setEnabled(false);
				}
				int result=JOptionPane.showConfirmDialog(this, "["+test.nowgame+"]을 플레이하시겠습니까?", "플레이할 게임 : "+test.nowgame, JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION) { // 지금 플레이 확인버튼 눌렀을 경우
					
					JOptionPane.showMessageDialog(this, "["+test.nowgame+"]\n룸으로 가져다 드리겠습니다.","플레이할 게임 : "+test.nowgame,JOptionPane.INFORMATION_MESSAGE);
					InsertOrderNum(); // 주문 번호 생성
					insertGame(test.nowgame); // 대여 게임 상세정보 생성
					setRentUpdate(gamename); // 대여 게임 수량 갱신(수량-1)
					// 지금 플레이, 대여버튼 활성/비활성화
					if(rentstock==0) { // 대여 수량이 없을 경우 (지금 플레이:비활성화/대여버튼:활성화)
						nowBtn.setEnabled(false);
						rentBtn.setEnabled(true);
					}else if(rentstock>0) {
						nowBtn.setEnabled(true);
						rentBtn.setEnabled(false);
						
					}else {
						System.out.println("판매용 수량 오류 발생");
					}
				}else { // 지금플레이에서 나갔을 경우
					test.nowgame="";
					test.nowstock=0;
				}
			}else if(test.nowgame.equals(gamename)) { // 현재 플레이하고 있는 게임을 또 플레이하려할 경우
				JOptionPane.showMessageDialog(this, "현재 플레이하고 계신 게임과 같은 게임입니다.","중복 발생",JOptionPane.INFORMATION_MESSAGE);
			}else if(!test.nowgame.equals(gamename)) { // 게임을 바꾸는 경우
				test.backgame=test.nowgame; // 사용한 게임을 사용했던 게임으로 변경
				test.backstock=test.nowstock; // 사용한 게임 수량을 사용했던 게임 수량으로 변경
				
				test.nowgame=gamename; // 사용하고 싶은 게임을 사용할 게임으로 변경
				test.nowstock=rentstock; // 사용하고 싶은 게임의 수량을 사용할 게임의 수량으로 변경
				
				int result=JOptionPane.showConfirmDialog(this, "["+test.backgame+"]은 반납하고\n["+test.nowgame+"]로 바꾸시겠습니까?","대여할 게임 : "+test.nowgame,JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION) { // 게임을 바꾸려는 경우
					updateGame(test.backgame); // 이전 게임을 바꾸기
					setReturnUpdate(test.backgame); // 이전 게임 반납(수량+1)
					
					InsertOrderNum(); // 주문 번호 생성
					insertGame(test.nowgame); // 대여 게임 상세정보 생성
					setRentUpdate(gamename); // 대여 게임 수량 갱신(수량-1)
					
					JOptionPane.showMessageDialog(this, "["+test.nowgame+""
							+ "]\n룸으로 가져다 드리겠습니다.","플레이할 게임 : "+test.nowgame,JOptionPane.INFORMATION_MESSAGE);
					
				}else { // 지금 플레이 취소 시에 사용하고 있는 게임을 사용했던 게임으로 넣어놨기 때문에 되돌려주는 과정
					test.nowgame=test.backgame;
					test.nowstock=test.backstock;
					test.backgame="";
					test.backstock=0;
				}
			}
				
		}else if(eventBtn.equals("예약하기")) { // 사용하고싶은 게임의 수량이 없을 때 대여버튼 활성화
		//	JOptionPane.showMessageDialog(this, "["+gamename+"]"+"을 예약하시겠습니까?", "예약하기 : "+gamename,JOptionPane.INFORMATION_MESSAGE);
			int result=JOptionPane.showConfirmDialog(this, "["+gamename+"]"+"을 예약하시겠습니까?", "예약하기 : "+gamename,JOptionPane.YES_NO_OPTION);
			if(result==JOptionPane.YES_OPTION) { // 예약하기 확인 버튼을 눌렀을 때
				InsertOrderNum(); // 주문 번호 생성
				reserveGame(gamename); // 대여 게임 상세정보 생성
				JOptionPane.showMessageDialog(this, "["+gamename+"]이 예약되었습니다.","예약하기 : "+gamename, JOptionPane.INFORMATION_MESSAGE);
			}
		}else if(eventBtn.equals("구매하기")) {
		//	JOptionPane.showMessageDialog(this, "["+gamename+"]\n룸으로 가져다 드리겠습니다", "구매하기 : "+gamename,JOptionPane.INFORMATION_MESSAGE);
			int result=JOptionPane.showConfirmDialog(this, "["+gamename+"]을 구매하시겠습니까?", "구매하기 : "+gamename,JOptionPane.YES_NO_OPTION);
			if(result==JOptionPane.YES_OPTION) { // 구매하기 확인 버튼을 누를 때
				JOptionPane.showMessageDialog(this, "퇴실하실 때 ["+gamename+"]를 드리겠습니다.","구매하기 : "+gamename,JOptionPane.INFORMATION_MESSAGE);
				InsertOrderNum();
				buyGame(gamename);
				setSellUpdate(gamename); // 선택한 게임을 구매할 때 판매용 재고 1 감소
			}
		}else if(eventBtn.equals("◀ 뒤로")) {
			test.change("gameList", id, btnCheck);
		}else if(eventBtn.equals("관리자 호출")) {
			adminCall();
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
	
	public void InsertOrderNum() { // 주문 번호 생성
		System.out.println(test.roomrenum);
		OrderGameDAO dao = new OrderGameDAO();
		
		int result=dao.InsertOrder(test.roomrenum);
		
		if(result>0) {
			List<OrderGameVO> lst=dao.CheckOrder(test.roomrenum);
			
			OrderGameVO vo=new OrderGameVO();
			vo=lst.get(0);
			ordernum=vo.getOrdernum();
		}
	}
	public void insertGame(String gamename) { // 대여 게임 상세정보 생성(대여)
		OrderGameDAO dao=new OrderGameDAO();
		
		int result=dao.InsertRentGame(ordernum,test.roomrenum, gamename);
	}
	public void updateGame(String gamename) { // 반납 게임 상세정보 변경(대여->반납)
		OrderGameDAO dao=new OrderGameDAO();
		
		int result=dao.UpdateRentGame(ordernum,test.roomrenum, gamename);
	}
	
	public void reserveGame(String gamename) { // 예약 게임 상세정보 생성(예약)
		OrderGameDAO dao=new OrderGameDAO();
		
		int result=dao.InsertReserveGame(ordernum,test.roomrenum, gamename);
		test.resergame=gamename;
	}
	
	public void buyGame(String gamename) {
		OrderGameDAO dao=new OrderGameDAO();
		
		int result=dao.InsertBuyGame(ordernum, test.roomrenum, gamename);
	}
	
	// 구매하기 버튼 클릭 시 판매용재고 -1
	public void setSellUpdate(String gamename) { // 구매할 때 수량 변경(-1)
		GameVO vo=new GameVO();
		vo.setSellstock(sellstock-1);
		vo.setGamename(gamename);
		
		GameDAO dao=new GameDAO();
		int result=dao.SellUpdate(vo);
		
		sellstock=vo.getSellstock();
		
		if(result>0) { // 수정이 된 경우
			if(sellstock==0) {
				buyBtn.setEnabled(false);
			}else if(sellstock>0) {
				buyBtn.setEnabled(true);
				
			}else {
				System.out.println("판매용 수량 오류 발생");
			}
		}else {
			System.out.println("구매 실행 오류");
		}
	}
		
	// 지금 플레이 시 rentstock-1
	public void setRentUpdate(String gamename) { // 대여할 때 수량 변경(-1)
		GameVO vo=new GameVO();
		vo.setRentstock(rentstock-1);
		vo.setGamename(gamename);
		
		GameDAO dao=new GameDAO();
		int result=dao.RentUpdate(vo);
		
		if(result>0) { // 수정이 된 경우
			rentstock=vo.getRentstock();
			test.backstock=rentstock;
		}else {
			System.out.println("대여 실패");
		}
	}
	
	// 반납하는 경우 rentstock+1
	public void setReturnUpdate(String gamename) { // 반납할 때 수량 변경(+1)
		GameVO vo=new GameVO();
		vo.setRentstock(test.backstock);
		vo.setGamename(gamename);
		
		GameDAO dao=new GameDAO();
		int result=dao.RentUpdate(vo);
		
		if(result>0) { // 수정이 된 경우
			test.backstock=vo.getRentstock();
			
			if(rentstock==0) {
				nowBtn.setEnabled(false);
				rentBtn.setEnabled(true);
		//	}else if(rentstock>0) {
			}else {
				nowBtn.setEnabled(true);
				rentBtn.setEnabled(false);
			}
		}else {
			System.out.println("반납 실패");
		}
	}
	
	class MyCanvas extends Canvas{
		public void paint(Graphics g) {
			GameDAO dao=new GameDAO();
			byte[] bytes=dao.getpicDB(gamename);
			
			try {
				if(bytes!=null) {
					BufferedImage img2 = ImageIO.read(new ByteArrayInputStream(bytes)); 	 
					int w = img2.getWidth(this);
				 	int h = img2.getHeight(this);
					if(img2!=null) {       						 
			        	g.drawImage(img2, 0,0,160,160, 0,0,w,h,this);
					}
				}
			} catch (Exception e) {
				    e.printStackTrace();
			}
		}
	}
	
	public void ruleSwitch(String gamename) {
		if(gamename.equals("할리갈리")) {
			gamename="&nbsp;&nbsp;1 인원수에 맞게 카드를 나눠줍니다.<br>"
					+ "&nbsp;&nbsp;2 돌아가면서 자기차례에는 카드를 한 장씩 뒤집습니다.<br>"
					+ "&nbsp;&nbsp;3 같은 과일 다섯개가 모이면 가장 먼저 종을 칩니다.<br>"
					+ "&nbsp;&nbsp;4 만약 과일 5개가 되지않았는데 종을 친 경우,<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;나머지 플레이어들에게 자신의 카드를 한 장씩 나눠줍니다.<br>"
					+ "&nbsp;&nbsp;5 카드를 더 많이 가지고 있는 사람이 승리합니다.<br><br><br><br>";
		}else if(gamename.equals("펭귄 얼음깨기")) {
			gamename="&nbsp;&nbsp;1 얼음판을 조립해 게임을 준비하세요.<br>"
					+ "&nbsp;&nbsp;2 펭귄을 올려놓고 게임을 시작해요.<br>"
					+ "&nbsp;&nbsp;3 룰렛을 돌리고 망치로 얼음 블록을 제거해요.<br>"
					+ "&nbsp;&nbsp;4 펭귄을 떨어뜨린 사람이 패배해요.<br><br><br><br><br><br>";
		}else if(gamename.equals("루미큐브")) {
			gamename="&nbsp;&nbsp;1 숫자 타일을 잘 섞은 다음,14개씩 타일을 가져갑니다.<br>"
					+ "&nbsp;&nbsp;2 총합이 30이상인 타일을 등록합니다.<br>\r\n"
					+ "&nbsp;&nbsp;3 같은 색깔로 연속되는 숫자 3개 이상이 되도록<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;내려놓거나, 다른 색깔의 같은 숫자<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3개 이상이 되도록 내려놓아야 합니다.<br>"
					+ "&nbsp;&nbsp;4 타일을 하나씩 가져옵니다.<br>"
					+ "&nbsp;&nbsp;5타일을 모두 사용했다면, 그 플레이어가 즉시 승리합니다.<br><br><br>";
		}else if(gamename.equals("사보타지")) {
			gamename="&nbsp;&nbsp;1 광부,방해꾼카드를 섞어 플레이어마다 1장씩 나눠줍니다.<br>"
					+ "&nbsp;&nbsp;2 굴카드는 길을 잇는데 사용합니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;이전에 놓은 굴카드와 이어지도록 놓아야 합니다.<br>"
					+ "&nbsp;&nbsp;3 행동카드는 자기자신이나 다른 플레이어에게 사용합니다.<br>"
					+ "&nbsp;&nbsp;4 만약 카드를 쓸 수 없는 상황이라면 카드를 버립니다.<br>"
					+ "&nbsp;&nbsp;5 출발지부터 목적지까지 길을 이었다면 카드를 엽니다.<br>"
					+ "&nbsp;&nbsp;6 금덩이가 있다면 광부들이 승리,<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;금덩이에 도달하지 못했다면 방해꾼들이 승리합니다.<br><br>";
		}else if(gamename.equals("슈퍼 라이노")) {
			gamename="&nbsp;&nbsp;1 바닥 카드를 내려놓고, 지붕카드를 섞어 나눠 가집니다.<br>"
					+ "&nbsp;&nbsp;2 바닥 카드에 놓인 표시대로 지붕카드를 한 장씩 올립니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;그 위에 라이노 인형을 놓습니다.<br>"
					+ "&nbsp;&nbsp;3 카드를 무너뜨린 사람은 게임에서 집니다.<br><br><br><br><br><br>";
		}else if(gamename.equals("13클루")) {
			gamename="&nbsp;&nbsp;1 단서카드를 인물,장소,흉기를 잘 섞은 후<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;더미당 1장씩 총 3장을 나눕니다.<br>"
					+ "&nbsp;&nbsp;2 추가로 2장을 더 받은 후, 장소,흉기,인물 카드를<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;각각 1장씩 가림막에 올려 둡니다.<br>"
					+ "&nbsp;&nbsp;3 각 플레이어들은  자신 앞에 있는 3장의 정체를 맞춥니다.<br>"
					+ "&nbsp;&nbsp;4 돋보기 토큰을 하나 가져오고 행동을 합니다.<br>"
					+ "&nbsp;&nbsp;5 다른 플레이어에게 질문을 하고 정보를 얻습니다.<br>"
					+ "&nbsp;&nbsp;6 공통 단서 카드로 공통 정보를 수집합니다.<br>"
					+ "&nbsp;&nbsp;7 사건을 추리합니다.<br>";
		}else if(gamename.equals("오딘을 위하여")) {
			gamename="&nbsp;&nbsp;1 자원을 모으고, 집과 배를 만들고,<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;먹을 것과 재산을 확보합니다.<br><br><br><br><br><br><br><br>";
		}else if(gamename.equals("아줄")) {
			gamename="&nbsp;&nbsp;1 한가지 색의 타일을 여러 개 가져오거나,<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;다른 색 타일을 한 개 가져옵니다.<br>"
					+ "&nbsp;&nbsp;2 맞는 색 타일을 그 칸에 적힌 수만큼 사용해야 합니다.<br>"
					+ "&nbsp;&nbsp;3 장식한 타일이 다른 타일과 많이 이어져 있을수록<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;더 높은 점수를 얻습니다.<br>"
					+ "&nbsp;&nbsp;4 매 라운드마다 한 색이 조커가 됩니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;조커타일은 다른 색깔 타일을 대신해서 사용할 수 있습니다.<br>"
					+ "&nbsp;&nbsp;5 특정 칸을 타일로 감싸면 보너스로 추가 타일을 가져옵니다.<br>"
					+ "&nbsp;&nbsp;6 남은 타일은 보관했다가 다음 라운드에서 사용하세요.";
		}else if(gamename.equals("넘버9")) {
			gamename="&nbsp;&nbsp;1 가장 아래쪽에 놓인 타일은 0점이지만<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2층,3층으로 올라가면서 2배 3배가 됩니다.<br>"
					+ "&nbsp;&nbsp;2 플레이어는 카드를 한 장 가져와 자기 앞에 놓습니다.<br>"
					+ "&nbsp;&nbsp;3 타일을 놓을 땐 같은 층에 있는 다른 타일에<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;한 칸이라고 인접하게 놓아야 합니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;타일 아래에 빈칸이 있으면 안됩니다.<br>"
					+ "&nbsp;&nbsp;4 새 타일을 위로 쌓을 때는 항상 그 아래층<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;타일 2장 이상에 걸쳐 놓아야 합니다.<br>";
		}else if(gamename.equals("로스트 시티")) {
			gamename="&nbsp;&nbsp;1 각자 8장씩 카드를 나눠 받아요<br>"
					+ "&nbsp;&nbsp;2 손에 든 카드를 색이 맞는 탐험지에 놓거나<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;색깔이 맞는 탐험지에 버려요.<br>"
					+ "&nbsp;&nbsp;3 기존 탐험지를 연장할 때는 먼저 놓인 카드보다<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;숫자가 더 큰 카드만 놓을 수 있습니다.<br>"
					+ "&nbsp;&nbsp;4 탐험지에 투자 카드가 1장이면 점수가 2배,<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2장이면 점수가 3배, 3장이면 점수가 4배입니다.<br>"
					+ "&nbsp;&nbsp;5 카드더미가 바닥이 나면 게임은 종료가 됩니다.<br>"
					+ "&nbsp;&nbsp;6 탐험지가 아예 없는 탐험지는 탐험 비용을 빼지 않고<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;그냥 0점 처리합니다.";
		}else if(gamename.equals("스플렌더")) {
			gamename="&nbsp;&nbsp;1 [보석 가져가기]<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;자신의 차례에는 보석을 선택해서 가져갈 수 있습니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;각각 다른 종류의 보석 3개를 가져가거나,<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4개 이상 남아있는 한 종류의 보석을<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2개 가져갈 수 있습니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;보석은 최대 10개 까지만 소유 가능합니다.<br>"
					+ "&nbsp;&nbsp;2 [카드 가져오기]<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;보석을 지불하고 카드를 구입할 수 있습니다.<br>\r\n"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;자신이 이미 가지고 있는 개발 카드가 있다면 그곳에<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;그려져 있는 보석만큼 추가 할인을 받을 수 있습니다.<br>"
					+ "&nbsp;&nbsp;3 미래에 구입하고자 하는 카드를 미리 보관할 수 있습니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;이 행동을 할 때 황금 토큰을 하나 가져올 수 있습니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;황금 토큰은 어떤 보석으로도 쓰일 수 있습니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;보관은 최대 3장 까지만 가능합니다.<br>"
					+ "&nbsp;&nbsp;4 자신의 차례가 끝나고 귀족타일 조건을 만족시켰다면,<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;즉시 귀족타일을 가져옵니다.<br>"
					+ "&nbsp;&nbsp;5 점수를 계산하여 점수가 15점을 넘었다면<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;게임이 종료됩니다.";
		}else if(gamename.equals("시타델")) {
			gamename="&nbsp;&nbsp;1 각자 금화2개와 카드4장을 가져갑니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;직업카드 중 번호별로 1장씩 고릅니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;그 중 원하는 번호 카드 8장을 골라 게임에서<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;사용합니다. 다만 4번카드는 반드시 사용합니다.<br>"
					+ "&nbsp;&nbsp;2 직업카드를 고릅니다.<br>"
					+ "&nbsp;&nbsp;3 왕관주인이 1번부터 호출합니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;해당하는 번호의 직업을 가진 플레이어가<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;행동을 시작합니다.<br>"
					+ "&nbsp;&nbsp;4 금화를 가져오거나 ,건물 카드 중<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1장을 가져오는 행동을 합니다.<br>"
					+ "&nbsp;&nbsp;5 금화를 지불하고 자신의 손에 있는 건물 카드를<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1장 골라 지을 수 있습니다.<br>"
					+ "&nbsp;&nbsp;6 자신이 고른 캐릭터 능력을 잘 보고,<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;적절히 잘 사용할 수 있습니다.<br>"
					+ "&nbsp;&nbsp;7 라운드를 종료하고 다음 라운드를 진행합니다.<br>"
					+ "&nbsp;&nbsp;8 누군가가 건물을 건설하면 게임은 종료됩니다.";
		}else if(gamename.equals("카르카손")) {
			gamename="&nbsp;1 말과 점수판을 준비합니다.<br>\r\n"
					+ "&nbsp;2 자기 차례에는 타일 하나를 뒤집어 기존의 타일에<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;붙입니다.단 타일을 붙일 때는 길, 성 등의 그림이<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;연결되도록 놓아야 합니다. 그 다음 자신의 말을<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;놓거나 안 놓거나 선택할 수 있습니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;말의 개수는 제한이 있기 때문에 신중하게 놓아야 합니다.<br>"
					+ "&nbsp;&nbsp;3 다른 플레이어가 이미 말을 놓은 성과<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;길에 타일을 붙일 때는 자신의 말을 놓을 수 없습니다. <br>"
					+ "&nbsp;&nbsp;4 타일을 놓았을 때 자신의 말을 놓았던 성이나 길이<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;완성되면 즉시 자신의 말을 빼면서 점수를 받습니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;다만 들판에 놓인 말은 게임 종료까지 타일 위에 둡니다.<br>"
					+ "&nbsp;&nbsp;5 자신의 말을 놓은 성이 완성되었을 경우<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;성 타일 하나당 2점을 받습니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;자신의 말을 놓은 길이 완성되었을 경우<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;성 타일 하나당 1점을 받습니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;자신의 말을 놓은 성단 주의 8칸이 모두 타일로<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;둘러 쌓여 완성될 경우 총 9점을 얻습니다.";
		}else if(gamename.equals("다빈치코드")) {
			gamename="&nbsp;&nbsp;1 타일을 뒤집어서 잘 섞은 후, 각자 타일을 4개씩<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;가져옵니다.가져온 타일은 왼쪽에서<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;오른쪽 방향으로 숫자가 커지도록 정리합니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;만약 같은 숫자일 경우, 검정 타일을 왼쪽에 놓아주세요.<br>"
					+ "&nbsp;&nbsp;2 자기 차례가 되면 뒤집어진 타일 1개를 가져와<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;순서에 맞게 정리해서 놓습니다. 그리고 상대방의 타일 중<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;하나를 선택해 그 숫자가 무엇인지 맞혀야 합니다. 숫자는<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0부터 11까지 있기 때문에 정보를 잘 조합해야 합니다.<br>"
					+ "&nbsp;&nbsp;3 상대방의 타일을 추리하세요!<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;추리가 틀렸다면 방금 가져온 숫자 타일을<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;공개하고 차례를 마칩니다.";
		}else if(gamename.equals("티켓 투 라이드")) {
			gamename="&nbsp;&nbsp;1 게임판의 가장자리에는 점수 트랙이 있습니다.<br>"
					+ "&nbsp;&nbsp;2 기차 카드를 잘 섞고 각 플레이어들에게 4장씩<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;나누어 줍니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;남은 기차 카드는 더미를 만들어 게임판 근처에 둡니다.<br>"
					+ "&nbsp;&nbsp;3 카드더미 가장 위에서 5장을 열어 앞면이 보이게<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;나란히 놓습니다.<br>"
					+ "&nbsp;&nbsp;4 목적지 카드를 플레이어에게 3장씩 나누어 줍니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;각 플레이어는 목적지 카드를 2개 선택합니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;나머지 카드는 반납합니다.<br>"
					+ "&nbsp;&nbsp;5 자신의 차례애는 2장의 기차카드를 가져올 수 있습니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;무지개색은 어느 색으로도 바꿀 수 있지만<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1장만 가져올 수 있습니다.<br>"
					+ "&nbsp;&nbsp;6 노선에 맞는 색을 가진 기차카드를 내면 노선을<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;연결할 수 있습니다.<br>"
					+ "&nbsp;&nbsp;7 기차말이 2개 이하가 되면 게임은 종료가 됩니다.";
		}else if(gamename.equals("우노")) {
			gamename="&nbsp;&nbsp;1 각자 7장씩 카드를 나눠 가진 후 더미에서 맨 위<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;한 장을 뒤집어 시작 카드로 놓습니다.<br>"
					+ "&nbsp;&nbsp;2 펼쳐진 카드와 숫자가 같거나 색이 같은 카드를<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;한 장 내려서 펼쳐진 카드를 덮습니다.<br>"
					+ "&nbsp;&nbsp;3 낼 카드가 없다면, 더미에서 카드를 한 장 가져옵니다.<br>"
					+ "&nbsp;&nbsp;4 손에 들고 있는 카드가 한 장이 되었을 때,<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;우노 라고 외칩니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;손에서 카드를 먼저 털어내는 사람이 승리합니다.";
		}else if(gamename.equals("타이니 타운")) {
			gamename="&nbsp;&nbsp;1 매 차례 수석 건축가의 망치를 가진 사람이<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;시작 플레이어가 되어 원하는 자원을 고릅니다.<br>"
					+ "&nbsp;&nbsp;2 모든 사람이 자원을 받아 각자의 공터에 놓습니다.<br>"
					+ "&nbsp;&nbsp;3 설계도에 따라 건물을 지으세요.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;건물을 지었다면 자원은 버릴 수 있습니다.<br>"
					+ "&nbsp;&nbsp;4 공터에 자원과 건물로 땅이 가득 차면 마을이 완성됩니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;아직 마을이 완성되지 않은 사람들은 계속 진행합니다.<br>"
					+ "&nbsp;&nbsp;5건물마다 다양한 방법으로 점수를 제공합니다.<br><br>";
		}else if(gamename.equals("뱅!")) {
			gamename="&nbsp;&nbsp;1 각자 인물 카드를 뽑고, 역할 카드를 뽑아 자신만<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;확인합니다. 보안관만 즉시 역할을 공개하고,<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;다른 사람은 뒤집어 놓습니다.<br>"
					+ "&nbsp;&nbsp;2 차례가 되면 먼저 가운데 카드 더미에서 2장을 뽑습니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;뱅!카드를 내면 옆에 있는 사람을 공격할 수 있습니다.<br>"
					+ "&nbsp;&nbsp;3 뱅 카드는 차례마다 한번만 낼 수 있습니다. <br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;공격을 받은 사람은 빗맞음 카드를 내서 피할 수<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 있습니다. 피하지 못하면 생명점 1점을 잃습니다.<br>"
					+ "&nbsp;&nbsp;4 총마다 사정거리가 다릅니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;총은 한 장만 내려 놓을 수 있습니다.<br>"
					+ "&nbsp;&nbsp;5 그 외에도 맥주,감옥,마차,다이너마이트 등<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;카드들이 있습니다.<br>"
					+ "&nbsp;&nbsp;6 생명을 모두 잃은 사람은 게임에서 빠집니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;보안관과 부관은, 무법자와 배신자가<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;모두 사망하면 승리합니다.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;무법자는 보안관이 사망하면 승리합니다.";
		}
		infoRuleTitle=new JLabel("게임 룰");
		infoRuleLbl=new JLabel("<html>"+gamename+"</html>");
	}
	
	
	public void gamePrint() {
		
	}
	

}
