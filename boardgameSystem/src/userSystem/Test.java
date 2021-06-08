package userSystem;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JFrame{
	public GameListPage gameList=null; 
	public GameInfoPage gameInfo=null;
	public LoginPage login=null;
	public JoinPage join = null;
	public AccountSearchPage account = null;
	public UserMainPage main = null;
	public RoomReservePage reserve = null;
	public RoomCancelPage cancel = null;
	public UsingHistory history = null;
	public ModifyPersonalInfo personInfo = null;
	public OrderFoodMain orderFood = null;
	JPanel askPane = new JPanel();
	JButton backBtn = new JButton("◀ 뒤로");
	JButton askBtn = new JButton("관리자문의");
	
	JPanel mainPane = new JPanel();
	
	static String nowgame="";
	static String resergame="";
	static String backgame="";
	static String id= "";
	static int nowstock=0;
	static int backstock=0;
	static int roomrenum=0;
	
	public Test() {
		// 메인 패널
		mainPane.setLayout(null);
		mainPane.setSize(460, 800);
		add(mainPane);
		
		login = new LoginPage(this);
		mainPane.add(login);
		
	//	setForeground(Color.WHITE);
	//	setResizable(false); //창크기 고정
		setLayout(null);
		setVisible(true);
		setSize(460, 800);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	//	backBtn.addActionListener(this);

	}
	public static void main(String[] args) {
		new Test();
	}
	
	public void change(String paneName, String str) {
		if(paneName.equals("main")) {
			mainPane.removeAll();
			main=new UserMainPage(this, str);
			
			mainPane.add(main);
			setVisible(true);
			revalidate();
			repaint();
		}else if(paneName.equals("reserve")) {
			mainPane.removeAll();
			reserve=new RoomReservePage(this, str);
			
			mainPane.add(reserve);
	 		setVisible(true);
			revalidate();
			repaint();
		}else if(paneName.equals("cancel")) {
			mainPane.removeAll();
			cancel=new RoomCancelPage(this, str);
			
			mainPane.add(cancel);
			setVisible(true);
			revalidate();
			repaint();
		}else if(paneName.equals("history")) {
			mainPane.removeAll();
			history=new UsingHistory(this, str);
			
			mainPane.add(history);
			setVisible(true);
			revalidate();
			repaint();
		}else if(paneName.equals("orderFood")) {
			mainPane.removeAll();
			orderFood=new OrderFoodMain(this, str);
			
			mainPane.add(orderFood);
			setVisible(true);
			revalidate();
			repaint();
		}
	}
	public void change(String paneName) {
		if(paneName.equals("login")) {
			mainPane.removeAll();
			login = new LoginPage(this);
			
			mainPane.add(login);
			setVisible(true);
			revalidate();
			repaint();
		}else if(paneName.equals("join")){
			mainPane.removeAll();
			join = new JoinPage(this);
			
			mainPane.add(join);
			setVisible(true);
			revalidate();
			repaint();
		}else if(paneName.equals("account")) {
			mainPane.removeAll();
			account = new AccountSearchPage(this);
			
			mainPane.add(account);
			setVisible(true);
			revalidate();
			repaint();
		}
	}
	public void change(String paneName, String str, String str1) {
		if(paneName.equals("gameList")) {
			mainPane.removeAll();
			gameList=new GameListPage(this, str, str1);
			
			mainPane.add(gameList);
			setVisible(true);
			revalidate();
			repaint();
		}else if(paneName.equals("personInfo")) {
			mainPane.removeAll();
			personInfo=new ModifyPersonalInfo(this, str, str1);
			
			mainPane.add(personInfo);
			setVisible(true);
			revalidate();
			repaint();
		}
	}
	public void change(String paneName, String str, String str1, String str2) {
		if(paneName.equals("gameInfo")){
			mainPane.removeAll();
			gameInfo=new GameInfoPage(this, str, str1, str2);
			
			mainPane.add(gameInfo);
			setVisible(true);
			revalidate();
			repaint();
		}
	}
}
