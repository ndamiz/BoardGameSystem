package userSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import vo.CallDAO;
import vo.OrderFoodDAO;
import vo.OrderFoodVO;

public class OrderFoodMain extends JPanel implements ActionListener{
	Test test;
	int roomrenum;
	int ordernum;
	String id;
	//관리자버튼
	JPanel askPane = new JPanel();
		JButton backBtn = new JButton("⇦ 뒤로");
		JButton askBtn = new JButton("관리자 호출");
	//음식종류선택버튼	
	JPanel foodSelectPane = new JPanel();
		JPanel selectPane = new JPanel();
			JButton riceBtn = new JButton("식사류");
			JButton bunsikBtn = new JButton("분식류"); 
			JButton snackBtn = new JButton("스낵류");
			JButton drinkBtn = new JButton("음료");	
	//음식수량선택패널	
	String[] titleFood = {"사진", "제품명","가격","담기"};
	
	DefaultTableModel modelFood = new DefaultTableModel(titleFood,0);
	JTable tableFood = new JTable(modelFood) {
         @Override
         public Class<?> getColumnClass(int column)  {
              // row - JTable에 입력된 2차원 배열의  행에 속한다면
              // 모든 컬럼을 입력된 형으로  반환한다.
              
              // 다시말해, 어떤 행이든 간에 입력된  column의 class를 반환하도록 한 것
              return getValueAt(0,  column).getClass();
         }
	};
	JScrollPane foodSp = new JScrollPane(tableFood);
	DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
	
	//음식수량토탈패널
	JPanel showItemPane = new JPanel(new BorderLayout());
		String[] title = {"제품명","가격","수량","금액","삭제"};
		DefaultTableModel modelTotal = new DefaultTableModel(title,0);
		JTable table = new JTable(modelTotal);
		JScrollPane foodTotalSp = new JScrollPane(table);
		
		JPanel nPane = new JPanel(new GridLayout(0,2));
			JPanel nTotalPane = new JPanel(new GridLayout());
				JLabel totalLbl = new JLabel("총금액", JLabel.CENTER);
				JLabel totalInt = new JLabel("0", JLabel.CENTER);
			JButton collectBtn = new JButton("주문하기");
					
