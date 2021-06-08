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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import vo.CallDAO;
import vo.MemberInfoDAO;
import vo.MemberInfoVO;
import vo.ReservationDAO;


class MyDialog extends JDialog implements ActionListener{
	JPanel pane = new JPanel();
	JLabel text = new JLabel("회원탈퇴 하시려면 'ID' 를 입력해주세요 ", JLabel.CENTER);
	JTextField tf = new JTextField(10);
	JButton okBtn = new JButton("Ok");
	JButton cancelBtn = new JButton("cancle");
	MyDialog(){
		add(pane); pane.setBackground(Color.white); pane.setLayout(null);
		
		text.setBounds(10,20,300,40); 	pane.add(text);
		tf.setBounds(16,60,300,40);		pane.add(tf);
		okBtn.setBounds(55,120,80,40);				pane.add(okBtn);
		cancelBtn.setBounds(200,120,80,40);			pane.add(cancelBtn);
		
		setSize(350,220);
	okBtn.addActionListener(this);
	cancelBtn.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae) {
		Object Obj = ae.getSource();
		if(Obj == okBtn) {
			String inputid = tf.getText();
			
			MemberInfoDAO dao = new MemberInfoDAO();
			int result = dao.memberDelete(inputid);
			
			if(result>0) {
				JOptionPane.showMessageDialog(this, "회원탈퇴하셨습니다.");
				tf.setText("");
			}else {
				JOptionPane.showMessageDialog(this, "회원탈퇴를 실패하였습니다");
			}
		}else if(Obj == cancelBtn) {
			setVisible(false);
		}

	}
}
public  class ModifyPersonalInfo extends JPanel implements ActionListener {
	Test test;
	MyDialog dialog;
	
	JPanel askPane = new JPanel();
		JButton backBtn = new JButton("⇦ 뒤로");
		JButton askBtn = new JButton("관리자 호출");
	JPanel joinPane = new JPanel();
		JLabel joinLbl = new JLabel("개인정보수정", JLabel.CENTER);
		
	JPanel modifyPane = new JPanel();
		JLabel name = new JLabel("성명", JLabel.CENTER);
		JLabel birth = new JLabel("생년월일", JLabel.CENTER);
		JLabel tel = new JLabel("연락처", JLabel.CENTER);
		JLabel ID = new JLabel("아이디", JLabel.CENTER);
		JLabel nowPWD = new JLabel("기존비밀번호", JLabel.CENTER);
		JLabel modifyPWD = new JLabel("수정할 비밀번호", JLabel.CENTER);
		JLabel modifyPWDok = new JLabel("수정할 비밀번호 확인", JLabel.CENTER);
		
		JLabel nameR = new JLabel("", JLabel.CENTER);
		JLabel birthR = new JLabel("", JLabel.CENTER);
		JTextField telR = new JTextField(30);
		JLabel IDR = new JLabel("", JLabel.CENTER);
		JPasswordField nowPWDR = new JPasswordField(30);
		JLabel idConditionLbl = new JLabel("* 로그인시 사용한 비밀번호를 입력하세요", JLabel.CENTER);
		JPasswordField modifyPWDR = new JPasswordField(30);
		JPasswordField modifyPWDokR = new JPasswordField(30);
		JLabel pwdConditionLbl1 = new JLabel("영문, 숫자, 특수문자 (5~15자)", JLabel.CENTER);
		JLabel pwdConditionLbl2 = new JLabel("` ~ ! @ # $ % ^ & * ? ", JLabel.CENTER);
		
	JPanel selPane = new JPanel();	
		JButton modifyBtn = new JButton("수정");
		JButton cancleBtn = new JButton("취소");
		JButton memberOutBtn = new JButton("회원탈퇴");
		
