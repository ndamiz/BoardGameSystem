package adminSystem;
 
//2/15 2:30까지

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
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
import javax.swing.text.NumberFormatter;

 
import vo.FoodDAO;
import vo.FoodVO;
import vo.GameDAO;

public class FoodInvenMng extends JPanel implements ActionListener,ItemListener{
	MainFrame mainFrm;
	Font fnt=new Font("돋움",Font.BOLD,13);
	JMenuBar mb=new JMenuBar();
	JMenu homeMenu=new JMenu("홈");
	JMenu statsMenu=new JMenu("통계");
	JMenu mngMenu=new JMenu("관리");
	JMenu stockMenu=new JMenu("재고관리");
		JMenuItem invenMngItem=new JMenuItem("게임재고관리");
		JMenuItem foodMngItem=new JMenuItem("음식재고관리");
	JMenu memberMenu=new JMenu("회원관리");
	static JPanel foodbigPane=new JPanel(new BorderLayout());
	JPanel northPane=new JPanel(new BorderLayout());
		JButton gameStockBtn = new JButton("게임 재고 관리");
		JButton foodStockBtn = new JButton("음식 재고 관리");
	JPanel norNorthPane=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel norPane2=new JPanel(new BorderLayout());
		JPanel norLeftPane=new JPanel(new GridLayout(5,1));
			JPanel catePane= new JPanel(new FlowLayout(FlowLayout.LEFT) );
				JLabel cateLbl = new JLabel(" 카테고리");
				JComboBox<String> foodCombo;
				String comboSelect;
				String[] foodCate = {"분식", "식사" ,"스낵","음료"};  
			JPanel foodNamePane=new JPanel(new FlowLayout(FlowLayout.LEFT));
				JLabel foodNameLbl = new JLabel(" 음식 명");
				JTextField foodNameTf = new JTextField(); 
			JPanel unitPricePane=new JPanel(new FlowLayout(FlowLayout.LEFT));
				JLabel unitPriceLbl = new JLabel(" 단가");
				NumberFormatter a = new NumberFormatter(NumberFormat.getNumberInstance()); 
				JFormattedTextField unitPriceTf  = new JFormattedTextField(a);
				int foodPrice;
			JPanel foodStockPane=new JPanel(new FlowLayout(FlowLayout.LEFT));
				JLabel foodStockLbl = new JLabel(" 재고량");
				JFormattedTextField foodStockTf   = new JFormattedTextField(a);
				JLabel emptyLbl = new JLabel ("                                                                                   ");
				JButton clearBtn = new JButton("내용 지우기");
			JPanel picPane=new JPanel();
				JPanel showPicPane = new JPanel(new BorderLayout()); 
				JButton getPicBtn = new JButton("사진 업로드");
				JButton removePicBtn = new JButton("사진 삭제");
				MyCanvas mc = new MyCanvas(); 
		JPanel norRightPane=new JPanel();
			JButton addBtn = new JButton("신규추가");
			JButton editBtn = new JButton("수정");
			JButton deleteBtn = new JButton("삭제");
	JPanel centerPane=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JTextField  searchTf = new JTextField();
		JButton searchBtn = new JButton("검색");
		JButton getAllBtn = new JButton("전체목록");
	
	JScrollPane sp = new JScrollPane();
		String lbl[] = {"음식명", "분류","단가","재고량"};
		DefaultTableModel model;
		JTable foodTable=new JTable();
		String id;
	
