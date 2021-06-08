package adminSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton; 
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import vo.MemberDAO;
import vo.MemberVO; 

public class MemberMng extends JPanel implements ActionListener{
	MainFrame mainFrm;
	String idCheck;
	Font fnt=new Font("돋움",Font.BOLD,13);
	JMenuBar mb = new JMenuBar();
	JMenu homeMenu = new JMenu("홈");
		JMenuItem homeItem = new JMenuItem("홈");
		JMenuItem exit = new JMenuItem("종료");
	JMenu statsMenu = new JMenu("통계");
	JMenu mngMenu = new JMenu("관리");
		JMenu stockMenu = new JMenu("재고관리");
			JMenuItem invenMngItem = new JMenuItem("게임재고관리");
			JMenuItem foodMngItem = new JMenuItem("음식재고관리");
		JMenuItem memberMenu = new JMenuItem("회원관리"); 
	
	//위의 입력창
	JPanel bigPane=new JPanel(new BorderLayout());
	JPanel northPane=new JPanel(new BorderLayout());
	JPanel norLeftPane=new JPanel(new GridLayout(6,1));
		JPanel idPane= new JPanel(new FlowLayout(FlowLayout.LEFT) );
			JLabel idLbl = new JLabel(" 회원아이디");
			JTextField idTf = new JTextField(); 
		JPanel pwdPane=new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel pwdLbl = new JLabel(" 회원비밀번호");
			JTextField pwdTf = new JTextField(); 
		JPanel namePane=new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel nameLbl = new JLabel(" 성함");
			JTextField nameTf = new JTextField(); 
		JPanel telPane=new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel telLbl = new JLabel(" 연락처");
			JTextField telTf = new JTextField(); 
		JPanel birthdatePane=new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel birthdateLbl = new JLabel(" 생년월일"); 
			boolean numOK = true;
			JTextField birthdateTf   = new JTextField();   
		JPanel joindatePane=new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel joindateLbl = new JLabel(" 가입일자");
			JTextField joindateTf = new JTextField(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	 

		//오른쪽 버튼 세개
		JPanel norRightPane=new JPanel();
			JButton addBtn = new JButton("신규추가");
			JButton editBtn = new JButton("수정");
			JButton deleteBtn = new JButton("삭제");
			JButton clearBtn = new JButton("내용 지우기");
		
		//중간 검색 패널
		JPanel centerPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JTextField  searchTf = new JTextField();
			JButton searchBtn = new JButton("검색");
			JButton getAllBtn = new JButton("전체목록");
		
		//아래 테이블 
		JScrollPane sp = new JScrollPane(); 
			String lbl[] = {"회원아이디", "회원비밀번호","성함","연락처","생년월일","가입일자"};
			DefaultTableModel model;
			JTable memberTable=new JTable();
			String id;
	
	public MemberMng() {} 		
	public MemberMng(MainFrame mainFrm) { 
		this.mainFrm = mainFrm;
	 	
		//회원 정보 입력창
		norLeftPane.setPreferredSize(new Dimension(400,210));
		idLbl.setFont(fnt);                 idPane.add(idLbl); 			 
		idTf.setFont(fnt);                  idPane.add(idTf);   
		idPane.setBounds(100,200,30,20);    
		
		pwdLbl.setFont(fnt);                pwdPane.add(pwdLbl);
		pwdTf.setFont(fnt);    		   	    pwdPane.add(pwdTf); 
		
		nameLbl.setFont(fnt);   		    namePane.add( nameLbl);
		nameTf.setFont(fnt);  		        namePane.add( nameTf); 
			 
		telLbl.setFont(fnt);    	        telPane.add( telLbl);
		telTf.setFont(fnt);     	        telPane.add( telTf); 
	 			 
		birthdateLbl.setFont(fnt);   		birthdatePane.add(birthdateLbl);
		birthdateTf.setFont(fnt);     		birthdatePane.add(birthdateTf); 
		
	  	//joindatePane.setLayout(null);
		joindateLbl.setBounds(5,0,110,25);
		joindateLbl.setFont(fnt);      		joindatePane.add(joindateLbl);
		joindateTf.setPreferredSize(new Dimension(200,25));
		joindateTf.setBounds(105,0,200,25);
		joindateTf.setFont(fnt);			joindatePane.add(joindateTf);
		
		norLeftPane.add(namePane); 			norLeftPane.setSize(300,300);
	 	norLeftPane.add(idPane);   			norLeftPane.add(pwdPane); 
	 	norLeftPane.add(telPane);			norLeftPane.add(birthdatePane);
	 	norLeftPane.add(joindatePane);
		
		clearBtn.setFont(fnt);  	 		 
		clearBtn.setPreferredSize(new Dimension(150,25));
		clearBtn.setLayout(new FlowLayout(FlowLayout.RIGHT));;
			
		idLbl.setPreferredSize(new Dimension(100,25));
		pwdLbl.setPreferredSize(new Dimension(100,25));
		nameLbl.setPreferredSize(new Dimension(100,25));
		telLbl.setPreferredSize(new Dimension(100,25));
		birthdateLbl.setPreferredSize(new Dimension(100,25));
		joindateLbl.setPreferredSize(new Dimension(100,25));
		idTf.setPreferredSize(new Dimension(200,25));
		pwdTf.setPreferredSize(new Dimension(200,25));
		nameTf.setPreferredSize(new Dimension(200,25));
		telTf.setPreferredSize(new Dimension(200,25));
		birthdateTf.setPreferredSize(new Dimension(200,25));
		
		//위의 오른쪽 버튼  		
		norRightPane.setPreferredSize(new Dimension(160,80));
		norRightPane.setLayout(null); 
		addBtn.setBounds(25, 35, 110,25);          addBtn.setFont(fnt);
		editBtn.setBounds(25, 75, 110,25);     	   editBtn.setFont(fnt);
		deleteBtn.setBounds(25, 115, 110,25);       deleteBtn.setFont(fnt); 
		clearBtn.setBounds(25,155,110,25);         clearBtn.setFont(fnt);
	 
		norRightPane.add(addBtn);                norRightPane.add(editBtn);				
		norRightPane.add(deleteBtn);             norRightPane.add(clearBtn); 
		 
		norRightPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		northPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		 
		//중간 검색  패널  
		centerPane.setPreferredSize(new Dimension(680,150)); 
		centerPane.setLocation( 0, 0);
		searchTf.setPreferredSize(new Dimension(200,25));    
		centerPane.add(searchTf);				 searchTf.setFont(fnt);
		searchBtn.setBounds(220,10,80,25);	
		centerPane.add(searchBtn);				 searchBtn.setFont(fnt);
		getAllBtn.setBounds(310,10,100,25);
		centerPane.add(getAllBtn);				 getAllBtn.setFont(fnt);
		 
		//아래 회원테이블 
	 	model = new DefaultTableModel(lbl,0);
		memberTable = new JTable(model);
		sp = new JScrollPane(memberTable);  
	 	sp.setPreferredSize(new Dimension(670,345));  
		
		//테이블 정렬
		DefaultTableCellRenderer center=new DefaultTableCellRenderer();  
		DefaultTableCellRenderer right=new DefaultTableCellRenderer(); 
		center.setHorizontalAlignment(SwingConstants.CENTER);
		right.setHorizontalAlignment(SwingConstants.RIGHT);
		TableColumnModel tcm = memberTable.getColumnModel();
		for(int i=0;i<tcm.getColumnCount();i++) {
			tcm.getColumn(i).setCellRenderer(center);
		}

		northPane.setPreferredSize(new Dimension(695,220));  
		northPane.add("East", norRightPane);
		northPane.add("West",norLeftPane); 
		bigPane.setPreferredSize(new Dimension(700,600));
		bigPane.add("North",northPane);
		bigPane.add("Center",centerPane);	 
		bigPane.add("South",sp);     
	 	add(bigPane);
		setSize(700,600);
		setVisible(true);
	 
		 //이벤트 등록
		addBtn.addActionListener(this);
		editBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		searchTf.addActionListener(this);
		searchBtn.addActionListener(this);
		getAllBtn.addActionListener(this);
		clearBtn.addActionListener(this); 
		invenMngItem.addActionListener(this);
		foodMngItem.addActionListener(this); 
		 		
		memberTable.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent me) { 
					if(me.getButton()==1) { 
						int row = memberTable.getSelectedRow(); 
						int col = memberTable.getColumnCount();
						for(int c=0; c<col; c++) {	
							String obj = String.valueOf(memberTable.getValueAt(row, c));
							if(c==0) {
								idTf.setText(obj);
							}else if(c==1) {
								pwdTf.setText(obj);
							}else if(c==2) {
								nameTf.setText(obj);
							}else if(c==3) {
								telTf.setText(String.valueOf(obj));
							}else if(c==4) {
								birthdateTf.setText(obj);
							}else if(c==5) {
								joindateTf.setText(obj);
							} 				
						}    
					}
				}
			});
			memberGetAll(); 
	}

	
	public void actionPerformed(ActionEvent ae) {
		Object eObj = ae.getSource(); 
		if(eObj==searchTf || eObj==searchBtn) {
				memberSearch();  //검색		
		}else if(eObj== getAllBtn) {
			    memberGetAll(); //전체목록 불러오기			 
		}else if(eObj==addBtn) {
				addMember(); //회원 신규추가하기
		}else if(eObj==editBtn) {
			try {		editMember();      //회원 수정하기
				} catch (ParseException e) { 
					e.printStackTrace();	} 
		}else if(eObj==deleteBtn) {
				deleteMember(); //회원 삭제하기
		}else if(eObj==clearBtn) { 
				clearForm(); //폼 내용 지우기
		}else if(eObj instanceof JMenuItem) {
				String eventMenu = ae.getActionCommand();
		if(eventMenu.equals("게임 재고 관리")){
				mainFrm.change("gamePage");
		}else if(eventMenu.equals("음식 재고 관리")){
				mainFrm.change("foodPage");
		}else if(eventMenu.equals("회원관리")){
				mainFrm.change("memberPage");
		} 
	}
}
	
	
		//회원 검색
		public void memberSearch() {
			String searchWord = searchTf.getText();
			if(searchTf.getText().equals("")) { 
				JOptionPane.showMessageDialog(this, "이름을 입력 후 검색을 눌러주세요");
			}else {
				MemberDAO dao = new MemberDAO();
				List<MemberVO> searchList = dao.getSearchRecord(searchWord); 
				if(searchList.size()==0) {
					JOptionPane.showMessageDialog(this, searchWord+"의 검색 결과가 없습니다.");
				}else {
					model.setRowCount(0); 
					setNewTableList(searchList);
				}
				searchTf.setText("");		
			}
		}
		
			//새로운 테이블 리스트 만들기 
		public void setNewTableList(List<MemberVO> lst) {
			model.setRowCount(0);
		
			for(int i=0; i<lst.size(); i++) {
				MemberVO vo= lst.get(i);  
				
				String birth = vo.getBirth().substring(0,10); 
				String join = vo.getJoindate().substring(0,10);
				
				Object[] data = {vo.getId(), vo.getPw(), vo.getName(), vo.getTel(),
						birth, join};

			 	model.addRow(data);
			}
		}

		//회원 목록 전체 불러오기  
		public void memberGetAll() {
			MemberDAO dao = new MemberDAO(); 
			List<MemberVO> lst= dao.getMemberList();  			
			setNewTableList(lst);      
		}

		
		
		//회원 신규 추가 중 - ID 확인
		
		public void checkID() {		
			idCheck = idTf.getText();
			MemberDAO dao = new MemberDAO(); 
	 		int idChecked = dao.userCheckId(idCheck);  
			if(idChecked==0) {//검색된 레코드가 없을때. 
				JOptionPane.showMessageDialog(this, "입력하신 ID ["  +idCheck+ "]는 \n 사용가능한 ID 입니다."  );
			}else {
				JOptionPane.showMessageDialog(this, "입력하신 ID ["  +idCheck+ "]와 \n 중복되는 ID가 있습니다.\n"
						+ "다른 ID를 입력해주세요.");
				idCheck="";
			}  
		}
		
		
		//회원 신규 추가
		public void addMember() {
			checkID();
	 		idCheck = idTf.getText();
	 		int result = 0;
	 		MemberDAO dao = new MemberDAO();
	 		MemberVO vo;
	 	
	 		if(idCheck.equals("")|| pwdTf.getText().equals("")|| nameTf.getText().equals("") 
	 				             || telTf.getText().equals("") || birthdateTf.getText().equals("") 
	 				             || joindateTf.getText().equals("")) {
	 			JOptionPane.showMessageDialog(this, "빈칸 없이 입력해주세요" );
 			}else {
 				vo = new MemberVO(idTf.getText(), pwdTf.getText(), nameTf.getText(), telTf.getText(), birthdateTf.getText(), joindateTf.getText());
	 	 			
	 			boolean birthPt = Pattern.matches(new String("^(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$"), vo.getBirth());
	 				 vo = new MemberVO();
	 			 	 
	 				 vo.setId(idCheck);  
	 				 vo.setPw(pwdTf.getText());
	 				 vo.setName(nameTf.getText());
	 				 vo.setTel(telTf.getText()); 
	 				
	 		  
	 				 if(!birthdateTf.getText().equals("") || birthdateTf.getText()!=null) {
	 					 vo.setBirth(birthdateTf.getText()); 
	 				 }else if(birthPt==false) {
	 					JOptionPane.showMessageDialog(this, "YYYY-MM-DD 형식으로 숫자로만 입력해 주세요"  );
	 				}
	 				if(joindateTf.getText()!="") {
	 					 vo.setJoindate(joindateTf.getText());	
	 				}
	 			 		 		 	
	 				 result = dao.memberInsert(vo, birthdateTf.getText());
	 	 
	 				 if(result>0) {  
	 					JOptionPane.showMessageDialog(this, "회원 가입이 완료되었습니다.");
	 					memberGetAll(); 
	 				 }else { 
	 				 JOptionPane.showMessageDialog(this, "회원 가입에 실패했습니다.");
	 				 } 	 
 			}
		}
	 			
	 			
	 	 
	 		
	 		
			  
			
	 	
		//폼의 값 지우기  
		public void clearForm() {
			 idTf.setText("");             pwdTf.setText("");
			 nameTf.setText("");           telTf.setText("");
			 birthdateTf.setText("");      joindateTf.setText("");
		}
	 	
		//회원정보 수정  
		public void editMember() throws ParseException {
		 	 MemberVO vo = new MemberVO();
			 
			 vo.setId(idTf.getText());  
			 vo.setPw(pwdTf.getText());
			 vo.setName(nameTf.getText());
			 vo.setTel(telTf.getText());
			 
			 vo.setBirth(birthdateTf.getText());
			 vo.setJoindate(joindateTf.getText());
		 
		 	 MemberDAO dao = new MemberDAO();
			 int result = dao.memberUpdate(vo);
 
			 if(result>0) {  
				JOptionPane.showMessageDialog(this, "회원 정보 수정이 완료되었습니다.");
				memberGetAll(); 
			 }else { 
			 JOptionPane.showMessageDialog(this, "회원 정보 수정에 실패했습니다.");
			 } 	 
		 }	
		
		//회원 삭제  
		public void deleteMember() {
			String id= idTf.getText();
			MemberDAO dao = new MemberDAO();
			int result = dao.memberDelete(id);
			String msg = "회원 정보가 삭제되었습니다.";
			if(result>0) {
				memberGetAll();
				clearForm();
			}else {
				msg = "회원 정보 삭제에 실패했습니다.";
			}
			JOptionPane.showMessageDialog(this, msg);	
		}
		
	 	public static void main(String[] args) {
			new MemberMng();
		}
 }