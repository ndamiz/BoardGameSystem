package userSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import vo.CallDAO;
import vo.MemberInfoDAO;
import vo.MemberInfoVO;

public class JoinPage extends JPanel implements ActionListener{
	Test test;
	JPanel askPane = new JPanel();
		JButton backBtn = new JButton("⇦ 뒤로");
		JButton askBtn = new JButton("관리자 호출");
	JPanel joinPane = new JPanel();
		JLabel joinLbl = new JLabel("회원 가입");
	
	JPanel inputPane = new JPanel();
		JLabel idLbl = new JLabel("ID");
		JLabel pwdLbl = new JLabel("PWD");
		JLabel repwdLbl = new JLabel("PWD확인");
		JLabel nameLbl = new JLabel("성함");
		JLabel telLbl = new JLabel("연락처");
		JLabel birthLbl = new JLabel("생년월일");
		
		JTextField idTf = new JTextField();
		JPasswordField pwdTf = new JPasswordField();
		JPasswordField repwdTf = new JPasswordField();
		JTextField nameTf = new JTextField();
		JTextField telTf = new JTextField("010-");
		JTextField birthTf = new JTextField();
		
		JButton duplicateBtn = new JButton("중복확인");
		JLabel idConditionLbl = new JLabel("영문, 숫자 만 가능합니다. (5~15자)");
		JLabel pwdConditionLbl1 = new JLabel("영문, 숫자, 특수문자 (5~15자)");
		JLabel pwdConditionLbl2 = new JLabel("` ~ ! @ # $ % ^ & * ? ");
		
	JPanel clickPane = new JPanel();
	JButton joinBtn = new JButton("회원가입");
	JButton cancelBtn = new JButton("가입취소"); 
	Font fnt_Title = new Font("돋움", Font.BOLD, 20);
	Font fnt = new Font("돋움", Font.PLAIN, 11);
	String idCheck;
	String pw ="" , repw="";
	public JoinPage() {}
	public JoinPage(Test test) {
		this.test=test;
		JoinFrame();
		//이벤트등록
		askBtn.setEnabled(false);
		
		backBtn.addActionListener(this);
		askBtn.addActionListener(this);
		duplicateBtn.addActionListener(this);
		joinBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		if(obj == duplicateBtn) { // 아이디중복확인
			checkId();
		}else if(obj == joinBtn) { //회원가입 버튼 
			setUserJoin();
		}else if(obj == cancelBtn || obj==backBtn) { //취소버튼 = 뒤로 같은 기능 
			test.change("login");
		}
		/*
		else if(obj == askBtn) { //관리자문의 버튼 
			
		}
		*/
	}
	
		
	public void checkId() {
		idCheck = idTf.getText();
		boolean idPt = Pattern.matches(new String("^[a-zA-Z0-9]*$"), idCheck);
		if(idCheck.equals("")) {//만약 공백입력했을 경우
			JOptionPane.showMessageDialog(this, "ID를 입력 후 중복확인을 눌러주세요");
		}else if(idPt==false) {
			JOptionPane.showMessageDialog(this, "ID는 영문,숫자만 입력이 가능합니다.");
		}else if(idCheck.length() < 5 || idCheck.length() > 15) {
			JOptionPane.showMessageDialog(this, "ID를 5~15자 내외로 입력해주세요");
		}else {
			MemberInfoDAO dao = new MemberInfoDAO();
			int result = dao.userCheckId(idCheck);
			if(result==0) {//검색된 레코드가 없을때. 
				JOptionPane.showMessageDialog(this, "입력하신 ID ["  +idCheck+ "]는 \n 사용가능한 ID 입니다."  );
			}else {
				JOptionPane.showMessageDialog(this, "입력하신 ID ["  +idCheck+ "]와 \n 중복되는 ID가 있습니다.\n"
						+ "다른 ID를 입력해주세요.");
				idCheck="";
			}
		}
	}
	public void setUserJoin() { //회원가입 
		//입력 데이터를 VO에 세팅. 
		pw = new String(pwdTf.getPassword());
		repw = new String(repwdTf.getPassword());
		
		MemberInfoVO vo = new MemberInfoVO(idTf.getText(), repw, nameTf.getText(), telTf.getText(), birthTf.getText());
		boolean pwPt = Pattern.matches(new String("^[A-Za-z[0-9]$@$!%*#?&`~]{5,15}$"), repw);
		boolean repwPt = Pattern.matches(new String("^[A-Za-z[0-9]$@$!%*#?&`~]{5,15}$"), vo.getPw());
		boolean telPt = Pattern.matches(new String("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$"), vo.getTel());
		boolean namePt = Pattern.matches(new String("^[가-힣]{2,30}$") , vo.getName());
		boolean birthPt = Pattern.matches(new String("^(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$"), vo.getBirth()); 

		if(idCheck.equals(idTf.getText())) { 
			if(!pw.equals(repw)) {
				JOptionPane.showMessageDialog(this, "입력하신 PWD가 서로 일치하지 않습니다. ");
			}else if(pwPt == false || repwPt == false) {
				JOptionPane.showMessageDialog(this, "영문대소문자, 숫자, 특수문자 ` ~ ! @ # $ % ^ & * ? 를 사용하여\n"
						+ "9~15자 입력해주세요 " );
			}else if(namePt == false) {
				JOptionPane.showMessageDialog(this, "성함은 2자리 이상인 한글만 입력이 가능합니다.");
			}else if(telPt == false) {
				JOptionPane.showMessageDialog(this, "010-****-**** 형식으로 입력해 주세요"  );
			}else if(birthPt==false) {
				JOptionPane.showMessageDialog(this, "YYYYMMDD 형식으로 숫자로만 입력해 주세요"  );
			}else {
				MemberInfoDAO dao = new MemberInfoDAO();
				int result = dao.userJoin(vo);
				if(result > 0 ) {
					JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다.");
					//로그인화면으로 돌아가게 넣기 
					test.change("login");
				}else {
					JOptionPane.showMessageDialog(this, "회원가입이 실패하였습니다.\n 잘못입력한 정보가있는지 확인해주세요");
				}
			}
		}else{
			JOptionPane.showMessageDialog(this, "ID중복확인을 해주시길 바랍니다."  );
		}
		
	}
	
