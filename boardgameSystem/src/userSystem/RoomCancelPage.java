package userSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import vo.RoomCancelDAO;
import vo.RoomCancelVO;

public class RoomCancelPage extends JPanel implements ActionListener{ 
	Test test;
	Font fnt = new Font("굴림",Font.BOLD,12);
	JPanel askPane = new JPanel();
	JButton backBtn = new JButton("⇦ 뒤로");
	JButton askBtn = new JButton("관리자문의");
	
	String lbl[] = {"예약 번호","입실 날짜","룸 번호","입실 시간","퇴실 시간"};
	
	JPanel presentPane = new JPanel();
		JTable presentTb;
		DefaultTableModel model;
		JScrollPane presentSp;
	
	JButton cancelBtn = new JButton("예약 취소");
	
	JPanel reserveRulePane = new JPanel();
		JTextArea ruleTa = new JTextArea("1. 입장 시간 3시간 전에는 예약 취소가 불가능합니다.");
		String id ="";
	public RoomCancelPage() {}
	public RoomCancelPage(Test test, String userid) {
		id = userid;
		this.test=test;
		//상단 관리자 문의 
		askPane.setLayout(null);	
		askPane.setBackground(Color.white);
		askPane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
			backBtn.setBackground(Color.LIGHT_GRAY);
			backBtn.setBounds(10, 10, 100, 40); askPane.add(backBtn);
			askBtn.setBackground(Color.LIGHT_GRAY);
			askBtn.setBounds(330, 10, 100, 40);	askPane.add(askBtn); askBtn.setEnabled(false);
		askPane.setBounds(0, 0, 460, 60);
		
		//현황판
		presentPane.setLayout(null);
		presentPane.setBackground(Color.white);
		
		model = new DefaultTableModel(lbl,0) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		presentTb=new JTable(model);
		presentTb.setRowSelectionAllowed(true);
		presentSp=new JScrollPane(presentTb);
		
		presentTb.getTableHeader().setFont(new Font("한컴 고딕",Font.BOLD,13));
		presentTb.getTableHeader().setBackground(new Color(232,234,255));
		
		presentTb.setRowHeight(30);
		presentTb.setFont(new Font("한컴 고딕", Font.PLAIN,11));
		presentTb.getParent().setBackground(Color.white);
		
		
		DefaultTableCellRenderer center=new DefaultTableCellRenderer(); // 센터 정렬
		DefaultTableCellRenderer right=new DefaultTableCellRenderer(); // 우측 정렬
		center.setHorizontalAlignment(SwingConstants.CENTER);
		right.setHorizontalAlignment(SwingConstants.RIGHT);
		TableColumnModel tcm=presentTb.getColumnModel();
		
		for(int i=0;i<tcm.getColumnCount();i++) {
			tcm.getColumn(i).setCellRenderer(center);
		}
		
			presentSp.setBounds(60, 40, 340, 260);
		//	presentSp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			
			presentPane.add(presentSp);
			
		presentPane.setBounds(0, 60, 460, 320);
		
		//예약 안내		
		reserveRulePane.setLayout(null);
		reserveRulePane.setBackground(Color.white);
		reserveRulePane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
			ruleTa.setBounds(60,30,340,320); reserveRulePane.add(ruleTa);
			ruleTa.setFont(fnt);
			ruleTa.setBorder(BorderFactory.createLineBorder(Color.BLACK)); //TextArea 테두리 그리기
		reserveRulePane.setBounds(0,380,460,480);
		
				
		setLayout(null);
		add(askPane);
		add(presentPane);
		add(reserveRulePane);
		
		setVisible(true);
		setSize(460, 800);
		
		setReRoomInfo(id); 
		
		backBtn.addActionListener(this);
		askBtn.addActionListener(this);
		presentTb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount()==2) {
					int row=presentTb.getSelectedRow();
					int col=presentTb.getColumnCount();
					
					String inday="", roomnum="", intime="", outtime="";
					int renum=0; 
					
					for(int c=0; c<col;c++) {
						String obj=String.valueOf(presentTb.getValueAt(row, c));
						
						if(c==0) {
							renum=Integer.parseInt(obj);
						}else if(c==1) {
							inday=obj;
						}else if(c==2) {
							roomnum=obj;
						}else if(c==3) {
							intime=obj;
						}else if(c==4) {
							outtime=obj;
						}
					}
					
					SimpleDateFormat sfmt=new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Calendar now=Calendar.getInstance();
					
					now.setTimeInMillis(now.getTimeInMillis() + 3*60*60*1000);
					String date=sfmt.format(now.getTime()); // 현재 시간 포맷 바꿈
					
					String indate=inday+" "+intime;
					
					System.out.println(date);
					System.out.println(indate);
					int a=date.compareTo(indate);
					System.out.println(a);
					
					if(a>0) {
						System.out.println("취소 불가");
						JOptionPane.showMessageDialog(test, "입장 시간 3시간 전에는 취소할 수 없습니다.","예약 취소 불가",JOptionPane.ERROR_MESSAGE);
					}else {
						System.out.println("취소 가능");
						int result=JOptionPane.showConfirmDialog(test, inday+" ("+intime+"~"+outtime+")\n ["+roomnum+"번룸]의 예약을 취소하시겠습니까?", "예약 취소",JOptionPane.OK_CANCEL_OPTION);
						
						if(result==JOptionPane.YES_OPTION) {
							
							JOptionPane.showMessageDialog(test, inday+" ("+intime+"~"+outtime+")\n["+roomnum+"번룸]의 예약이 취소되었습니다.", "예약 취소 완료",JOptionPane.INFORMATION_MESSAGE);
							DeleteReservation(id, renum);
							setReRoomInfo(id);
						}
					}	
				//	System.out.println(date);
				//	System.out.println(indate);
				}
			}
		});
		

	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		if(backBtn==obj) {
			test.change("main", id);
		}else if(askBtn==obj) {
			
		}
	}
	public void setReRoomInfo(String id) {
		RoomCancelDAO dao=new RoomCancelDAO();
		List<RoomCancelVO> lst=dao.SelectRoom(id);
		
		model.setRowCount(0); // JTable의 모든 레코드 지우기
		for(int i=0;i<lst.size();i++) {
			RoomCancelVO vo=lst.get(i);
			
			Object[] data= {vo.getRoomrenum(),vo.getInday(), vo.getRoomnum(), vo.getIntime(), vo.getOuttime()};
			model.addRow(data);
		}
	}
	
	public void DeleteReservation(String id, int roomrenum) {
		RoomCancelDAO dao=new RoomCancelDAO();
		
		int result=dao.ReservationCancel(id, roomrenum);
	}

}
