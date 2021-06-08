package adminSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import vo.AdminDAO;

public class AdminLogin extends JFrame implements ActionListener{
	JPanel backPane = new JPanel();
	ImageIcon img = new ImageIcon("img/logo.png");
	JLabel logoLbl = new JLabel(img);
	JLabel titleLbl = new JLabel("보드게임카페 관리자 프로그램");
	JLabel idLbl = new JLabel("ID");
	JTextField idTf = new JTextField();
	JLabel pwdLbl = new JLabel("PWD");
	JPasswordField pwdTf = new JPasswordField();
	JButton loginBtn = new JButton("LOG IN");	
	
	Font ftn_Title = new Font("돋움체", Font.BOLD, 30);
	Font ftn1 = new Font("돋움체", Font.PLAIN, 20);
	public AdminLogin() {
		adminLoginFrame();
		
		idTf.addActionListener(this);
		pwdTf.addActionListener(this);
		loginBtn.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		if(obj==loginBtn || obj==idTf || obj==pwdTf) {
			loginCheck();
		}
	}
	public void loginCheck() {
		String pw = new String(pwdTf.getPassword());
		if(idTf.getText().equals("") && pw.equals("")) {
			JOptionPane.showMessageDialog(this, "ID와 PWD를 입력해 주세요");
		}else if(idTf.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "ID를 입력해 주세요");
		}else if(pw.equals("")) {
			JOptionPane.showMessageDialog(this, "PWD를 입력해 주세요");
		}else if(idTf.getText().equals("admin") && pw.equals("master1234")) {
			 // 메인메뉴 연결 
			resetDateCheck(); 
			new MainFrame();
			setVisible(false);
		}else {
			JOptionPane.showMessageDialog(this, "ID와 PWD확인 후 다시 로그인해주세요");
		}
	}
	public void loginDateSet() {
		AdminDAO dao = new AdminDAO();
		int result = dao.adminLoginDate();
	}
	public void resetDateCheck() {
		AdminDAO dao = new AdminDAO();
		int result = dao.orderSqResetCheck();
		if(result>0) { //이미 로그인 한것
			//시퀸스 삭제, 시퀸스 새로 생성  하고 ->데이트 추가 
			System.out.println("오늘 로그인 했습니다 ");
			
		}else{
			System.out.println("오늘 로그인 한적 없습니다 ");
			int result1=dao.orderSqDel();
			if(result1>0) { //시퀸스 삭제완료 
				int result2=dao.orderSqCreat(); //시퀸스 새로 생성 
				if(result2>0) {
					dao.orderNumTblDel(); //오더넘버 삭제 리셋
					System.out.println("order(주문번호) 시퀸스 삭제 후 재생성 완료");
					loginDateSet(); //오늘 로그인 처음이라 로그인 날짜 인설트 남긴다 
				}else {
					System.out.println("시퀸스재생성안대따 ? ");
				}
			}else{ //시퀸스 삭제안댐 
				System.out.println("시퀸스삭제안댐 ");
			}
		}
	}
	public void adminLoginFrame() {
		logoLbl.setBounds(10, 50, 460, 460);
		titleLbl.setFont(ftn_Title);
		titleLbl.setBounds(300, 150, 500, 50);
		idLbl.setBounds(450, 250, 60, 40); idLbl.setFont(ftn1);
		idTf.setBounds(510, 250, 150, 40);
		pwdLbl.setBounds(450, 300, 60, 40); pwdLbl.setFont(ftn1);
		pwdTf.setBounds(510, 300, 150, 40);
		
		loginBtn.setBounds(560, 350, 100, 40);
		
		backPane.add(loginBtn);
		backPane.add(idLbl);backPane.add(idTf);
		backPane.add(pwdLbl);backPane.add(pwdTf);
		backPane.add(titleLbl);
		backPane.add(logoLbl); 
		backPane.setBackground(Color.white);
		
		backPane.setLayout(null);
		add(backPane);
		setSize(700,600);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	public static void main(String[] args) {
		new AdminLogin();
	}


}
