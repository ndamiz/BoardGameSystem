 package adminSystem;
// 2/15 2:30까지

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.NumberFormatter;

import vo.GameDAO;
import vo.GameVO;
 
 
 

public class GameInvenMng extends JPanel implements ActionListener{
 	MainFrame mainFrm;
	Font fnt=new Font("돋움",Font.BOLD,13);
	JMenuBar mb=new JMenuBar();
	JMenu homeMenu=new JMenu("홈");
	JMenu statsMenu=new JMenu("통계");
	JMenu stockMenu=new JMenu("재고관리");
		JMenuItem invenMngItem=new JMenuItem("게임재고관리");
		JMenuItem foodMngItem=new JMenuItem("음식재고관리"); 
	JMenu memberMenu=new JMenu("회원관리");
	JPanel bigPane =new JPanel(new BorderLayout());
	JPanel norNorthPane=new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel northPane=new JPanel(new BorderLayout());
		JButton gameStockBtn = new JButton("게임 재고 관리");
		JButton foodStockBtn = new JButton("음식 재고 관리");
	JPanel norPane2=new JPanel(new BorderLayout());
	JPanel norLeftPane=new JPanel(new GridLayout(5,1));
		JPanel gamePane= new JPanel(new FlowLayout(FlowLayout.LEFT) );
	 	JPanel pricePane=new JPanel(new FlowLayout(FlowLayout.LEFT));
	 	JPanel stockPane=new JPanel(new FlowLayout(FlowLayout.LEFT));
	 	JPanel peoplePane=new JPanel(new FlowLayout(FlowLayout.LEFT));
	 	JPanel playtimePane=new JPanel(new FlowLayout(FlowLayout.LEFT));
	 	JPanel picPane=new JPanel();
	 		JPanel showPicPane = new JPanel(new BorderLayout()); 
	 		JButton getPicBtn = new JButton("사진 업로드");
	 		JButton removePicBtn = new JButton("사진 삭제");
	 		MyCanvas mc = new MyCanvas(); 
			JLabel gameLbl = new JLabel(" 게임 명");
			JLabel unitPriceLbl = new JLabel(" 단가");
			int gamePrice ;
			JLabel rentAmountLbl = new JLabel(" 대여용 재고량");
			JLabel sellAmountLbl = new JLabel(" 판매용 재고량");
			JLabel minLbl = new JLabel(" 최소 인원");
			JLabel maxLbl = new JLabel("  최대 인원");
			JLabel playtimeLbl = new JLabel(" 플레이타임");
			JLabel emptyLbl3 = new JLabel("                                                                     ");
			JTextField gameTf = new JTextField(); 			
			NumberFormatter a = new NumberFormatter(NumberFormat.getNumberInstance()); 
			NumberFormatter b = new NumberFormatter(NumberFormat.getNumberInstance()); 
			NumberFormatter c = new NumberFormatter(NumberFormat.getNumberInstance()); 
			NumberFormatter d = new NumberFormatter(NumberFormat.getNumberInstance()); 
			JFormattedTextField unitPriceTf = new JFormattedTextField(a);
			JFormattedTextField rentAmountTf = new JFormattedTextField(b);
			JFormattedTextField sellAmountTf = new JFormattedTextField(b);
			JFormattedTextField minTf = new JFormattedTextField(c);
			JFormattedTextField maxTf = new JFormattedTextField(d);			
			JTextField  playtimeTf = new JTextField();			
		JPanel norRightPane=new JPanel();
			JButton addBtn = new JButton("신규추가");
			JButton editBtn = new JButton("수정");
			JButton deleteBtn = new JButton("삭제");
			JButton clearBtn = new JButton("내용 지우기");
	JPanel centerPane=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JTextField  searchTf = new JTextField();
		JButton searchBtn = new JButton("검색");
		JButton getAllBtn = new JButton("전체목록");
	