	public FoodInvenMng() {}
	public FoodInvenMng(MainFrame mainFrm, String adminId) { 
		this.mainFrm = mainFrm;
		this.id = adminId;
	  	 
		//Numberformat textfield 세팅
		a.setMaximum(100000);              a.setAllowsInvalid(false);
		
		//맨 위 두개 버튼 
		gameStockBtn.setFont(fnt);         norNorthPane.add(gameStockBtn);
		foodStockBtn.setFont(fnt);         norNorthPane.add(foodStockBtn);
		norNorthPane.setPreferredSize(new Dimension(700,35));
		
		//음식 정보 입력창
		cateLbl.setPreferredSize(new Dimension(60,25));
		foodNameLbl.setPreferredSize(new Dimension(60,25));
		unitPriceLbl.setPreferredSize(new Dimension(60,25));
		foodStockLbl.setPreferredSize(new Dimension(60,25));
		
		norLeftPane.setPreferredSize(new Dimension(300,175));
		cateLbl.setFont(fnt);              catePane.add(cateLbl); 			    
		foodCombo = new JComboBox<String>(foodCate); 
		foodCombo.setFont(fnt);              
		catePane.add(foodCombo);		 
		foodNameLbl.setFont(fnt);   		foodNamePane.add(foodNameLbl);
		foodNameTf.setFont(fnt);        	foodNamePane.add(foodNameTf); 
		unitPriceLbl.setFont(fnt);     		unitPricePane.add(unitPriceLbl);
		unitPriceTf.setFont(fnt);      		unitPricePane.add(unitPriceTf);   
		foodStockLbl.setFont(fnt); 			foodStockPane.add(foodStockLbl);
		foodStockTf.setFont(fnt); 			foodStockPane.add(foodStockTf); 
		foodStockPane.add(emptyLbl);	

		picPane.setLayout(null); 
		picPane.add(showPicPane);
		showPicPane.add(mc); 		
		showPicPane.setBounds(95,10,110,95);
		getPicBtn.setFont(fnt);      removePicBtn.setFont(fnt); 		 
		picPane.add(getPicBtn);      picPane.add(removePicBtn);
		getPicBtn.setBounds(95,115,110,25);
		removePicBtn.setBounds(95,145,110,25);	
		
		foodNameTf.setPreferredSize(new Dimension(200,25));
		unitPriceTf.setPreferredSize(new Dimension(200,25));
		foodStockTf.setPreferredSize(new Dimension(100,25));
		
		norLeftPane.add(catePane);
		norLeftPane.add(foodNamePane);
		norLeftPane.add(unitPricePane);
		norLeftPane.add(foodStockPane); 
		
		norRightPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		norPane2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		//위의 오른쪽 버튼  		
		norRightPane.setPreferredSize(new Dimension(170,80));
		norRightPane.setLayout(null); 
		addBtn.setBounds(25, 15, 110,25);           addBtn.setFont(fnt);
		editBtn.setBounds(25, 55, 110,25);     	    editBtn.setFont(fnt);
		deleteBtn.setBounds(25, 95, 110,25);        deleteBtn.setFont(fnt); 
		clearBtn.setBounds(25,135,110,25);          clearBtn.setFont(fnt);
	 
		norRightPane.add(addBtn);      				norRightPane.add(editBtn);	
		norRightPane.add(deleteBtn); 				norRightPane.add(clearBtn);  
		
		 
		//중간 검색 패널  
		searchTf.setPreferredSize(new Dimension(200,25)); 
		centerPane.add(searchTf);		searchTf.setFont(fnt);
		searchBtn.setBounds(220,10,80,25);
		centerPane.add(searchBtn);		searchBtn.setFont(fnt);
		getAllBtn.setBounds(310,10,100,25);
		centerPane.add(getAllBtn);		getAllBtn.setFont(fnt);
		
		//아래 테이블 패널	    
		model = new DefaultTableModel(lbl,0);
		foodTable = new JTable(model);
		sp = new JScrollPane(foodTable); 
		sp.setPreferredSize(new Dimension(700,280));
		//테이블 정렬
		DefaultTableCellRenderer center=new DefaultTableCellRenderer();  
		DefaultTableCellRenderer right=new DefaultTableCellRenderer(); 
		center.setHorizontalAlignment(SwingConstants.CENTER);
		right.setHorizontalAlignment(SwingConstants.RIGHT);
		TableColumnModel tcm = foodTable.getColumnModel();
		tcm.getColumn(0).setCellRenderer(center);
		tcm.getColumn(1).setCellRenderer(center);
		tcm.getColumn(2).setCellRenderer(right);
		tcm.getColumn(3).setCellRenderer(center);
	 
		norPane2.setPreferredSize(new Dimension(695,182)); 
		norPane2.add("East", norRightPane);
		norPane2.add(picPane); 
		norPane2.add("West",norLeftPane);
		northPane.add("South", norPane2);
		northPane.add("North",norNorthPane);
		foodbigPane.add("North",northPane);
		foodbigPane.add("Center",centerPane);	
		foodbigPane.add("South",sp); 
		add("South", foodbigPane); 
		setSize(700,600);
		setVisible(true);
	 
		//이벤트 등록
		addBtn.addActionListener(this);
		editBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		searchTf.addActionListener(this);
		searchBtn.addActionListener(this);
		getAllBtn.addActionListener(this);
		foodCombo.addItemListener(this);
		clearBtn.addActionListener(this);
		getPicBtn.addActionListener(this);
		removePicBtn.addActionListener(this);
		gameStockBtn.addActionListener(this);
		foodStockBtn.addActionListener(this);
		invenMngItem.addActionListener(this); 
		memberMenu.addActionListener(this);
		
	 	foodTable.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) { 
				if(me.getButton()==1) { 
					int row = foodTable.getSelectedRow(); 
					int col = foodTable.getColumnCount();
					for(int c=0;c<col;c++) {	
						String obj = String.valueOf(foodTable.getValueAt(row, c));
						if(c==0) {
							foodNameTf.setText(obj);
							mc.repaint();
						}else if(c==1) {
							foodCombo.setSelectedItem(obj);
						}else if(c==2) {
							unitPriceTf.setText(obj);
						}else if(c==3) {
							foodStockTf.setText(String.valueOf(obj));
						} 		
					}
				}
			}
		}); 
		foodGetAll();
	}
	
	 
	public void actionPerformed(ActionEvent ae) {
		Object eObj = ae.getSource(); 
		if(eObj==searchTf || eObj==searchBtn) {
				foodSearch();  //검색		
		}else if(eObj== getAllBtn) {
			    foodGetAll(); //전체목록 불러오기		 
		}else if(eObj==addBtn) {
				addFood(); //음식 신규추가하기
		}else if(eObj==editBtn) {
				editFood(); //음식 수정하기
		}else if(eObj==deleteBtn) {
				deleteFood(); //음식 삭제하기
		}else if(eObj==clearBtn) {
				clearForm(); //폼 내용 지우기
		}else if(eObj==getPicBtn) { 
				uploadPicture();//사진 업로드
		}else if(eObj==removePicBtn) { 
				removePicture();//사진 삭제
		}else if(eObj==gameStockBtn) {
				mainFrm.change("gamePage", id);
		}else if(eObj==foodStockBtn) {
				mainFrm.change("foodPage", id);
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
 	 
		//음식검색
		public void foodSearch() {
			String searchWord = searchTf.getText();
			if(searchTf.getText().equals("")) { 
				JOptionPane.showMessageDialog(centerPane, "음식명을 입력 후 검색을 눌러주세요");
			}else {
				FoodDAO dao = new FoodDAO();
				List<FoodVO> searchList = dao.getSearchRecord(searchWord); 
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
		public void setNewTableList(List<FoodVO> lst) {
			model.setRowCount(0);
			String commaPrice = null;
			DecimalFormat formatter = new DecimalFormat("###,###"); 
			
			for(int i=0; i<lst.size(); i++) {
				FoodVO vo= lst.get(i);
				commaPrice = formatter.format(vo.getFoodPrice()); 
				Object[] data = {vo.getFoodName(), vo.getFoodCategory(), 
						commaPrice, vo.getFoodStock()};
			 	model.addRow(data);
			}	mc.repaint();
		}

		//음식 목록 전체 불러오기  
		public void foodGetAll() {
			FoodDAO dao = new FoodDAO(); 
			List<FoodVO> lst= dao.getFoodList();  			
			setNewTableList(lst);     
		}
		

		//음식 단가에서 콤마 제거 :  String->Int 형변환
		public int removeComma(){
			String StringPrice = unitPriceTf.getText();		
			String newStringPrice = StringPrice.replaceAll(",", "");
			foodPrice = Integer.parseInt(newStringPrice);
			return foodPrice;
		} 
		 
				
		
		//음식 신규 추가
		public void addFood() {
			removeComma();
			FoodVO vo = new FoodVO(foodNameTf.getText(), foodCombo.getSelectedItem(), foodPrice, 
					Integer.parseInt(foodStockTf.getText())); 
			 
			if( foodNameTf.getText().equals("") ) {
				JOptionPane.showMessageDialog(centerPane, "음식 명은 반드시 입력하세요.");
			}else {
				FoodDAO dao = new FoodDAO();
				int result = dao.foodInsert(vo);
				if(result>0) {  
					JOptionPane.showMessageDialog(centerPane, "음식이 등록되었습니다.");
					uploadPicture();
					foodGetAll();
					clearForm();
				}else {  
					JOptionPane.showMessageDialog(centerPane, "음식 추가 실패하였습니다.");		
				}
			} 
		}

		//폼의 값 지우기  
		public void clearForm() {
			 foodNameTf.setText("");        
			 unitPriceTf.setText("0");      foodStockTf.setText("0"); 
			 mc.repaint();
		}
		
		//음식 수정  
		public void editFood() {
			removeComma();
			FoodVO vo = new FoodVO();
			 
			vo.setFoodName(foodNameTf.getText());  
			vo.setFoodCategory(foodCombo.getSelectedItem());
			vo.setFoodPrice(foodPrice);
			vo.setFoodStock(Integer.parseInt(foodStockTf.getText())); 
			
			FoodDAO dao = new FoodDAO();
			int result = dao.foodUpdate(vo);
		 	
			if(result>0) {  
				JOptionPane.showMessageDialog(centerPane, "음식 정보 수정이 완료되었습니다.");
				foodGetAll(); 
			}else { 
			JOptionPane.showMessageDialog(centerPane, "음식 정보 수정에 실패했습니다.");
		} 	
	}
				
		//음식 삭제  
		public void deleteFood() {
			String food= foodNameTf.getText();
			FoodDAO dao = new FoodDAO();
			int result = dao.foodDelete(food);
			String msg = "음식 정보가 삭제되었습니다.";
			if(result>0) {
				foodGetAll();
				clearForm();
			}else {
				msg = "음식 정보 삭제에 실패했습니다.";
			}
			JOptionPane.showMessageDialog(centerPane, msg);	
		}
		
		
		//사진 디비에 업로드
		public void uploadPicture( ) {
			String food = foodNameTf.getText();
			String msg = "사진 업로드 완료되었습니다.";
			FoodDAO dao = new FoodDAO();
		 	try { 
		 		if(foodNameTf.getText().equals("")) {
				 	JOptionPane.showMessageDialog(centerPane, "음식을 먼저 선택해주세요.");
				 }else {	 		
					 JFileChooser fc = new JFileChooser(); 
					 FileFilter ff = new FileNameExtensionFilter("image(.jpg, .jpeg, .png, .gif)","png", "jpg",  "gif", "jpeg");
					 fc.setFileFilter(ff);
					 int state = fc.showOpenDialog(centerPane);		
					 if(state ==0) {  
						 File selFile = fc.getSelectedFile(); 
						 int result = dao.imgUpload(food , selFile); 
				 		 if(result>0) {
							 foodGetAll(); 
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
				String food = foodNameTf.getText(); 
				FoodDAO dao = new FoodDAO();
				byte[] bytes= dao.getpicDB(food);
			 	try { 		
					if(bytes!=null) {
						BufferedImage img2 = ImageIO.read(new ByteArrayInputStream(bytes)); 	 
						int w = img2.getWidth(this);
					 	int h = img2.getHeight(this);
						if(img2!=null) {       						 
				        	g.drawImage(img2, 0,0,110,95, 0,0,w,h,this);
						}
					}
				}catch (Exception e) {
					    e.printStackTrace();
				}
			}
		}


		//사진 삭제
		public void removePicture() {
			FoodDAO dao = new FoodDAO(); 
			try { 
			 	 if(foodNameTf.getText().equals("")) {
			 		JOptionPane.showMessageDialog(centerPane, "음식을 먼저 선택해주세요.");
			 	 }else {
			 		dao.picDelete(foodNameTf.getText()); 			 	
					mc.repaint();
			 		JOptionPane.showMessageDialog(centerPane, "사진 삭제가 완료되었습니다" ); 
			 	 }
			}catch(Exception e) {
				 e.printStackTrace();
				 JOptionPane.showMessageDialog(centerPane, "사진 삭제에 실패하였습니다");
			 }
		}
			
	 public static void main(String[] args) {
		 new FoodInvenMng() ;
 	}

 	public void itemStateChanged(ItemEvent e) {}
}