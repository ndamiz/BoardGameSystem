package adminSystem;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RentGame_Dialog extends JFrame{
	JPanel titlePane = new JPanel();
		JLabel titleLbl = new JLabel("주문번호 n번");
	JPanel centerPane = new JPanel();
		JLabel roomNumLbl = new JLabel("n번 룸");
		JLabel orderTitleLbl = new JLabel("<게임 요청>");
		JLabel orderGameLbl = new JLabel("게임이름 ");
		JLabel returnTitleLbl = new JLabel("<반납게임>");
		JLabel returnGameLbl = new JLabel("반납하는 게임이름");
		
	JButton exitBtn = new JButton("닫기");
	JButton doneBtn = new JButton("완료");
	Color co = Color.white;
	Font fnt1 = new Font("돋움체", Font.BOLD, 20);
	Font fnt2 = new Font("돋움체", Font.BOLD, 15);;
	Font fnt3 = new Font("돋움체", Font.PLAIN, 15);;
	public RentGame_Dialog() {
		titlePane.setSize(300, 50);		titleLbl.setFont(fnt1); 
		titlePane.setBackground(co);
		titlePane.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		
		centerPane.setLayout(null);  centerPane.setBackground(co);
		roomNumLbl.setBounds(20, 5, 300, 40); roomNumLbl.setFont(fnt1);
		orderTitleLbl.setBounds(20, 50, 300, 40);	orderTitleLbl.setFont(fnt2);
		orderGameLbl.setBounds(20, 90, 300, 40); orderGameLbl.setFont(fnt3);
		
		returnTitleLbl.setBounds(20, 150, 300, 40); returnTitleLbl.setFont(fnt2);
		returnGameLbl.setBounds(20, 190, 300, 40); returnGameLbl.setFont(fnt3);
		
		exitBtn.setBounds(30, 250, 100, 30);
		doneBtn.setBounds(160, 250, 100, 30);
		centerPane.add(roomNumLbl);		centerPane.add(orderTitleLbl);	
		centerPane.add(orderGameLbl);	centerPane.add(returnTitleLbl);
		centerPane.add(returnGameLbl);
		centerPane.add(exitBtn);	centerPane.add(doneBtn);
		
		titlePane.add(titleLbl);
		add("North", titlePane);
		add("Center", centerPane);
		setSize(300, 360);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new RentGame_Dialog();

	}

}