	Font fnt_Title = new Font("굴림", Font.BOLD, 20);
	Font fnt_Modify = new Font("굴림", Font.BOLD,12);
	Font fnt_Modify_Mini = new Font("굴림", Font.BOLD,10);
	Font fnt = new Font("굴림", Font.PLAIN, 11);	
	String id = "", pw="", usertel="";
	String nowPw="", modifyPw="", modifyPw2=""; 
	String btnCheck="";
	public ModifyPersonalInfo() {}
	public ModifyPersonalInfo(Test test, String userId, String btnCheck) {
		this.test = test;
		this.btnCheck = btnCheck;
		id = userId;
		nowPw = new String(nowPWDR.getPassword());
		modifyPw= new String(modifyPWDR.getPassword());
		modifyPw2= new String(modifyPWDokR.getPassword());
		ModifyPersonalInfoFrame();
		memberinfoSet(id);
		
		
		if(btnCheck.equals("1")) {askBtn.setEnabled(false);} else if(btnCheck.equals("2")) { askBtn.setEnabled(true); }
		
		dialog = new MyDialog();
		
		backBtn.addActionListener(this);
		askBtn.addActionListener(this);
		modifyBtn.addActionListener(this);
		cancleBtn.addActionListener(this);
		memberOutBtn.addActionListener(this);
		cancleBtn.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae) {
		Object eventObj = ae.getSource();
		if(eventObj == backBtn || eventObj == cancleBtn) {
			test.change("main", id);
		}else if(eventObj == askBtn) {
			adminCall();
		}else if(eventObj == modifyBtn) {
			//memberinfoSet(id);
			memberDataUpdate();
		}else if(eventObj == memberOutBtn) {
			memberDelCheck(id);
		}
	}
	