	public void JoinFrame() {
		//상단 관리자 문의 
		askPane.setLayout(null);	askPane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
		backBtn.setBackground(Color.LIGHT_GRAY);	backBtn.setBounds(10, 10, 100, 40); askPane.add(backBtn);
		askBtn.setBackground(Color.LIGHT_GRAY);	askBtn.setBounds(330, 10, 100, 40);	askPane.add(askBtn);
		askPane.setBackground(Color.white);	askPane.setBounds(0, 0, 460, 60);
		//회원가입 라벨
		joinPane.setLayout(null);	joinLbl.setBounds(120, 40, 200, 70);	joinLbl.setFont(fnt_Title);
		joinLbl.setHorizontalAlignment(JLabel.CENTER);		joinLbl.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
		joinPane.add(joinLbl);	joinPane.setBackground(Color.white);
		joinPane.setBounds(0, 60, 460, 150);
		//입력받는 부분
		inputPane.setLayout(null);
		//ID입력 
		idLbl.setBounds(70, 40, 70, 40);		inputPane.add(idLbl);
		idTf.setBounds(140, 40, 170, 40);		inputPane.add(idTf);
		duplicateBtn.setBackground(Color.LIGHT_GRAY);
		duplicateBtn.setBounds(320, 40, 100, 40);	inputPane.add(duplicateBtn);
		idConditionLbl.setFont(fnt);	
		idConditionLbl.setBounds(140, 80, 460, 15); 	inputPane.add(idConditionLbl);
		//PWD입력
		pwdLbl.setBounds(70, 130, 70, 40); 	inputPane.add(pwdLbl);
		
		pwdTf.setBounds(140, 130, 170, 40);	inputPane.add(pwdTf);
		repwdLbl.setBounds(70, 180, 70, 40);	inputPane.add(repwdLbl);
		repwdTf.setBounds(140, 180, 170, 40);	inputPane.add(repwdTf);
		pwdConditionLbl1.setFont(fnt);
		pwdConditionLbl1.setBounds(140, 220, 280, 11); inputPane.add(pwdConditionLbl1);
		pwdConditionLbl2.setFont(fnt);
		pwdConditionLbl2.setBounds(140, 231, 280, 11); inputPane.add(pwdConditionLbl2);
		
		//성함 입력
		nameLbl.setBounds(70, 260, 70, 40); inputPane.add(nameLbl);
		nameTf.setBounds(140, 260, 170, 40); inputPane.add(nameTf);
		//연락처입력
		telLbl.setBounds(70, 310, 70, 40); inputPane.add(telLbl);
		telTf.setBounds(140, 310, 170, 40); inputPane.add(telTf);
		//생년월일 입력
		birthLbl.setBounds(70, 360, 70, 40); inputPane.add(birthLbl);
		birthTf.setBounds(140, 360, 170, 40); inputPane.add(birthTf);
		inputPane.setBackground(Color.WHITE);
		inputPane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
		inputPane.setBounds(0, 210, 460, 440);
		
		//회원가입 버튼 
		clickPane.setLayout(null);
		joinBtn.setBackground(Color.LIGHT_GRAY);
		cancelBtn.setBackground(Color.LIGHT_GRAY);
		joinBtn.setBounds(100, 20, 100, 40); clickPane.add(joinBtn);
		cancelBtn.setBounds(250, 20, 100, 40); clickPane.add(cancelBtn);
		
		clickPane.setBackground(Color.WHITE);
		clickPane.setBounds(0, 650, 460, 190);
				
		setLayout(null);
		add(askPane);	add(joinPane);	add(inputPane);	add(clickPane);
		setVisible(true);	setSize(460, 800);
	}
}
