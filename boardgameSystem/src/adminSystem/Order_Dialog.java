package adminSystem;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vo.OrderDetailVO;

public class Order_Dialog extends JDialog implements ActionListener{
	JPanel titlePane = new JPanel();
		JLabel titleLbl = new JLabel("주문번호 n번");
	JPanel centerPane = new JPanel();
		JLabel roomNumLbl = new JLabel("n번 룸");
		JLabel orderTitleLbl = new JLabel("<음식주문>"); //게임주문
		JPanel orderMenuPane = new JPanel(new GridLayout(0,1));
		JScrollPane orderSp = new JScrollPane(orderMenuPane);
		
	JButton exitBtn = new JButton("닫기");
	JButton doneBtn = new JButton("완료");
	Color co = Color.white;
	Font fnt1 = new Font("돋움체", Font.BOLD, 20);
	Font fnt2 = new Font("돋움체", Font.BOLD, 15);;
	Font fnt3 = new Font("돋움체", Font.PLAIN, 15);;
	public Order_Dialog() { }
	public Order_Dialog(int roomnum ,int ordernum, String gamename, String state) { 
		Order_DialogFrame();
		//orderMenuPane.removeAll();
		titleLbl.setText("주문번호 "+ordernum+"번");
		roomNumLbl.setText(roomnum+"번 룸");
		orderTitleLbl.setText(state);
		JLabel lbl = new JLabel(gamename);
		orderMenuPane.add(lbl);
	}
	public Order_Dialog(int roomnum , int ordernum, List<OrderDetailVO> lst, String state) { 
		Order_DialogFrame();
		//orderMenuPane.removeAll();
		titleLbl.setText("주문번호 "+ordernum+"번");
		roomNumLbl.setText(roomnum+"번 룸");
		if(state.equals("<게임 구매>")) {
			orderTitleLbl.setText(state);
			String gameOrder[] = new String[lst.size()];
			for (int i = 0; i < lst.size(); i++) {
				OrderDetailVO vo = lst.get(i);
				vo.getGamename();
				vo.getOrdercount();
				gameOrder[i] = vo.getGamename() +" : " +vo.getOrdercount() + "개" ;
				JLabel lbl = new JLabel(gameOrder[i]);
				orderMenuPane.add(lbl);
			}
		}else if(state.equals("<음식 주문>")) {
			orderTitleLbl.setText(state);
			String foodOrder[] = new String[lst.size()];
			for (int i = 0; i < lst.size(); i++) {
				OrderDetailVO vo = lst.get(i);
				foodOrder[i] = vo.getFoodname() +" : " +vo.getOrdercount() + "개" ;
				JLabel lbl = new JLabel(foodOrder[i]);
				orderMenuPane.add(lbl);
			}
		}
	}
	public void Order_DialogFrame() {
		titlePane.setSize(300, 50);		titleLbl.setFont(fnt1); 
		titlePane.setBackground(co);
		titlePane.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		
		centerPane.setLayout(null);  centerPane.setBackground(co);
		roomNumLbl.setBounds(20, 5, 300, 40); roomNumLbl.setFont(fnt1);
		orderTitleLbl.setBounds(20, 50, 300, 40);	orderTitleLbl.setFont(fnt2);
		orderSp.setBounds(20, 90, 250, 150); orderMenuPane.setBackground(co);
		
		exitBtn.setBounds(30, 250, 100, 30);
		doneBtn.setBounds(160, 250, 100, 30);
		centerPane.add(roomNumLbl);		centerPane.add(orderTitleLbl);	
		centerPane.add(orderSp);
		centerPane.add(exitBtn);	centerPane.add(doneBtn);
		
		titlePane.add(titleLbl);
		add("North", titlePane);
		add("Center", centerPane);
		setSize(300, 360);
		setVisible(true);
		
		doneBtn.addActionListener(this);
		exitBtn.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==doneBtn || obj==exitBtn) {
			setVisible(false);
		}
	}
}