	// 관리자 호출
	public void adminCall() {
		CallDAO dao=new CallDAO();
		
		int result=dao.InsertCall(test.roomrenum);
		
		if(result>0) {
			JOptionPane.showMessageDialog(this, "관리자를 호출하였습니다","관리자 호출 완료",JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
		
	public void memberDelCheck(String id) {
		ReservationDAO dao = new ReservationDAO();
		int result = dao.checkMemberDel(id);
		if(result>0) {
			JOptionPane.showMessageDialog(this, "예약중인(결제 미완료) 내역이 있어 탈퇴가 불가합니다."  );
		}else {
			dialog.setVisible(true);
		}
	}
	public void memberinfoSet(String id) {
		MemberInfoDAO dao = new MemberInfoDAO();
		List<MemberInfoVO> lst = dao.setModifyPersonalInfo(id);
		
		MemberInfoVO vo = lst.get(0);
		pw = vo.getPw();
		usertel = vo.getTel();
		nameR.setText(vo.getName());
		//telR.setText(vo.getTel());
		birthR.setText(vo.getBirth().substring(0,10));
		IDR.setText(vo.getId());
	}
	public void memberDataUpdate() {
		MemberInfoVO getVo = new MemberInfoVO();
		nowPw = new String(nowPWDR.getPassword());
		modifyPw= new String(modifyPWDR.getPassword());
		modifyPw2= new String(modifyPWDokR.getPassword());
		getVo.setPw(modifyPw);
		getVo.setTel(telR.getText());
		getVo.setId(IDR.getText());
		MemberInfoDAO dao = new MemberInfoDAO();
		boolean pwPt = Pattern.matches(new String("^[A-Za-z[0-9]$@$!%*#?&`~]{5,15}$"), modifyPw);
		boolean repwPt = Pattern.matches(new String("^[A-Za-z[0-9]$@$!%*#?&`~]{5,15}$"), modifyPw2);
		boolean telPt = Pattern.matches(new String("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$"), telR.getText());
		if(nowPw.equals("")) {
			JOptionPane.showMessageDialog(this, "연락처 변경을 원하시면\n  변경할 연락처, 현재비밀번호 입력\n\n 비밀번호 변경을 원하시면 \n현재 비밀번호, 수정할비밀번호 입력 \n수정버튼을 눌러주세요 ");
		}else if(nowPw.equals(pw)){ //입력한 현재 비밀번호가 같다면 정보변경이 가능하다.  
			if(telR.getText().equals(usertel) && (modifyPw.equals("")) && (modifyPw2.equals(""))){
				//비밀번호를 맞았고, tel은 (기본입력이되어있지만)기본입려값 + 변경할 비밀번호를 입력하지 않았다면
				JOptionPane.showMessageDialog(this, "입력값이 기존 정보와 같습니다.\n\n연락처 변경을 원하시면\n  변경할 연락처, 현재비밀번호 입력\n\n비밀번호 변경을 원하시면\n  현재 비밀번호, 수정할비밀번호 입력 \n\n 후에 수정버튼을 눌러주세요 ");
			}else if((!telR.getText().equals(usertel)) && (!modifyPw.equals("") && !modifyPw2.equals(""))) {
				if(telPt==false) {
					JOptionPane.showMessageDialog(this, "010-****-**** 형식으로 입력해 주세요"  );
				}else if(!modifyPw.equals(modifyPw2)){//만약 비빌번호, 비밀번호 확인이 같지않다면, 
					JOptionPane.showMessageDialog(this, "수정할 비밀번호가 서로 일치하지 않습니다. 확인 후 재입력 부탁드립니다.");
				}else if(pwPt == false || repwPt == false) {
					JOptionPane.showMessageDialog(this, "영문대소문자, 숫자, 특수문자 ` ~ ! @ # $ % ^ & * ? 를 사용하여\n"
							+ "5~15자 입력해주세요 " );
				}else {
					int result2 = dao.telChange(id,telR.getText());
					int result1 = dao.changePw(id,modifyPw2);
					if(result1 > 0 && result2 >0) {
						JOptionPane.showMessageDialog(this, "연락처, 비밀번호 변경이 완료되었습니다.");
						nowPWDR.setText(""); modifyPWDR.setText(""); modifyPWDokR.setText("");
					}
				}
			}else if(!telR.getText().equals(usertel) && (modifyPw.equals("") && modifyPw2.equals(""))) {
				//비밀번호를 맞았고, tel은 변경 + 변경할 비밀번호를 입력하지 않았다면 => 번호만 변경. !
				if(telPt==false) {
					JOptionPane.showMessageDialog(this, "010-****-**** 형식으로 입력해 주세요"  );
				}else {
					int result = dao.telChange(id,telR.getText());
					if(result > 0) {
						JOptionPane.showMessageDialog(this, "연락처 변경이 완료되었습니다.");
						nowPWDR.setText("");
					}else{
						JOptionPane.showMessageDialog(this, "연락처 변경에 실패하였습니다.\n 잘못입력한 정보가있는지 확인해주세요");
					}
				}
			}else if(telR.getText().equals(usertel) && (!modifyPw.equals("")) || (!modifyPw2.equals(""))) { 
				// 현재비밀번호 맞았고, 연락처가 기존거랑같게 입력되어있고 변경비밀번호가 비어있지 않다면   //비밀번호 변경
				if(!modifyPw.equals(modifyPw2)){//만약 비빌번호, 비밀번호 확인이 같지않다면, 
					JOptionPane.showMessageDialog(this, "수정할 비밀번호가 서로 일치하지 않습니다. 확인 후 재입력 부탁드립니다.");
				}else if(pwPt == false || repwPt == false) {
					JOptionPane.showMessageDialog(this, "영문대소문자, 숫자, 특수문자 ` ~ ! @ # $ % ^ & * ? 를 사용하여\n"
							+ "5~15자 입력해주세요 " );
				}else {
					int result = dao.changePw(id,modifyPw2);
					if(result > 0) {
						JOptionPane.showMessageDialog(this, "비밀번호 변경이 완료되었습니다.");
						nowPWDR.setText(""); modifyPWDR.setText(""); modifyPWDokR.setText("");
					}else{
						JOptionPane.showMessageDialog(this, "비밀번호 변경에 실패하였습니다.\n 잘못입력한 정보가있는지 확인해주세요");
					}
				}
				
			}
		}else {
			JOptionPane.showMessageDialog(this, "현재 비밀번호가 틀렸습니다.");
		}
	}
	public void ModifyPersonalInfoFrame() {
		//상단 관리자 문의 
		askPane.setLayout(null);	
		askPane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
			backBtn.setBackground(Color.LIGHT_GRAY);
			backBtn.setBounds(10, 10, 100, 40); askPane.add(backBtn);
			askBtn.setBackground(Color.LIGHT_GRAY);
			askBtn.setBounds(330, 10, 100, 40);	askPane.add(askBtn);
			askPane.setBackground(Color.white);
			
		askPane.setBounds(0, 0, 460, 60);
		//회원가입 라벨
		joinPane.setLayout(null);
		joinLbl.setBounds(120, 40, 200, 70);
			joinLbl.setFont(fnt_Title);
			joinLbl.setHorizontalAlignment(JLabel.CENTER);
			joinLbl.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
			joinPane.add(joinLbl);
			joinPane.setBackground(Color.white);
		
		joinPane.setBounds(0, 60, 460, 150);
		
		
		//회정정보수정란
		modifyPane.setLayout(null); modifyPane.setBackground(Color.white);
		modifyPane.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
		
			name.setBounds(60,20,120,30); 			name.setFont(fnt_Modify); 				modifyPane.add(name); 
			birth.setBounds(60,70,120,30); 			birth.setFont(fnt_Modify); 				modifyPane.add(birth);
			tel.setBounds(60,120,120,30); 			tel.setFont(fnt_Modify); 				modifyPane.add(tel);
			ID.setBounds(60,170,120,30); 			ID.setFont(fnt_Modify); 				modifyPane.add(ID);
			nowPWD.setBounds(60,220,120,30); 		nowPWD.setFont(fnt_Modify);				modifyPane.add(nowPWD);
			modifyPWD.setBounds(60,300,120,30);	 	modifyPWD.setFont(fnt_Modify);			modifyPane.add(modifyPWD);
			modifyPWDok.setBounds(60,350,150,30); 	modifyPWDok.setFont(fnt_Modify); 		modifyPane.add(modifyPWDok);
			
			nameR.setBounds(255,20,120,30); 																		 modifyPane.add(nameR);
			birthR.setBounds(255,70,120,30); 						 												 modifyPane.add(birthR);
			telR.setBounds(255,120,120,30);  				telR.setHorizontalAlignment(JTextField.CENTER);			 modifyPane.add(telR);
			IDR.setBounds(255,170,120,30); 							 												 modifyPane.add(IDR);
			nowPWDR.setBounds(255,220,120,30); 				nowPWDR.setHorizontalAlignment(JTextField.CENTER);		 modifyPane.add(nowPWDR);
			idConditionLbl.setBounds(220,250,195,30);		idConditionLbl.setFont(fnt_Modify_Mini);				 modifyPane.add(idConditionLbl);
			modifyPWDR.setBounds(255,300,120,30); 			modifyPWDR.setHorizontalAlignment(JTextField.CENTER);	 modifyPane.add(modifyPWDR);
			modifyPWDokR.setBounds(255,350,120,30); 		modifyPWDokR.setHorizontalAlignment(JTextField.CENTER);	 modifyPane.add(modifyPWDokR);
			pwdConditionLbl1.setBounds(220,381,205,30); 	pwdConditionLbl1.setFont(fnt_Modify_Mini);			   	 modifyPane.add(pwdConditionLbl1);
			pwdConditionLbl2.setBounds(220,400,205,30); 	pwdConditionLbl2.setFont(fnt_Modify_Mini);				 modifyPane.add(pwdConditionLbl2);
		
		modifyPane.setBounds(0,210,460,450);
		
		//선택패널
		selPane.setLayout(null);
		selPane.setBackground(Color.white);
			modifyBtn.setBounds(40,30,100,40);		selPane.add(modifyBtn);
			cancleBtn.setBounds(180,30,100,40);		selPane.add(cancleBtn);
			memberOutBtn.setBounds(320,30,100,40);	selPane.add(memberOutBtn);
			
		selPane.setBounds(0,660,460,800); 
			
		
		
		setLayout(null);
		add(askPane);
		add(joinPane);
		add(modifyPane);
		add(selPane);
		setSize(460,800);
		setVisible(true);
	}
}
