package userSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import vo.CallDAO;
import vo.HistoryDAO;
import vo.HistoryVO;

public class UsingHistory extends JPanel implements ActionListener{
	Test test;
	//맨위 버튼
	JPanel askPane = new JPanel();
		JButton backBtn = new JButton("⇦ 뒤로");
		JButton askBtn = new JButton("관리자 호출");
	//사용내역	
	JPanel historyPane = new JPanel();
		JLabel historyStr = new JLabel("사용내역");
		JPanel lblPane = new JPanel(new ModifiedFlowLayout());
		JScrollPane sp = new JScrollPane(lblPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	//사용내역 세부사항
		JPanel detailHistoryPane = new JPanel();
			JLabel detailStrLbl = new JLabel("선택한 사용내역");
			JPanel detailLblPane = new JPanel(new ModifiedFlowLayout());
			JScrollPane detailSp = new JScrollPane(detailLblPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				JLabel detailHistoryLbl = new JLabel("사용내역이 없습니다.", JLabel.LEFT);
	String id ="";
	public UsingHistory() { }		
	public UsingHistory(Test test, String userId) {
		this.test=test;
		id=userId;
		usingHistoryFrame();
		getHistory(id);
		
		backBtn.addActionListener(this);
		askBtn.addActionListener(this);
		
		}
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		if(backBtn == obj) {
			test.change("main", id);
		}else if(askBtn ==obj) {
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
		
	public void getHistory(String id) {
		HistoryDAO dao = new HistoryDAO();
		List<HistoryVO> lst = dao.getMemberHistory(id);

		//lst에 데이터가 없을떄 --> 현재사용내역이없습니다. 출력
		//  for문으로 돌려서 라벨에 데이터집어넣을거얌
		//패널과 라벨이 한셋트임  // 
		if(lst.size()==0) {
			JLabel noTextLbl = new JLabel("사용 내역이 없습니다.");
			noTextLbl.setPreferredSize(new Dimension(350,40));
			lblPane.add(noTextLbl);
		}else {
			for(int i = 0; i<lst.size(); i++) {
				HistoryVO vo = lst.get(i);
				JLabel history = new JLabel("<HTML>"+ vo.getIntime().substring(0,10) +
						" [ "+ String.valueOf(vo.getRoomnum() + "번 룸] <BR>" + vo.getIntime().substring(11,16) + " ~ " + vo.getOuttime() + "</HTML>"));
				history.setPreferredSize(new Dimension(350,40)); history.setBorder(new LineBorder(Color.black));
				lblPane.add(history);
				history.addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent me) {
						if(me.getButton()==1) {
							//JOptionPane.showMessageDialog(getContentPane(), "된다요");
							detailLblPane.setVisible(false);
							detailLblPane.removeAll();
							detailLblPane.setVisible(true);
							String date = history.getText().substring(6,16);
							//System.out.println(date);
							HistoryDAO dao = new HistoryDAO();
							List<HistoryVO> lst = dao.getSelectedMemberHistory(id, date);
							//System.out.println(lst.size());
							
							HistoryVO vo = lst.get(0);
							JLabel detailhistory = new JLabel("<HTML>" + "[ " + vo.getRoomnum() + " 번룸] <BR> " + 
									"  이용시간 : " + vo.getIntime() + " ~ " + vo.getOuttime() + "</HTML>");
							
							String gameName = "";
							for(int i = 0; i<lst.size(); i++) {
								vo = lst.get(i);
								if(i == lst.size()-1) {
								gameName += vo.getGamename();
								}
								else {
									gameName += vo.getGamename()+",";
								}
							}
							JLabel detailhistoryGameName = new JLabel("<HTML>" + "  이용한 게임 : "+ gameName + "<BR>" + 
							"  결제 금액 : " + vo.getPay() + "</HTML>");
							
							detailLblPane.add(detailhistory);
							detailLblPane.add(detailhistoryGameName);
							
							detailhistory.setPreferredSize(new Dimension(350,27));
							detailhistoryGameName.setPreferredSize(new Dimension(350,30));
						}
					}
				});
				
			}
		}
		
	}
	public void usingHistoryFrame() {
		//상단 관리자 문의 
		askPane.setLayout(null);	
		askPane.setBackground(Color.white);
		askPane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
			backBtn.setBackground(Color.LIGHT_GRAY);
			backBtn.setBounds(10, 10, 100, 40); askPane.add(backBtn);
			askBtn.setBackground(Color.LIGHT_GRAY);
			askBtn.setBounds(330, 10, 100, 40);	askPane.add(askBtn);
		
		askPane.setBounds(0, 0, 443, 80);
		
		//사용내역
		// 검색 결과가 없으면 boder 없고 검색결과가 있을경우 테두리 만들면되지 않을까?
		historyPane.setLayout(null);	
		historyPane.setBackground(Color.white);
		historyPane.setBorder(new LineBorder(Color.black));	
			historyPane.add(sp); 
			historyPane.add(historyStr);
			historyStr.setFont(new Font("굴림", Font.BOLD, 15));
				lblPane.setBackground(Color.white); 
					
		historyStr.setBounds(30,5,120,40);
		sp.setBounds(33,50,380,270);
		historyPane.setBounds(0,80,443,350);	
	
		//사용내역 세부사항
		detailHistoryPane.setLayout(null);
		detailHistoryPane.setBackground(Color.white);
		detailHistoryPane.setBorder(new LineBorder(Color.black));
			detailHistoryPane.add(detailSp);
			detailHistoryPane.add(detailStrLbl);
			detailStrLbl.setFont(new Font("굴림", Font.BOLD, 15));
				detailLblPane.setBackground(Color.white);
				detailLblPane.add(detailHistoryLbl);
				detailHistoryLbl.setPreferredSize(new Dimension(350,40));
				
		detailSp.setBounds(33,50,380,250);	
		detailStrLbl.setBounds(30,5,120,40);
		detailHistoryPane.setBounds(0,430,443,370);
			
		setLayout(null);
		add(askPane);
		add(historyPane);
		add(detailHistoryPane);
		setVisible(true);
		setSize(460, 800);
	}
}
