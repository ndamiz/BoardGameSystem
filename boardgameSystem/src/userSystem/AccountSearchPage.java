package userSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import vo.MemberInfoDAO;
import vo.MemberInfoVO;

public class AccountSearchPage extends JPanel implements ActionListener{
	Test test;
	JPanel askPane = new JPanel();
	JButton backBtn = new JButton("⇦ 뒤로");
	JButton askBtn = new JButton("관리자 호출");
	
	JPanel idSearchPane = new JPanel();
		JLabel idSearchLbl = new JLabel("아이디 찾기");
		JLabel idNameLbl = new JLabel("성함");
		JTextField idNameTf = new JTextField();
		JLabel idTelLbl = new JLabel("연락처");
		JTextField idTelTf = new JTextField();
		JLabel idBirthLbl = new JLabel("생년월일");
		JTextField idBirthTf = new JTextField();
		JButton idSearchBtn = new JButton("아이디 찾기");
		
	JPanel pwdSearchPane = new JPanel();	
		JLabel pwdSearchLbl = new JLabel("패스워드 재설정");
		JLabel pwdIdLbl = new JLabel("아이디");
		JTextField pwdIdTf = new JTextField();
		JLabel pwdNameLbl = new JLabel("성함");
		JTextField pwdNameTf = new JTextField();
		JLabel pwdTelLbl = new JLabel("연락처");
		JTextField pwdTelTf = new JTextField();
		JLabel pwdBirthLbl = new JLabel("생년월일");
		JTextField pwdBirthTf = new JTextField();
		JButton pwdSearchBtn = new JButton("패스워드 재설정");
		
		
	Font fnt_Title = new Font("굴림", Font.BOLD, 20);
	MemberInfoVO vo1;
	JDialog dialog = null;
	public AccountSearchPage() {}
	public AccountSearchPage(Test test) {
		this.test=test;
		 AccountSearchPageFrame();
		 
		 askBtn.setEnabled(false);
		 //이벤트등록
		 backBtn.addActionListener(this);
		 askBtn.addActionListener(this);
		 idNameTf.addActionListener(this);
		 idTelTf.addActionListener(this);
		 idBirthTf.addActionListener(this);
		 idSearchBtn.addActionListener(this);
		 pwdIdTf.addActionListener(this);
		 pwdNameTf.addActionListener(this);
		 pwdTelTf.addActionListener(this);
		 pwdBirthTf.addActionListener(this);
		 pwdSearchBtn.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		if(obj==idNameTf || obj == idTelTf || obj == idBirthTf || obj == idSearchBtn) {
			searchId();
		}else if(obj==pwdIdTf||obj==pwdNameTf || obj == pwdTelTf || obj == pwdBirthTf || obj == pwdSearchBtn) {
			searchPwd();
		}else if(obj==backBtn) {
			test.change("login");
		}
		/*
		else if(obj==askBtn) {
			//관리자 문의 
		}
		*/
	}
	public void searchId() {
		MemberInfoVO vo = new MemberInfoVO(idNameTf.getText(), idTelTf.getText(), idBirthTf.getText());
		boolean namePt = Pattern.matches(new String("^[가-힣]{2,30}$") , vo.getName());
		boolean telPt = Pattern.matches(new String("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$"), vo.getTel());
		boolean birthPt = Pattern.matches(new String("^(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$"), vo.getBirth()); 
		if(namePt == false) {
			JOptionPane.showMessageDialog(this, "성함을 확인 후 다시 입력해 주세요 ");
		}else if(telPt == false) {
			JOptionPane.showMessageDialog(this, "연락처 확인 후 \n 010-****-**** 형식으로 \n 다시 입력해 주세요"  );
		}else if(birthPt==false) {
			JOptionPane.showMessageDialog(this, "생년월일 확인 후 \n YYYYMMDD 형식으로 숫자로만 \n 다시 입력해 주세요"  );
		}else {
			MemberInfoDAO dao = new MemberInfoDAO();
			List<MemberInfoVO> searchList =  dao.searchId(vo);
			if(searchList.size() ==0) {
				JOptionPane.showMessageDialog(this, "입력하신 정보와 일치하는 회원이 없습니다.\n 확인 후 다시 입력해주세요");
			}else {
				vo1 = searchList.get(0);
				int i = JOptionPane.showConfirmDialog(this, vo1.getName() +" 님의 ID는 " 
						+ vo1.getId() + "입니다.\n비밀번호 재설정을 하시려면 OK를 눌러주세요.","ID 찾기결과",0);
				//yes : 0, no :1 
				if(i==0) {//여기부터시작해 
					PwChangeDialog pwc = new PwChangeDialog(vo1.getId());
					
				}
				tfClear();
			}
		}
	}
	public void searchPwd() {
		MemberInfoVO vo = new MemberInfoVO(pwdIdTf.getText(), pwdNameTf.getText(), pwdTelTf.getText(), pwdBirthTf.getText());
		boolean idPt = Pattern.matches(new String("^[a-zA-Z0-9]*$"), vo.getId());
		boolean namePt = Pattern.matches(new String("^[가-힣]{2,30}$") , vo.getName());
		boolean telPt = Pattern.matches(new String("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$"), vo.getTel());
		boolean birthPt = Pattern.matches(new String("^(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$"), vo.getBirth()); 
		if(idPt == false) {
			JOptionPane.showMessageDialog(this, "ID 확인 후 다시 입력해 주세요");
		}else if(namePt == false) {
			JOptionPane.showMessageDialog(this, "성함을 확인 후 다시 입력해 주세요 ");
		}else if(telPt == false) {
			JOptionPane.showMessageDialog(this, "연락처 확인 후 \n 010-****-**** 형식으로 \n 다시 입력해 주세요"  );
		}else if(birthPt==false) {
			JOptionPane.showMessageDialog(this, "생년월일 확인 후 \n YYYYMMDD 형식으로 숫자로만 \n 다시 입력해 주세요"  );
		}else {
			MemberInfoDAO dao = new MemberInfoDAO();
			List<MemberInfoVO> searchList =  dao.searchPw(vo);
			if(searchList.size() ==0) {
				JOptionPane.showMessageDialog(this, "입력하신 정보와 일치하는 회원이 없습니다.\n 확인 후 다시 입력해주세요");
			}else {
				vo1 = searchList.get(0);
				PwChangeDialog pwc = new PwChangeDialog(vo1.getId());
				tfClear();
			}
			
		}
	}
	public void tfClear() {
		idNameTf.setText("");	idTelTf.setText(""); 	idBirthTf.setText("");
		pwdIdTf.setText("");	pwdNameTf.setText("");	pwdTelTf.setText("");	pwdBirthTf.setText("");	
	}
	public void AccountSearchPageFrame() {
		//상단 관리자 문의 
		askPane.setLayout(null);	
		askPane.setBackground(Color.white);
		askPane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
			backBtn.setBackground(Color.LIGHT_GRAY);
			backBtn.setBounds(10, 10, 100, 40); askPane.add(backBtn);
			askBtn.setBackground(Color.LIGHT_GRAY);
			askBtn.setBounds(330, 10, 100, 40);	askPane.add(askBtn);
		askPane.setBounds(0, 0, 460, 60);
		
		//아이디 찾기
		idSearchPane.setLayout(null);
		idSearchPane.setBackground(Color.white);
			idSearchLbl.setBounds(120, 40, 200, 40);
			idSearchLbl.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
			idSearchLbl.setHorizontalAlignment(JLabel.CENTER);
			idSearchLbl.setFont(fnt_Title);
		idSearchPane.add(idSearchLbl);
			idNameLbl.setBounds(70, 100, 70, 40);
			idNameTf.setBounds(140, 100, 210, 40);
			idTelLbl.setBounds(70, 150, 70, 40);
			idTelTf.setBounds(140, 150, 210, 40);
			idBirthLbl.setBounds(70, 200, 70, 40);
			idBirthTf.setBounds(140, 200, 210, 40);
			idSearchBtn.setBackground(Color.LIGHT_GRAY);
			idSearchBtn.setBounds(300, 250, 130, 40);
		idSearchPane.add(idNameLbl);	idSearchPane.add(idNameTf);
		idSearchPane.add(idTelLbl);	idSearchPane.add(idTelTf);
		idSearchPane.add(idBirthLbl);	idSearchPane.add(idBirthTf);
		idSearchPane.add(idSearchBtn);
		idSearchPane.setBounds(0, 60, 460, 330);
		//패스워드 찾기
		pwdSearchPane.setLayout(null);
		pwdSearchPane.setBackground(Color.white);
		pwdSearchPane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
			pwdSearchLbl.setBounds(120, 40, 200, 40);
			pwdSearchLbl.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
			pwdSearchLbl.setHorizontalAlignment(JLabel.CENTER);
			pwdSearchLbl.setFont(fnt_Title);
		pwdSearchPane.add(pwdSearchLbl);
			pwdIdLbl.setBounds(70, 100, 70, 40);
			pwdIdTf.setBounds(140, 100, 210, 40);
			pwdNameLbl.setBounds(70, 150, 70, 40);
			pwdNameTf.setBounds(140, 150, 210, 40);
			pwdTelLbl.setBounds(70, 200, 70, 40);
			pwdTelTf.setBounds(140, 200, 210, 40);
			pwdBirthLbl.setBounds(70, 250, 70, 40);
			pwdBirthTf.setBounds(140, 250, 210, 40);
			pwdSearchBtn.setBackground(Color.LIGHT_GRAY);
			pwdSearchBtn.setBounds(300, 300, 130, 40);
		pwdSearchPane.add(pwdIdLbl);	pwdSearchPane.add(pwdIdTf);
		pwdSearchPane.add(pwdNameLbl);	pwdSearchPane.add(pwdNameTf);
		pwdSearchPane.add(pwdTelLbl);	pwdSearchPane.add(pwdTelTf);
		pwdSearchPane.add(pwdBirthLbl);	pwdSearchPane.add(pwdBirthTf);
		pwdSearchPane.add(pwdSearchBtn);
		pwdSearchPane.setBounds(0, 390, 460, 410);
		
		setLayout(null);
		add(askPane);
		add(idSearchPane);
		add(pwdSearchPane);
		setVisible(true);
		setSize(460, 800);
		
	}
}
