package adminSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import vo.GameDAO;
import vo.GameVO;
import vo.ReservationDAO;

public class Pay_Dialog extends JDialog implements ActionListener{
	JPanel mainPane = new JPanel();
	JLabel totalLbl = new JLabel("총 결제 금액");
	JLabel totalPayLbl = new JLabel();
	JLabel wonLbl = new JLabel("원");
	JButton donePayBtn = new JButton("결제완료");
	JButton exitBtn = new JButton("닫기");
	Color co = Color.white;
	Font fnt1 = new Font("돋움체", Font.BOLD, 20);
	Font fnt2 = new Font("돋움체", Font.BOLD, 15);;
	Font fnt3 = new Font("돋움체", Font.PLAIN, 15);;
	int roomnum = 0;
	int roomrenum = 0;
	int pay = 0;
	public Pay_Dialog() { }
	public Pay_Dialog(int roomnum, int roomrenum, int pay) {
		this.roomnum = roomnum;
		this.roomrenum = roomrenum;
		this.pay = pay;
		pay_DialogFrame();
		DecimalFormat dFmt = new DecimalFormat("#,###");
		totalPayLbl.setText(dFmt.format(pay));
		
		donePayBtn.addActionListener(this);
		exitBtn.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		if(obj==donePayBtn) {
			//DAO -> 사용중이던 게임 반납으로 변경 하면서 -> 수량 +1 시키기 
			//DAO -> 해당 룸예약번호의 PayCheck->'Y'로 변경, 결제금액 총금액으로 변경
			returnGame();
			paySet();
		}else if(obj==exitBtn){
			Detail_Info_Dialog did = new Detail_Info_Dialog(roomnum);
			setVisible(false);
		}
		
	}
	public void returnGame() { //플레이중이던게임 반납처리하기. 
		//사용중이던 게임 반납으로 변경 하면서 -> 수량 +1 시키기 	
		//사용중이던 게임 이름으로 gameDAO에서 rentStock 수량 가져오기. => 1개 늘리기 => 업데이트로 1개추가된걸로 바꾸기. 
		String nowGame= AdminMain.nowGameGet(roomrenum);
		GameDAO dao = new GameDAO();
		GameVO vo = new GameVO();
		vo = dao.GameInfoSelect(nowGame); //게임이름으로해당게임의 정보 불러오
		int changerentStock = vo.getRentstock()+1; //불러온 렌트스탁에 +1 해서 변수에저장  
		System.out.println("\n기존수량 =>"+ vo.getRentstock()+"\n변경수량 =>" +changerentStock);
		vo.setRentstock(changerentStock); // 다시 vo에 해당 수량 저장  
		int result = dao.RentUpdate(vo); // 수량+1 업데이트하기. 
		if(result>0) {
			System.out.println("수량증가 되었음.");
		}
	}
	public void paySet() { 
		//DAO -> 해당 룸예약번호의 PayCheck->'Y'로 변경, 결제금액 총금액으로 변경
		//룸예약번호로 검색한걸로 업데이트 넣기.  (reservationDAO) , int result로 리턴 받기.  
		ReservationDAO dao = new ReservationDAO();
		int result = dao.setPayCheckYes(roomrenum, pay);
		if(result>0) {
			JOptionPane.showMessageDialog(this, "결제 완료되었습니다.");
			Detail_Info_Dialog did = new Detail_Info_Dialog(roomnum);
			setVisible(false);
		}
	}
	public void pay_DialogFrame() {
		mainPane.setBackground(co);
		
		totalLbl.setBounds(30, 10, 200, 50);	totalLbl.setFont(fnt1);
		totalPayLbl.setBounds(40, 80, 150, 20);	totalPayLbl.setFont(fnt3);
		totalPayLbl.setHorizontalAlignment(JLabel.RIGHT);
		totalPayLbl.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		wonLbl.setBounds(193, 70, 20, 40); wonLbl.setFont(fnt3);
		donePayBtn.setBounds(50, 120, 100, 40);	exitBtn.setBounds(170, 120, 100, 40);
		
		mainPane.setLayout(null);
		mainPane.add(totalLbl);		mainPane.add(totalPayLbl);	mainPane.add(wonLbl);
		mainPane.add(donePayBtn);	mainPane.add(exitBtn);
		add(mainPane);	
		setSize(300, 210);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}
