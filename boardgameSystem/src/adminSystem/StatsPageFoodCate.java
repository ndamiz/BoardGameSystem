package adminSystem;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vo.DBDAO;
import vo.DBVO;

public class StatsPageFoodCate extends JPanel implements ActionListener{
	 MainFrame mainFrm;
	Font fnt = new Font("굴림", Font.BOLD, 15);

	
	//매출통계 라벨이 들어가는 패널/ 매출통계 라벨
		
    JPanel BigLblpane=new JPanel();    JLabel BigLbl4=new JLabel("음식매출(카테고리)");
  
                                      
    //콤보박스가 들어갈 패널
    JPanel selectPane=new JPanel();
   
    
		    //년도 콤보박스/콤보박스 사이에 들어갈 라벨과 버튼
		    JComboBox <Integer >yearCombo=new JComboBox<Integer>();
		    JComboBox <Integer >monthCombo=new JComboBox<Integer>();
		    JComboBox <Integer >dayCombo=new JComboBox<Integer>();
		    
		    JComboBox <Integer >yearCombo2=new JComboBox<Integer>();
		    JComboBox <Integer >monthCombo2=new JComboBox<Integer>();
		    JComboBox <Integer >dayCombo2=new JComboBox<Integer>();
		    JButton SearchBtn = new JButton("검색");
		    JLabel SubLbl=new JLabel("~");   
    //콤보박스 안에 들어갈 날짜 설정
		    Calendar date;
		    int year,year2;
		    int month,month2;
		    int day,day2;
		    int startYear, startMonth, startDay, endYear, endMonth, endDay;
		    String start, startMon, end, endMon;
		    
    //테이블에 들어갈 라벨들
		    String TStatsLbl4[]= {"날짜","분식류","식사류","스낵류","음료류","총액"};
   
    //테이블 데이터 나오는거랑,총액 나오는거 만들기
    JTable table1;
    JScrollPane sp1;
    DefaultTableModel model1;
    
    
    
    public StatsPageFoodCate() {}
	
	public StatsPageFoodCate(MainFrame mainFrm) {
		   this.mainFrm = mainFrm;
    	
    	//날짜 선택패널 위쪽에 배치, 패널에 콤보박스와 버튼 등 추가
    
    	selectPane.setBounds(150,25,450,30);
        add(selectPane);
		    	selectPane.add(yearCombo); selectPane.add(monthCombo); selectPane.add(dayCombo); selectPane.add(SubLbl);
		    	selectPane.add(yearCombo2); selectPane.add(monthCombo2);  selectPane.add(dayCombo2);  
		    	BigLblpane.add(SearchBtn);

	    //캘린더 변수
			date=Calendar.getInstance();
				year=date.get(Calendar.YEAR);                year2=date.get(Calendar.YEAR);
				month = date.get(Calendar.MONTH)+1;            month2 = date.get(Calendar.MONTH)+1;
				day = date.get(Calendar.DATE);            day2 = date.get(Calendar.DATE);
		///날짜 넣기
			setCalendar();  setCalendar2();

////////////////////////////

       //라벨패널 임의 배치,패널에 라벨 추가
				BigLbl4.setFont(fnt);
		    	add(BigLblpane);  BigLblpane.setBounds(10, 3, 150, 30);    BigLblpane.add(BigLbl4);  

	  //정보 테이블	
				model1=new DefaultTableModel(TStatsLbl4,0);
				table1=new JTable(model1);
				sp1=new JScrollPane(table1);
  
				
				
	  //j테이블패널을  추가
				sp1.setBounds(5,70,680,430);
				add(sp1);
				
				
		
		setLayout(null);
		setSize(700,600);
		setVisible(true);
	
		startYear=(int)yearCombo.getSelectedItem();
	      startMonth=(int)monthCombo.getSelectedItem();
	      if(Integer.toString(startMonth).length()==1) { // 1~9월일 경우
	         startMon="0"+startMonth; // 01~09월로 변경
	      }else {
	         startMon=Integer.toString(startMonth);
	      }
	      startDay=(int)dayCombo.getSelectedItem();
	      
	      start=startYear+"-"+startMon+"-"+startDay; // 문자열 start 변수에 시작 년월일을 저장
	      
	      
	      // 종료일
	      endYear=(int)yearCombo2.getSelectedItem();
	      endMonth=(int)monthCombo2.getSelectedItem();
	      if(Integer.toString(endMonth).length()==1) { // 1~9월일 경우
	         endMon="0"+endMonth; // 01~09월로 변경
	      }else {
	         endMon=Integer.toString(endMonth);
	      }
	      endDay=(int)dayCombo2.getSelectedItem();
	      
	      end=endYear+"-"+endMon+"-"+endDay; // 문자열 end 변수에 종료 년월일을 저장

	      SearchBtn.addActionListener(this);
	      yearCombo.addActionListener(this);
	      monthCombo.addActionListener(this);
	      dayCombo.addActionListener(this);
	   
	      yearCombo2.addActionListener(this);
	      monthCombo2.addActionListener(this);
	      dayCombo2.addActionListener(this);
	   

	
	//////////////////////////
	
	}
	
