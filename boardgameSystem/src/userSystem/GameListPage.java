package userSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import vo.CallDAO;
import vo.GameDAO;
import vo.GameVO;

public class GameListPage extends JPanel implements ActionListener{
	Test test;
	
	JPanel askPane = new JPanel();
	JButton backBtn = new JButton("◀ 뒤로");
	JButton askBtn = new JButton("관리자 호출");
	
	String lbl[] = {"게임명","게임 인원","플레이타임","대여 여부"}; 
	
	JPanel searchPane=new JPanel();
		JTextField searchTf=new JTextField();
		JButton searchBtn=new JButton("검색");
		JButton clearBtn=new JButton("초기화");
	
	JPanel gamePane=new JPanel();
		JTable gameTable;
		JScrollPane gameSp;
		DefaultTableModel model;
	String id="";
	String btnCheck = ""; // 1:( 플레이, 예약, 구매) 모두 비활성화 ,  2: 구매 활성화, 플레이 or 예약은(재고에따라 활성화 변경)
	public GameListPage() {}
	public GameListPage(Test test, String userId, String btnCheck) {
		this.test=test;
		id=userId;
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
		//검색 
		searchPane.setLayout(null);
		searchPane.setBounds(0, 60, 460, 60);
		searchPane.setBackground(Color.white);
			searchTf.setBounds(50,10,160,40); searchPane.add(searchTf);
			searchBtn.setBackground(Color.LIGHT_GRAY); 
			searchBtn.setBounds(223,10,70,40); searchPane.add(searchBtn);
			clearBtn.setBackground(Color.LIGHT_GRAY);
			clearBtn.setBounds(308,10,80,40); searchPane.add(clearBtn);
		
		//게임 목록
		gamePane.setLayout(null);
		gamePane.setBackground(Color.white);
		
		// 내용 수정 불가능 메소드
		model = new DefaultTableModel(lbl,0) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		
		gameTable=new JTable(model);
		gameTable.setRowSelectionAllowed(true);
		gameSp=new JScrollPane(gameTable);
		
		gameTable.getTableHeader().setFont(new Font("한컴 고딕",Font.BOLD,15));
		gameTable.getTableHeader().setBackground(new Color(232,234,255));
		
		gameTable.setRowHeight(30);
		gameTable.setFont(new Font("한컴 고딕", Font.PLAIN,12));
		gameTable.getParent().setBackground(Color.white);
		
		
		DefaultTableCellRenderer center=new DefaultTableCellRenderer(); // 센터 정렬
		DefaultTableCellRenderer right=new DefaultTableCellRenderer(); // 우측 정렬
		center.setHorizontalAlignment(SwingConstants.CENTER);
		right.setHorizontalAlignment(SwingConstants.RIGHT);
		TableColumnModel tcm=gameTable.getColumnModel();
		
		for(int i=1;i<tcm.getColumnCount();i++) {
			tcm.getColumn(i).setCellRenderer(center);
		}
		
		gameSp.setBounds(50,0,340,600); gamePane.add(gameSp);
		gamePane.setBounds(0,120,460,645);
		
		add(searchPane);
		add(gamePane);
		
		setForeground(Color.WHITE);
	//	setResizable(false); //창크기 고정
		setLayout(null);		
		setVisible(true);
		setSize(460, 763);
	//	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		searchBtn.addActionListener(this);
		clearBtn.addActionListener(this);
		backBtn.addActionListener(this);
		askBtn.addActionListener(this);
		getGameAll();
		
		gameTable.addMouseListener(new MouseAdapter() { // 메소드가 많기 때문에 어댑터 사용
			public void mouseClicked(MouseEvent me) {
				// 이벤트 발생이 마우스 왼쪽버튼이면
				if(me.getClickCount()==2) { // 더블 클릭 이벤트인 경우, 한 번 클릭 이벤트는 me.getButton()==1
					// 선택된 행 번호 가져오기
					int row=gameTable.getSelectedRow(); // 행 구하기
					String obj=(String)gameTable.getValueAt(row, 0); // 게임 명 얻기
				//	setVisible(false); // visible을 false를 해야 GameInfo 화면이 바로 뜸
					test.change("gameInfo", id ,obj , btnCheck); // gameInfo에 게임명을 넘겨준다
				}
			}
		});
	}
	// 버튼 이벤트
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		String eventBtn=ae.getActionCommand();
		if(eventBtn.equals("검색")) {
			gameSearch();
		}else if(eventBtn.equals("초기화")) {
			getGameAll();
		}else if(backBtn==obj) {
			test.change("main", id);
		}else if(askBtn==obj) {
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
	
	// 대여 여부
	public void rentStatus(int rentStock) {
		
	}
	// 게임 검색
	public void gameSearch() {
		// 검색어에 입력된 게임명
		String searchWord=searchTf.getText();
		if(searchWord.equals("")) { // 입력된 게임명이 없는 경우
			JOptionPane.showMessageDialog(this, "게임명을 입력 후 검색하여 주세요.");
		}else { // 입력된 게임명이 있는 경우
			GameDAO dao=new GameDAO();
			List<GameVO> searchList=dao.getSearchRecord(searchWord); // game이 들어있는 list가 리턴
			
			if(searchList.size()==0) { // 입력한 게임명이 없을 경우
				JOptionPane.showMessageDialog(this, searchWord+"의 결과가 존재하지 않습니다.");
			}else { // 입력한 게임명이 있을 경우
				setNewTableList(searchList);
			}
			searchTf.setText("");
		}
	}
	
	// 게임 전체선택
	public void getGameAll() {
		// db의 모든 회원을 선택해서 JTable에 표시한다
		GameDAO dao=new GameDAO();
		List<GameVO> lst=dao.GameAllSelect(); // MemberDAO에서 리턴받은 lst를 받아온다
			
		setNewTableList(lst);
	}
	
	public void setNewTableList(List<GameVO> lst) {
		String rentStatus = null;
		model.setRowCount(0); // JTable의 모든 레코드 지우기
		for(int i=0;i<lst.size();i++) { // size는 length와 비슷한 메소드
			GameVO vo=lst.get(i);
			if(vo.getRentstock()>0) {
				rentStatus="O";
			}else { // 대여 가능 재고가 0이하인 경우
				rentStatus="X";
			}
			Object[] data= {vo.getGamename(), vo.getMaxpeople(), vo.getPlaytime(), vo.getRentstock()};
		//	Object[] data= {vo.getGamename(), vo.getMaxpeople(), vo.getPlaytime(), rentStatus};
			model.addRow(data);
		}
	}
	

}
