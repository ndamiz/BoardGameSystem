package userSystem;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import vo.MemberInfoDAO;

public class LoginPage extends JPanel implements ActionListener{
	Test test;
	
	JPanel askPane = new JPanel();
		JButton askBtn = new JButton("관리자 호출");
	ImageIcon img = new ImageIcon("img/logo.png");
	JLabel logoPane = new JLabel(img);
	JPanel loginPane = new JPanel();
		JLabel idLbl = new JLabel("ID");
		JLabel pwdLbl = new JLabel("PWD");
		JTextField idTf = new JTextField();
		JPasswordField pwdTf = new JPasswordField();
		JButton loginBtn = new JButton("<html>Log"+"<br>&nbsp;In</html>");
		JButton joinBtn = new JButton("회원가입");
		JButton accountBtn = new JButton("계정찾기");
	public LoginPage() {}
	public LoginPage(Test test) {
		this.test=test;
		LoginFrame();
		
		askBtn.setEnabled(false);
		
		//이벤트등록 
		askBtn.addActionListener(this);
		idTf.addActionListener(this);
		pwdTf.addActionListener(this);
		loginBtn.addActionListener(this);
		joinBtn.addActionListener(this);
		accountBtn.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		String pw = new String(pwdTf.getPassword());
		if(obj == idTf || obj == pwdTf || obj == loginBtn) {
			if(idTf.getText().equals("") && pw.equals("")) {
				JOptionPane.showMessageDialog(this, "ID와 PWD를 입력 후 로그인을 확인을 눌러주세요");
			}else if(!idTf.getText().equals("")&& pw.equals("")) {
				JOptionPane.showMessageDialog(this, "PWD를 입력 후 로그인을 확인을 눌러주세요");
			}else if(idTf.getText().equals("")&& !pw.equals("")) {
				JOptionPane.showMessageDialog(this, "ID를 입력 후 로그인을 확인을 눌러주세요");
			}else {
				MemberInfoDAO dao = new MemberInfoDAO();
				int resultId =dao.userLoginIdCheck(idTf.getText());
				if(resultId==0) {
					JOptionPane.showMessageDialog(this, "ID를 다시 확인해 주세요");
				}else if(resultId>0) {
					int resultPw =dao.userLoginPwCheck(idTf.getText(), pw);
					if(resultPw==0) {
						JOptionPane.showMessageDialog(this, "PWD를 다시 확인해 주세요");
					}else if(resultPw>0){
						test.change("main", idTf.getText());
					}
				}
			}
		}else if(obj==joinBtn) {
			test.change("join");	
		}else if(obj==accountBtn) {
			test.change("account");
		}else if(obj==askBtn){
			//관리자문의 
		}
		
	}
	public void LoginFrame() {
		//상단 관리자 문의 
		askPane.setLayout(null);	
		askPane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
		askBtn.setBackground(Color.LIGHT_GRAY);
		askBtn.setBounds(330, 10, 100, 40);	askPane.add(askBtn);
		askPane.setBackground(Color.white);
		
		askPane.setBounds(0, 0, 460, 60);
		//로고 이미지 
		logoPane.setLayout(null);
		logoPane.setBounds(0, 60, 460, 460);
		//로그인창
		loginPane.setLayout(null);			loginPane.setBackground(Color.WHITE);
		idLbl.setBounds(70, 0, 50, 40); 	loginPane.add(idLbl);
		pwdLbl.setBounds(70, 50, 50, 40);	loginPane.add(pwdLbl);
		idTf.setBounds(120, 0, 180, 40);  	loginPane.add(idTf);
		pwdTf.setBounds(120, 50, 180, 40);	loginPane.add(pwdTf);
		loginBtn.setBackground(Color.LIGHT_GRAY);
		
		loginBtn.setBounds(310, 0, 90,90); 	loginPane.add(loginBtn);
		//회원가입, 계정찾기 
		joinBtn.setBackground(Color.LIGHT_GRAY);
		joinBtn.setBounds(110, 110, 100, 40);		loginPane.add(joinBtn);
		accountBtn.setBackground(Color.LIGHT_GRAY);
		accountBtn.setBounds(220, 110, 100, 40); loginPane.add(accountBtn);
		loginPane.setBounds(0, 520, 460, 300);
		

		setLayout(null);
		add(askPane);
		add(logoPane);
		add(loginPane);
		setVisible(true);
		setSize(460, 800);
	}
}
