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


public class StatsPageTotal extends JPanel implements ActionListener{
  MainFrame mainFrm;
   String startYear ;
   String startMonth ;
   String startDay ;
   
   String endYear ;
   String endMonth ;
   String endDay ;
   
   
   Font fnt = new Font("굴림", Font.BOLD, 15);

   //메뉴바
   JMenuBar mb=new JMenuBar();
         JMenu homeMenu = new JMenu("홈");
         JMenu statMenu = new JMenu("통계");
               JMenuItem totalItem=new JMenuItem("매출통계");
               JMenuItem gameM=new JMenu("게임통계");
                     JMenuItem bgameItem=new JMenuItem("대여용 보드게임 통계");
                     JMenuItem sgameItem=new JMenuItem("판매용 보드게임 통계");
               JMenuItem foodM=new JMenu("음식통계");   
                     JMenuItem foodcItem=new JMenuItem("음식 매출 통계(카테고리)");
                     JMenuItem foodiItem=new JMenuItem("음식 매출 통계(아이템별)");
                  
         JMenu itemMenu = new JMenu("재고관리");
         JMenu memberMenu = new JMenu("회원관리");   
   //매출통계 라벨이 들어가는 패널/ 매출통계 라벨
      
    JPanel BigLblpane=new JPanel();    
                                       JLabel BigLbl=new JLabel("매출 통계");
                                      
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
    //테이블에 들어갈 라벨들
   String TStatsLbl[]= {"날짜","룸대여","게임판매","음식판매","총액"};
   
    //테이블 데이터 나오는거랑,총액 나오는거 만들기
    JTable table1,table2;
    JScrollPane sp1;
    DefaultTableModel model1,model2;
    
    
    
    
   public StatsPageTotal() {}
   
   public StatsPageTotal(MainFrame mainFrm) {
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
            month = date.get(Calendar.MONTH);            month2 = date.get(Calendar.MONTH);
            day = date.get(Calendar.DATE);            day2 = date.get(Calendar.DATE);
      ///날짜 넣기
         setCalendar();  setCalendar2();

////////////////////////////

       //라벨패널 임의 배치,패널에 라벨 추가
            BigLbl.setFont(fnt);
             add(BigLblpane);  BigLblpane.setBounds(10, 3, 150, 30);    BigLblpane.add(BigLbl);  

     //정보 테이블   
            model1=new DefaultTableModel(TStatsLbl,0);
            table1=new JTable(model1);
            sp1=new JScrollPane(table1);
      //정보 총액 테이블
            model2=new DefaultTableModel(1, 7);
            table2=new JTable(model2);
            
     //j테이블패널을  추가
            sp1.setBounds(5,70,680,430);
            add(sp1);
           
            
      
      setLayout(null);
      setSize(700,600);
      setVisible(true);
      
      
      SearchBtn.addActionListener(this);
      yearCombo.addActionListener(this);
	  monthCombo.addActionListener(this);
	  dayCombo.addActionListener(this);
	
	  yearCombo2.addActionListener(this);
	  monthCombo2.addActionListener(this);
	  dayCombo2.addActionListener(this);
      
   }

   
   public void actionPerformed(ActionEvent ae) {
      Object obj = ae.getSource();
      if(obj instanceof JButton) {
      if(obj == SearchBtn) {
    	  model1.setRowCount(0);
          getGameTotalPrice();
      }
   }
   }
   
     //년도 셋팅
     public void setCalendar() {
      // 앞에서 고른 달//중에서 가장 마지막 날짜
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
      /*
       *     JComboBox <Integer >yearCombo=new JComboBox<Integer>();
          JComboBox <Integer >monthCombo=new JComboBox<Integer>();
          JComboBox <Integer >dayCombo=new JComboBox<Integer>();
          
          JComboBox <Integer >yearCombo2=new JComboBox<Integer>();
          JComboBox <Integer >monthCombo2=new JComboBox<Integer>();
          JComboBox <Integer >dayCombo2=new JComboBox<Integer>();
       */
      public void getGameTotalPrice() {
         startYear = String.valueOf(yearCombo.getSelectedItem());
         startMonth = String.valueOf(monthCombo.getSelectedItem());
         startDay = String.valueOf(dayCombo.getSelectedItem());
         String setStartDay = startYear + "-" + startMonth + "-" + startDay;
         
         endYear = String.valueOf(yearCombo2.getSelectedItem());
         endMonth = String.valueOf(monthCombo2.getSelectedItem());
         endDay = String.valueOf(dayCombo2.getSelectedItem());
         String setEndDay = endYear + "-" + endMonth + "-" + endDay;
         
       
         
         model1.setRowCount(0);
         DBDAO dao = new DBDAO();
         List<DBVO> lst3 = dao.getRoomTotal(setStartDay, setEndDay);
         List<DBVO> lst = dao.getGameTotal(setStartDay, setEndDay);
         List<DBVO> lst2 = dao.getFoodTotal(setStartDay, setEndDay);
         
         for(int i=0; i<lst.size(); i++) {
        	 //룸
        	 DBVO vo3 = lst3.get(i);
            //게임
            DBVO vo = lst.get(i);
            // 음식
            DBVO vo2 = lst2.get(i);
            
            
            Object[] obj = {vo.getOrderdate().substring(2,10),vo3.getRoomtotal(),vo.getGametotal(),vo2.getFoodtotal(),vo.getGametotal()+vo2.getFoodtotal()+vo3.getRoomtotal()};
            model1.addRow(obj);
         }
         
          
      }


   public static void main(String[] args) {
      new StatsPageTotal();
   }

}