	JScrollPane sp = new JScrollPane();
		String lbl[] = {"게임명", "단가","대여용 재고량","판매용 재고량","최소인원","최대인원","플레이타임"};
		DefaultTableModel model;
		JTable gameTable=new JTable();
		 
	
		String adminId;
	public GameInvenMng() {}
	public GameInvenMng(MainFrame mainFrm, String adminId) { 
		this.mainFrm = mainFrm;
		this.adminId = adminId;
		
	  	//Numberformat textfield 세팅
		a.setMaximum(1000000);           a.setAllowsInvalid(false);
		b.setMaximum(100);				 b.setAllowsInvalid(false);
		c.setMaximum(20);				 c.setAllowsInvalid(false);
		d.setMaximum(50);				 d.setAllowsInvalid(false);
			
		// 맨 위 두개 버튼  
    	gameStockBtn.setBounds(10,80,100,25);
	 	foodStockBtn.setBounds(110,10,100,25);
		gameStockBtn.setFont(fnt);   norNorthPane.add(gameStockBtn);
		foodStockBtn.setFont(fnt);   norNorthPane.add(foodStockBtn);
		norNorthPane.setPreferredSize(new Dimension(600,35)); 
		
	 	//게임 정보 입력창
		gameLbl.setPreferredSize(new Dimension(90,25));
		unitPriceLbl.setPreferredSize(new Dimension(90,25));
		rentAmountLbl.setPreferredSize(new Dimension(90,25));
		sellAmountLbl.setPreferredSize(new Dimension(90,25));
		minLbl.setPreferredSize(new Dimension(90,25));
		maxLbl.setPreferredSize(new Dimension(90,25));
		playtimeLbl.setPreferredSize(new Dimension(90,25));
		
		norLeftPane.setPreferredSize(new Dimension(380,180));
		gameLbl.setFont(fnt);        gamePane.add(gameLbl); 
		gameTf.setFont(fnt);	     gamePane.add(gameTf);
		gameTf.setBounds(100,200,30,20);
		unitPriceLbl.setFont(fnt);   pricePane.add(unitPriceLbl);
		unitPriceTf.setFont(fnt);    pricePane.add(unitPriceTf);
		rentAmountLbl.setFont(fnt);  stockPane.add(rentAmountLbl);
		rentAmountTf.setFont(fnt);   stockPane.add(rentAmountTf);
		sellAmountLbl.setFont(fnt);  stockPane.add(sellAmountLbl);		
		sellAmountTf.setFont(fnt);   stockPane.add(sellAmountTf);
		minLbl.setFont(fnt);         peoplePane.add(minLbl); 
		minTf.setFont(fnt);  	  	 peoplePane.add(minTf);
		maxLbl.setFont(fnt);  		 peoplePane.add(maxLbl); 
		maxTf.setFont(fnt);  		 peoplePane.add(maxTf);
		playtimeLbl.setFont(fnt);    playtimePane.add(playtimeLbl);
		playtimeTf.setFont(fnt);  	 playtimePane.add(playtimeTf);
		playtimePane.add(emptyLbl3); 
				
		picPane.setLayout(null); 
		picPane.add(showPicPane);
		showPicPane.add(mc); 		
		showPicPane.setBounds(15,10,110,95);
		getPicBtn.setFont(fnt);      removePicBtn.setFont(fnt); 		 
		picPane.add(getPicBtn);      picPane.add(removePicBtn);
		getPicBtn.setBounds(15,115,110,25);
		removePicBtn.setBounds(15,145,110,25);		 
	 	  
		
		gameTf.setPreferredSize(new Dimension(200,25));
		unitPriceTf.setPreferredSize(new Dimension(200,25));
		rentAmountTf.setPreferredSize(new Dimension(90,25));
		sellAmountTf.setPreferredSize(new Dimension(90,25));
		minTf.setPreferredSize(new Dimension(90,25));
		maxTf.setPreferredSize(new Dimension(90,25));
		playtimeTf.setPreferredSize(new Dimension(90,25)); 
	 	
		norLeftPane.add(gamePane);
		norLeftPane.add(pricePane);
		norLeftPane.add(stockPane);
		norLeftPane.add(peoplePane);
		norLeftPane.add(playtimePane); 
		
		norRightPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		norPane2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		//위의 오른쪽 버튼  		
		norRightPane.setPreferredSize(new Dimension(170,80));
		norRightPane.setLayout(null); 
		addBtn.setBounds(25, 15, 110,25);          addBtn.setFont(fnt);
		editBtn.setBounds(25, 55, 110,25);     	   editBtn.setFont(fnt);
		deleteBtn.setBounds(25, 95, 110,25);       deleteBtn.setFont(fnt); 
		clearBtn.setBounds(25,135,110,25);         clearBtn.setFont(fnt);
		norRightPane.add(addBtn);
		norRightPane.add(editBtn);
		norRightPane.add(deleteBtn); 
		norRightPane.add(clearBtn);  
	 	  
		//중간 검색 패널  
		centerPane.setBounds(0,10,500,30);
		searchTf.setPreferredSize(new Dimension(200,25));
		centerPane.add(searchTf);		           searchTf.setFont(fnt);
		searchBtn.setBounds(220,10,80,25);
		centerPane.add(searchBtn);		           searchBtn.setFont(fnt);
		getAllBtn.setBounds(310,10,100,25);
		centerPane.add(getAllBtn);		           getAllBtn.setFont(fnt);
		
	   	//아래 테이블 패널	   
		model = new DefaultTableModel(lbl,0);
		gameTable = new JTable(model);
		sp = new JScrollPane(gameTable);
		sp.setPreferredSize(new Dimension(700,280));
		//테이블 정렬
		DefaultTableCellRenderer center=new DefaultTableCellRenderer();  
		DefaultTableCellRenderer right=new DefaultTableCellRenderer(); 
		center.setHorizontalAlignment(SwingConstants.CENTER);
		right.setHorizontalAlignment(SwingConstants.RIGHT);
		TableColumnModel tcm = gameTable.getColumnModel();
		tcm.getColumn(0).setCellRenderer(center);
		tcm.getColumn(1).setCellRenderer(right);
		for(int i=2;i<tcm.getColumnCount();i++) {
			tcm.getColumn(i).setCellRenderer(center);
		}
		
		norPane2.add("East", norRightPane);
		norPane2.add(picPane); 
		norPane2.add("West",norLeftPane);
		northPane.add("South", norPane2);
		northPane.add("North",norNorthPane);
		bigPane.add("North",northPane);
		bigPane.add("Center",centerPane);	
		bigPane.add("South",sp);  
		add("South", bigPane);
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
		getPicBtn.addActionListener(this);
		removePicBtn.addActionListener(this);
		gameStockBtn.addActionListener(this);
		foodStockBtn.addActionListener(this); 
		foodMngItem.addActionListener(this);
		memberMenu.addActionListener(this);
		
		gameTable.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) { 
				if(me.getButton()==1) { 
					int row = gameTable.getSelectedRow(); 
					int col = gameTable.getColumnCount();
					for(int c=0;c<col;c++) {	
						String obj = String.valueOf(gameTable.getValueAt(row, c));
						if(c==0) {
							gameTf.setText(obj);
							mc.repaint();
						}else if(c==1) {
							unitPriceTf.setText(String.valueOf(obj));
						}else if(c==2) {
							rentAmountTf.setText(String.valueOf(obj));
						}else if(c==3) {
							sellAmountTf.setText(String.valueOf(obj));
						}else if(c==4) {
							minTf.setText(String.valueOf(obj));
						}else if(c==5) {
							maxTf.setText(String.valueOf(obj));
						}else if(c==6) {
							playtimeTf.setText(obj);
						}					
					}  
				}
			}
		});	
		gameGetAll(); 
	}

	
	 
	public void actionPerformed(ActionEvent ae) {
		Object eObj = ae.getSource(); 
		if(eObj==searchTf || eObj==searchBtn) {
				gameSearch();  //검색		
		}else if(eObj== getAllBtn) {
			    gameGetAll(); //전체목록 불러오기		 
		}else if(eObj==addBtn) {
				addGame(); //게임 신규추가하기
		}else if(eObj==editBtn) {
				editGame(); //게임 수정하기
		}else if(eObj==deleteBtn) {
				deleteGame(); //게임 삭제하기 
		}else if(eObj==clearBtn) { 
				clearForm(); //폼 내용 지우기
			 	//paint 연결 끊기
		}else if(eObj==getPicBtn) { 
				uploadPicture();//사진 업로드
		}else if(eObj==removePicBtn) { 
				removePicture();//사진 삭제
		}else if(eObj==gameStockBtn) {
				mainFrm.change("gamePage", adminId);
		}else if(eObj==foodStockBtn) {
				mainFrm.change("foodPage", adminId);
		}else if(eObj instanceof JMenuItem) {
			String eventMenu = ae.getActionCommand();
			if(eventMenu.equals("게임 재고 관리")){
				mainFrm.change("gamePage", adminId);
			}else if(eventMenu.equals("음식 재고 관리")){
				mainFrm.change("foodPage", adminId);
			}else if(eventMenu.equals("회원관리")){
				mainFrm.change("memberPage");
			} 
		}
	}
	
 
	 

	
	//게임검색
	public void gameSearch() {
		String searchWord = searchTf.getText(); 
		
		 if(searchTf.getText().equals("")) { 
		 		JOptionPane.showMessageDialog(centerPane, "게임명을 입력 후 검색을 눌러주세요");
		  	}else {
				GameDAO dao = new GameDAO();
				List<GameVO> searchList = dao.getSearchRecord(searchWord); 
			
				if(searchList.size()==0) {
					JOptionPane.showMessageDialog(centerPane, searchWord+"의 검색 결과가 없습니다.");
				}else {
					model.setRowCount(0); 
					setNewTableList(searchList);
				}
				searchTf.setText("");		
			}
		 }
		
	 
	//새로운 테이블 리스트 만들기 
		public void setNewTableList(List<GameVO> lst) {
			model.setRowCount(0); 
			String commaPrice = null;
			DecimalFormat formatter = new DecimalFormat("###,###"); 
		 	
			for(int i=0; i<lst.size(); i++) {
				GameVO vo= lst.get(i);
				commaPrice = formatter.format(vo.getGameprice()); 
				Object[] data = {vo.getGamename(), commaPrice, 
						         vo.getRentstock(), vo.getSellstock(), 
						         vo.getMinpeople(), vo.getMaxpeople(),vo.getPlaytime()}; 
			 	model.addRow(data);
			}   mc.repaint();  
		
		}
	
		
	//게임 목록 전체 불러오기  
		public void gameGetAll() {
			GameDAO dao = new GameDAO(); 
			List<GameVO> lst= dao.getGameList();  			
			setNewTableList(lst);    
		}
		
		
		//게임 단가에서 콤마 제거 :  String->Int 형변환
		public int removeComma(){
			String StringPrice = unitPriceTf.getText();		
			String newStringPrice = StringPrice.replaceAll(",", "");
			gamePrice = Integer.parseInt(newStringPrice);
			return gamePrice;
		} 
		
		//게임 신규 추가
		public void addGame() { 
			removeComma();
			if(gameTf.getText().equals("") ) {
				JOptionPane.showMessageDialog(centerPane, "게임 명은 반드시 입력하세요.");
			}else {	GameVO vo = new GameVO(gameTf.getText(), gamePrice ,Integer.parseInt(rentAmountTf.getText()), 
		                Integer.parseInt(sellAmountTf.getText()),  Integer.parseInt(minTf.getText()),Integer.parseInt(maxTf.getText()), playtimeTf.getText());
				GameDAO dao = new GameDAO();
				int result = dao.gameInsert(vo); 
			
				if(result>0) { //추가 등록됨
					JOptionPane.showMessageDialog(centerPane, "게임이 등록되었습니다.");
					uploadPicture();
					gameGetAll();
					clearForm();
				}else { // 등록 실패함
					JOptionPane.showMessageDialog(centerPane, "게임 추가 실패하였습니다.");		
				}
			} gameGetAll();
		}
		
		//폼의 값 지우기  
		public void clearForm() {
			 gameTf.setText("");            unitPriceTf.setText("0");
			 rentAmountTf.setText("0");     sellAmountTf.setText("0");
			 minTf.setText("0");            maxTf.setText("0");
			 playtimeTf.setText(""); 	    
			 mc.repaint();
		}
		
		//게임 수정  
		public void editGame() {
			removeComma(); 
			GameVO vo = new GameVO();
			GameDAO dao = new GameDAO(); 
			
			vo.setGamename(gameTf.getText());  
			vo.setGameprice(gamePrice);
			vo.setRentstock(Integer.parseInt(rentAmountTf.getText()));
			vo.setSellstock(Integer.parseInt(sellAmountTf.getText()));
			vo.setMinpeople(Integer.parseInt(minTf.getText()));
			vo.setMaxpeople(Integer.parseInt(maxTf.getText()));
			vo.setPlaytime(playtimeTf.getText()); 
		
			int result = dao.gameUpdate(vo);
			 
			if(result>0) {  
				JOptionPane.showMessageDialog(centerPane, "게임 정보 수정이 완료되었습니다.");
				gameGetAll(); 
			}else {  
				JOptionPane.showMessageDialog(centerPane, "게임 정보 수정에 실패했습니다.");
		}   
			
	}
		 
		//게임 삭제  
		public void deleteGame() {
			String game= gameTf.getText();
			GameDAO dao = new GameDAO();
			int result = dao.gameDelete(game);
			String msg = "게임 정보가 삭제되었습니다.";
			if(result>0) {
				gameGetAll();
				clearForm();
			}else {
				msg = "게임 정보 삭제에 실패했습니다.";
			}
			JOptionPane.showMessageDialog(centerPane, msg);	
		}
		
		
		
		
	//사진 디비에 업로드
	public void uploadPicture( ) {
			String game = gameTf.getText();
			String msg = "사진 업로드 완료되었습니다.";
			GameDAO dao = new GameDAO();
	 	try { 
	 		if(gameTf.getText().equals("")) {
			 	JOptionPane.showMessageDialog(centerPane, "게임을 먼저 선택해주세요.");
			 }else {	 		
				 JFileChooser fc = new JFileChooser(); 
				 FileFilter ff = new FileNameExtensionFilter("image(.jpg, .jpeg, .png, .gif)","png", "jpg",  "gif", "jpeg");
				 fc.setFileFilter(ff);
				 int state = fc.showOpenDialog(centerPane);		
				 if(state ==0) {  
					 File selFile = fc.getSelectedFile(); 
					 int result = dao.imgUpload(game , selFile); 
			 		 if(result>0) {
						 gameGetAll(); 
					 }else msg = "사진 업로드에 실패했습니다.";
					 JOptionPane.showMessageDialog(centerPane, msg);	
				 }
			 } 
		}catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
//사진 화면에 띄우기	
class MyCanvas extends Canvas {  
	
	public void paint(Graphics g) {
				String game = gameTf.getText(); 
				GameDAO dao = new GameDAO();
				byte[] bytes= dao.getpicDB(game);
				
				try { 		
					if(bytes!=null) {
						BufferedImage img2 = ImageIO.read(new ByteArrayInputStream(bytes)); 	 
						int w = img2.getWidth(this);
					 	int h = img2.getHeight(this);
						if(img2!=null) {       						 
				        	g.drawImage(img2, 0,0,110,95, 0,0,w,h,this);
						}
					}
				} catch (Exception e) {
					    e.printStackTrace();
				}
			}
		}


		//사진 삭제
		public void removePicture() {
			GameDAO dao = new GameDAO();
			
			try { 
			 	 if(gameTf.getText().equals("")) {
			 		JOptionPane.showMessageDialog(centerPane, "게임을 먼저 선택해주세요.");
			 	 }else {
			 		dao.picDelete(gameTf.getText()); 			 	
					mc.repaint();
			 		JOptionPane.showMessageDialog(centerPane, "사진 삭제가 완료되었습니다" ); 
			 	 }
			}catch(Exception e) {
				 e.printStackTrace();
				 JOptionPane.showMessageDialog(centerPane, "사진 삭제에 실패하였습니다");
			 }
		}
		
		
		
		
	public static void main(String[] args) {
		 new GameInvenMng() ;
		 }
	
	
	

	
}
 
//사진 화면에 띄우기  
 