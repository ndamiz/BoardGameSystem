package userSystem;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import vo.MemberInfoDAO;

public class PwChangeDialog extends JDialog implements ActionListener{
		JPanel pwChangePane = new JPanel();
		JLabel changeLbl = new JLabel("변경하실 PWD를 입력해 주세요");
		JLabel pwLbl = new JLabel("PWD");
		JLabel rePwLbl = new JLabel("PWD확인");
		JPasswordField pwTf = new JPasswordField();
		JPasswordField rePwTf = new JPasswordField();
		JLabel pwdConditionLbl = new JLabel("<html>영문, 숫자, 특수문자 (5~15자)<br>` ~ ! @ # $ % ^ & * ? </html>");
		JButton changeBtn = new JButton("변경");
		JButton cancleBtn = new JButton("취소");
		String id;
		Font fnt1 = new Font("돋움체", Font.BOLD, 15); 
		Font fnt2 = new Font("돋움체", Font.PLAIN, 10); 
		String pw ="" , repw="";
	public PwChangeDialog() {}
	public PwChangeDialog(String userid) {
		setLayout(new BorderLayout());
		id = userid;
		pwChangePane.setLayout(null);
		changeLbl.setFont(fnt1);
		changeLbl.setBounds(50, 10, 300, 50);
		pwLbl.setBounds(30, 60, 70, 40);	pwTf.setBounds(100, 65, 150, 30);
		rePwLbl.setBounds(30, 100, 70, 30);	rePwTf.setBounds(100, 105, 150, 30);
		
		pwdConditionLbl.setBounds(100, 135, 150, 30); 		pwdConditionLbl.setFont(fnt2);
		changeBtn.setBounds(30, 170, 100, 40);	cancleBtn.setBounds(150, 170, 100, 40);
		pwChangePane.add(changeLbl); pwChangePane.add(pwLbl); pwChangePane.add(pwTf);
		pwChangePane.add(rePwLbl); pwChangePane.add(rePwTf);
		pwChangePane.add(pwdConditionLbl);
		pwChangePane.add(changeBtn);	pwChangePane.add(cancleBtn);
		add(pwChangePane);
		
		
		pwTf.addActionListener(this);
		rePwTf.addActionListener(this);
		changeBtn.addActionListener(this);
		cancleBtn.addActionListener(this);
		
		setSize(300, 250);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		if(obj==pwTf || obj==rePwTf || obj==changeBtn) {
			changePw(id);
		}else if (obj == cancleBtn) {
			setVisible(false);
		}
		
	}
	public void changePw(String id) {
		pw = new String(pwTf.getPassword());
		repw = new String(rePwTf.getPassword());
		boolean pwPt = Pattern.matches(new String("^[A-Za-z[0-9]$@$!%*#?&`~]{5,15}$"),pw);
		boolean repwPt = Pattern.matches(new String("^[A-Za-z[0-9]$@$!%*#?&`~]{5,15}$"),repw);
		if(!pw.equals(repw)) {
			JOptionPane.showMessageDialog(this, "입력하신 PWD가 서로 일치하지 않습니다. ");
		}else if(pwPt == false || repwPt == false) {
			JOptionPane.showMessageDialog(this, "영문대소문자, 숫자, 특수문자 ` ~ ! @ # $ % ^ & * ? 를 사용하여\n"
					+ "5~15자 입력해주세요 " );
		}else {
			MemberInfoDAO dao = new MemberInfoDAO();
			int result =  dao.changePw(id, repw);
			if(result > 0){
				JOptionPane.showMessageDialog(this, "PWD변경이 완료되었습니다.");
				Test test = new Test();
				test.change("login");
				setVisible(false);
			}else {
				JOptionPane.showMessageDialog(this, "PWD변경에 실패하였습니다.");
				
			}
		}
	}
}