	Font btnFnt = new Font("굴림",Font.BOLD,20);
	Font titleFnt = new Font("굴림",Font.BOLD,13);
	public OrderFoodMain() { }
	public OrderFoodMain(Test test, String id) {
		this.test = test;
		this.id = id;
		this.roomrenum = test.roomrenum;
		//상단 관리자 문의 
		askPane.setLayout(null);	
		askPane.setBackground(Color.white);
		askPane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
			backBtn.setBackground(Color.LIGHT_GRAY);
			backBtn.setBounds(10, 10, 100, 40); askPane.add(backBtn);
			askBtn.setBackground(Color.LIGHT_GRAY);
			askBtn.setBounds(330, 10, 100, 40);	askPane.add(askBtn);
		askPane.setBounds(0, 0, 460, 60);
		
		//음식종류 선택 창
		foodSelectPane.setLayout(new GridLayout(0,4)); 
			foodSelectPane.add(riceBtn); 			riceBtn.setFont(btnFnt);
			foodSelectPane.add(bunsikBtn);			bunsikBtn.setFont(btnFnt);
			foodSelectPane.add(snackBtn);			snackBtn.setFont(btnFnt);
			foodSelectPane.add(drinkBtn);			drinkBtn.setFont(btnFnt);
		foodSelectPane.setBounds(0,60,460,100);
		
		//정렬
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = tableFood.getColumnModel();
		for(int i = 1; i<tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
		//음식 담기선택창
		foodSp.setBounds(0,160,460,400);
		tableFood.getTableHeader().setReorderingAllowed(false);
		
		tableFood.setRowHeight(112);
		tableFood.getColumnModel().getColumn(3).setCellEditor(new TableCell());
		tableFood.getColumnModel().getColumn(3).setCellRenderer(new TableCell());
		
		//음식 토탈창
		table.setRowHeight(30);
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnModel().getColumn(4).setCellEditor(new TableDelete());
		table.getColumnModel().getColumn(4).setCellRenderer(new TableDelete());
		
		showItemPane.add(foodTotalSp, "Center");
		showItemPane.add(nPane,"South");
			nPane.add(nTotalPane);
				nTotalPane.add(totalLbl);	totalLbl.setBorder(new LineBorder(Color.black));
				nTotalPane.add(totalInt);	totalInt.setBorder(new LineBorder(Color.black));
			nPane.add(collectBtn);
		
		
		showItemPane.setBounds(0,560,450,200);
				
		foodImageSet("식사");
	
		setLayout(null);
		add(askPane);
		add(foodSelectPane);
		add(foodSp);
		add(showItemPane);
		setVisible(true);
		setSize(460, 800);
		
		riceBtn.addActionListener(this);
		bunsikBtn.addActionListener(this);
		snackBtn.addActionListener(this);
		drinkBtn.addActionListener(this);
		collectBtn.addActionListener(this);
		backBtn.addActionListener(this);
		askBtn.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		if(obj == riceBtn) {
			foodImageSet("식사");
		}else if(obj == bunsikBtn) {
			foodImageSet("분식");
		}else if(obj == snackBtn) {
			foodImageSet("스낵");
		}else if(obj == drinkBtn) {
			foodImageSet("음료");
		}else if(obj == collectBtn) {
			buyGame();
			TableRemoveRowAll();
		}else if(obj==backBtn) {
			test.change("main", id);
		}else if(obj==askBtn) {
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
		
	public void buyGame() {
		//table에 있는 값을 넣기위한 lst
		List<OrderFoodVO> lst = new ArrayList<OrderFoodVO>();

		OrderFoodDAO dao=new OrderFoodDAO();
		int rowCount = table.getRowCount();
		//System.out.println(rowCount + "--> table의 row숫자는? [184줄]");
		// List에다가 table에서 얻은 정보를 대입하는 for문
		insertOrdernumber();
		for(int i=0; i<rowCount; i++) {
			OrderFoodVO vo = new OrderFoodVO();
			vo.setFoodName((String)table.getValueAt(i, 0));
			vo.setFoodPrice((int)table.getValueAt(i, 1));
			vo.setFoodStock((int)table.getValueAt(i, 2));
			lst.add(vo);
		}
		//lst의 size는 내가 주문하는 품목의 갯수임
		for(int j = 0; j<lst.size(); j++) {
			//lst 는 table의 내용
			OrderFoodVO vo2 = lst.get(j);
			//System.out.println(lst.size() + "--> lst 의 크기는? [200줄]");
			//System.out.println(vo2.getFoodName()+vo2.getFoodStock());
			
			//lst2는 db에 있는 내용
			List<OrderFoodVO> lst2 = dao.getOrderFoodStock(vo2.getFoodName());
			//System.out.println(lst2.size()+"-->lst2의크기는?[205줄]");
			OrderFoodVO vo3 = lst2.get(0);
			
			int stock = vo3.getFoodStock()-vo2.getFoodStock();
			
			if(  vo3.getFoodStock()-vo2.getFoodStock() > 0  ) {
				//System.out.println(vo3.getFoodStock()-vo2.getFoodStock() + "db에 있는 수량과 table에서 시킨 수량의 차이[208줄]");
				
				int result=dao.InsertBuyFood(ordernum, roomrenum, vo2.getFoodName(), vo2.getFoodStock());
				//if(result>0) {
				//	JOptionPane.showMessageDialog(this, "디테일 정보 들어감");
				//}else {
				//	JOptionPane.showMessageDialog(this, "디테일은 개뿔 안들어갔음");
				//}	
				int result2 = dao.updateFoodStock(stock, vo2.getFoodName() );
				//if(result2>0) {
				//	JOptionPane.showMessageDialog(this, "갯수가 수정되었습니다");
				//}else {
				//	JOptionPane.showMessageDialog(this, "갯수가 수정되지 않습니다");
				//}
			}else {		
				JOptionPane.showMessageDialog(this, "<HTML>"+ vo2.getFoodName() + "의 재고가 부족하여 의 주문이 취소되었습니다. <BR> 현재 남은 재고는" +vo3.getFoodStock()+ " 개 입니다.");
				//	JOptionPane.showMessageDialog(this, "["+gamename+"]\n룸으로 가져다 드리겠습니다", "구매하기 : "+gamename,JOptionPane.INFORMATION_MESSAGE);
			}	
		}	
		int result=JOptionPane.showConfirmDialog(this, "주문하시겠습니까?(취소된 상품 제외)", "음식주문" ,JOptionPane.YES_NO_OPTION);
		if(result==JOptionPane.YES_OPTION) { // 구매하기 확인 버튼을 누를 때 // 초과된 제품을 제외한 음식이 detail table에 들어감
			JOptionPane.showMessageDialog(this, "주문처리되었습니다", "음식주문", JOptionPane.INFORMATION_MESSAGE);
		}else if(result == JOptionPane.NO_OPTION) { // 구매하기 NO 버튼을 누를 때 // 모든 주문을 삭제함
			int result3 = dao.orderNumDelete(ordernum);
			int result4 = dao.orderDetailDelete(ordernum);

			for(int l = 0; l<lst.size(); l++) {
				//lst 는 table의 내용
				OrderFoodVO vo4 = lst.get(l);
				//System.out.println(lst.size() + "--> lst 의 크기는? [200줄]");
				//System.out.println(vo4.getFoodName()+vo4.getFoodStock());
				
				//lst3는 db에 있는 내용
				List<OrderFoodVO> lst3 = dao.getOrderFoodStock(vo4.getFoodName());
				OrderFoodVO vo5 = lst3.get(0);
				
				int stock2 = vo5.getFoodStock()+vo4.getFoodStock();
				if(vo5.getFoodStock()+vo4.getFoodStock()<=30) {
					int result5 = dao.returnFoodStock(stock2, vo4.getFoodName());
				}
			}
		}
	}
	public void insertOrdernumber() {
		OrderFoodDAO dao = new OrderFoodDAO();
		
		int result = dao.InsertOrder(roomrenum); 
		if(result>0) {

			List<OrderFoodVO> lst = dao.getOrdernum(roomrenum);
			
			OrderFoodVO vo = lst.get(0);
			ordernum = vo.getOrdernum();
			//System.out.println(ordernum + "---> ordernum 의 숫자는??  [270줄]");
			//JOptionPane.showMessageDialog(this, "주문이 완료됬습니다.");	
		}else {
			//JOptionPane.showMessageDialog(this, "주문번호 생성실패");
		}
	}
	public void foodImageSet(String food) {
		modelFood.setRowCount(0);
		
		tableFood.setRowSorter(new TableRowSorter(modelFood));
		tableFood.setAutoCreateRowSorter(true);
		
		String foodCategory = food;
		OrderFoodDAO dao = new OrderFoodDAO();
		List<OrderFoodVO> lst = dao.Foodset(foodCategory);
		for(int i =0; i<lst.size(); i++) {
			OrderFoodVO vo = lst.get(i);
			Object[] obj = {vo.getFoodpic(), vo.getFoodName(), vo.getFoodPrice(), ""};
			modelFood.addRow(obj);
		}
	}
	public void insertItem() {
		try {
			String foodname = (String)tableFood.getValueAt(tableFood.getSelectedRow(), 1); 
	    	int price = (int)tableFood.getValueAt(tableFood.getSelectedRow(), 2); 
	    	int in = 0;
	    	if(table.getRowCount() > 0) {
	        	for (int i = 0; i < table.getRowCount(); i++) {
	        	     if(foodname.equals((String)table.getValueAt(i, 0))){
	        	    	// System.out.println("foodname" + foodname);
	        	    	// System.out.println((String)table.getValueAt(i, 0));
	        	    	// System.out.println("이미 들어가 있는 음식이다");
	        	    	 int num = (int)table.getValueAt(i, 2);
	        	    	 
	        	    	 num++;
	        	    	 table.setValueAt(num, i, 2);
	        	    	 table.setValueAt(num*price, i, 3);
	        	    	 in = 1;
	        	    	
	        	    	 break;
	        	     }
				}
	    	}
	    	if(in == 0) {
	    		Object[] data = {foodname, price, 1, price*1 };	
	    		modelTotal.addRow(data); 
	    	}
		}catch(Exception e) {	}
    	
    	sumPrice();
    }
    public void sumPrice() {
    	int sum = 0;
    	for(int i = 0; i<table.getRowCount(); i++) {
    		sum += (int)modelTotal.getValueAt(i, 3);
    	}
    	totalInt.setText(String.valueOf(sum));
    	
    }
	public void JTableRemoveRow() {
		int row = table.getSelectedRow();
		if(row == -1)
			return;

		DefaultTableModel model = (DefaultTableModel)table.getModel();
		
		// 위에서 삭제한 모델을 재정렬해주는 메서드s
		table.setRowSorter(new TableRowSorter(model));
		table.setAutoCreateRowSorter(true);
		
		model.removeRow(row);
		sumPrice();
	}
	
	public void TableRemoveRowAll() {
		int rowCount = table.getRowCount();
		System.out.println(rowCount);
		for(int i =0; i<rowCount; i++) {
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			table.setRowSorter(new TableRowSorter(model));
			table.setAutoCreateRowSorter(true);
			model.removeRow(0);
		}
		totalLbl.setText("0");
	}

	class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
        JButton jb;     
        public TableCell() {
            jb = new JButton("담기");
            jb.addActionListener(e -> {
               Object obj = e.getSource();
               if(obj == jb) {
            	   insertItem();
            	   ;}
            	            	    
            });
        }
        
        @Override
        public Object getCellEditorValue() {
            return null;
        }
       
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            return jb;
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            return jb;
        }
    }

	class TableDelete extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
        JButton db;     
        public TableDelete() {
            db = new JButton("삭제");
            db.addActionListener(e -> {
               JTableRemoveRow();  	            	    
            });
        }
     
        @Override
        public Object getCellEditorValue() {
            return null;
        }
      
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            return db;
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            return db;
        }
        public boolean isCellEditable(int row, int column)
        {
          return false;
        }
    }
	

}