	   public void actionPerformed(ActionEvent ae) {
		      Object eventObj=ae.getSource();
		      if(eventObj instanceof JButton) {
		         if(eventObj==SearchBtn) {
		            model1.setRowCount(0);
		            getdataAll10();
		         }
		      }else if(eventObj instanceof JComboBox) {
		         startYear=(int)yearCombo.getSelectedItem();
		         startMonth=(int)monthCombo.getSelectedItem();
		         startDay=(int)dayCombo.getSelectedItem();
		         
		         endYear=(int)yearCombo2.getSelectedItem();
		         endMonth=(int)monthCombo2.getSelectedItem();
		         endDay=(int)dayCombo2.getSelectedItem();
		         
		         // 시작 일
		         
		         if(Integer.toString(startMonth).length()==1) { // 1~9월일 경우
		            startMon="0"+startMonth; // 01~09월로 변경
		         }else {
		            startMon=Integer.toString(startMonth);
		         }
		         start=startYear+"-"+startMon+"-"+startDay; // 문자열 start 변수에 시작 년월일을 저장
		         /////////////////////////////////////////////////////////////////////////////////
		         // 종료 일
		         if(Integer.toString(endMonth).length()==1) { // 1~9월일 경우
		            endMon="0"+endMonth; // 01~09월로 변경
		         }else {
		            endMon=Integer.toString(endMonth);
		         }
		         end=endYear+"-"+endMon+"-"+endDay; // 문자열 end 변수에 종료 년월일을 저장
		         
		      }
		   }
/*
public void actionPerformed(ActionEvent ae) {
   	 String eventBtn=ae.getActionCommand();
   	 if(eventBtn.equals("검색")) {
   		getdataAll10();
   		
   	 }
   	 }
	
*/

 //부분 통계
	   		public void getdataAll10() {
						
					DBDAO dao10=new DBDAO();
					List<DBVO>lst10=dao10.DBAllSelect10(start,end);
						//setNewTableList10(lst10);
					for(int i=0; i<lst10.size(); i++) {
							
			   		DBVO vo10 =lst10.get(i);
					
				
					
					Object[]d= {vo10.getDate(),vo10.getFood1(),vo10.getFood2(),vo10.getFood3(),vo10.getFood4(),vo10.getTotal() };
					model1.addRow(d);
						
	   			}
	   		}

/*
	   		public void setNewTableList10(List<DBVO> lst10) {
	   			model1.setRowCount(0);
	
	 
	   			for(int i=0; i<lst10.size(); i++) {
	
	   				DBVO vo10 =lst10.get(i);
			
		
			
			Object[]d= {vo10.getDate(),vo10.getFood1(),vo10.getFood2(),vo10.getFood3(),vo10.getFood4(),vo10.getTotal() };
			model1.addRow(d);
}

}
*/
	
	

	
	
	
	  //년도 셋팅
	  public void setCalendar() {
		  //date.set(year, month-1,1);
		  
		  //int lastDay = date.getActualMaximum(Calendar.DAY_OF_MONTH);
		  date.set(Calendar.MONTH,5-1);
		  int lastDay=date.getActualMaximum(Calendar.DAY_OF_MONTH);
		  
		  date.set(Calendar.DATE,lastDay);
		  
				for(int i=year-10; i<year+20; i++) {
					yearCombo.addItem(i);
				}
				yearCombo.setSelectedItem(year);
				
				for(int i=1; i<=12; i++) {
					monthCombo.addItem(i);
				}
				monthCombo.setSelectedItem(month);
				
				for(int i=1; i<=lastDay; i++) {
					dayCombo.addItem(i);
				}
				dayCombo.setSelectedItem(day);
				
				
				
				
			}
	  
	  
	  public void setCalendar2() {
		  int lastDay2 = date.getActualMaximum(Calendar.DATE);
		 
			for(int i=year2-50; i<year2+20; i++) {
				yearCombo2.addItem(i);
			}
			yearCombo2.setSelectedItem(year2);
			
			for(int i=1; i<=12; i++) {
				monthCombo2.addItem(i);
			}
			monthCombo2.setSelectedItem(month2);
			
			for(int i=1; i<=lastDay2; i++) {
				dayCombo2.addItem(i);
			}
			dayCombo2.setSelectedItem(day2);
			
			
			
		}
			
			
	
	


	public static void main(String[] args) {
		new StatsPageFoodCate();
	}

}
