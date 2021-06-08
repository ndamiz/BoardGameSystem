package adminSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements ActionListener{
	public GameInvenMng gamePage = null; 
	public FoodInvenMng foodPage = null;
	public MemberMng memberPage = null;
	public AdminMain adminMain = null;
	public StatsPageBorrowGame borrowGame = null;
	public StatsPageFoodCate foodCate = null;
	public StatsPageFoodItem foodItemPage = null;
	public StatsPageSellGame sellGamePage = null;
	public StatsPageTotal totalpage = null;
	
	Font fnt = new Font("돋움",Font.BOLD,13);
	JPanel mainPane = new JPanel();
	JMenuBar mb = new JMenuBar();
	JMenu homeMenu = new JMenu("홈");
		JMenuItem homeItem = new JMenuItem("홈");
		JMenuItem exit = new JMenuItem("종료");
	JMenu statsMenu = new JMenu("통계");
		JMenuItem BorrowGameItem = new JMenuItem("게임순위통계");
		JMenuItem foodCateItem = new JMenuItem("음식(카테고리별)통계");
		JMenuItem foodItem = new JMenuItem("음식(아이템별)통계");
		JMenuItem sellGameItem = new JMenuItem("게임 판매 통계");
		JMenuItem totalItem = new JMenuItem("종합 통계");
	JMenu mngMenu = new JMenu("관리");
		JMenu stockMenu = new JMenu("재고관리");
			JMenuItem invenMngItem = new JMenuItem("게임재고관리");
			JMenuItem foodMngItem = new JMenuItem("음식재고관리");
		JMenuItem memberMenu = new JMenuItem("회원관리");
	JPanel foodbigPane = new JPanel(new BorderLayout());
	JPanel northPane = new JPanel(new BorderLayout());
		JButton gameStockBtn = new JButton("게임 재고 관리");
		JButton foodStockBtn = new JButton("음식 재고 관리");
	JPanel norNorthPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
	String adminId;
	
	public MainFrame() {
		super("보드게임 CAFE [관리자]");
		//// 메뉴바 //////  
		homeMenu.setFont(fnt);				homeItem.setFont(fnt);	
		exit.setFont(fnt);					statsMenu.setFont(fnt);	
		mngMenu.setFont(fnt);	 			stockMenu.setFont(fnt);	
		invenMngItem.setFont(fnt);	 		foodMngItem.setFont(fnt);	
		memberMenu.setFont(fnt);	
		
		mb.add(homeMenu);
			homeMenu.add(homeItem);
			homeMenu.add(exit);
		mb.add(statsMenu);
			statsMenu.add(BorrowGameItem);
			statsMenu.add(foodCateItem);
			statsMenu.add(foodItem);
			statsMenu.add(sellGameItem);
			statsMenu.add(totalItem);
		mb.add(mngMenu);
			mngMenu.add(stockMenu);
				stockMenu.add(invenMngItem);
				stockMenu.add(foodMngItem);
			mngMenu.add(memberMenu); 
		setJMenuBar(mb);
		
		mainPane.setLayout(null); 
		mainPane.setSize(700,600);
		
		adminMain = new AdminMain(this);
		mainPane.add(adminMain);
		add(mainPane);

		setLayout(null);
		setSize(700,600);
		setVisible(true);
		//setResizable(false); 
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//이벤트 등록
		invenMngItem.addActionListener(this);
		foodMngItem.addActionListener(this);
		memberMenu.addActionListener(this);
		homeItem.addActionListener(this);
		BorrowGameItem.addActionListener(this);
		foodCateItem.addActionListener(this);
		foodItem.addActionListener(this);
		sellGameItem.addActionListener(this);
		totalItem.addActionListener(this);
 	}


	public void change(String paneName, String adminId) {
		if(paneName.equals("gamePage")) {
			mainPane.removeAll();
			gamePage = new GameInvenMng(this, adminId);
			
			mainPane.add(gamePage);
			setVisible(true);
			revalidate();
			repaint();
		}else if(paneName.equals("foodPage")) {
			mainPane.removeAll();
			foodPage=new FoodInvenMng(this, adminId);
			
			mainPane.add(foodPage);
	 		setVisible(true);
			revalidate();
			repaint();
		}		
	}
	public void change(String paneName) {
		if(paneName.equals("adminMain")) {
			mainPane.removeAll();
			adminMain = new AdminMain(this);
			
			mainPane.add(adminMain);
			setVisible(true);
			revalidate();
			repaint();
		}else if(paneName.equals("memberPage")) {
			mainPane.removeAll();
			memberPage = new MemberMng (this);
			
			mainPane.add(memberPage);
			setVisible(true);
			revalidate();
			repaint();
		}else if(paneName.equals("borrowGame")) {
			mainPane.removeAll();
			borrowGame = new StatsPageBorrowGame(this);
			
			mainPane.add(borrowGame);
			setVisible(true);
			revalidate();
			repaint();
		}else if(paneName.equals("foodCate")) {
			mainPane.removeAll();
			foodCate = new StatsPageFoodCate(this);
			
			mainPane.add(foodCate);
			setVisible(true);
			revalidate();
			repaint();
		}else if(paneName.equals("foodItemPage")) {
			mainPane.removeAll();
			foodItemPage = new StatsPageFoodItem(this);
			
			mainPane.add(foodItemPage);
			setVisible(true);
			revalidate();
			repaint();
		} else if(paneName.equals("sellGamePage")){
			mainPane.removeAll();
			sellGamePage = new StatsPageSellGame(this);
			
			mainPane.add(sellGamePage);
			setVisible(true);
			revalidate();
			repaint();
		}else if(paneName.equals("totalpage")){
			mainPane.removeAll();
			totalpage = new StatsPageTotal(this);
			
			mainPane.add(totalpage);
			setVisible(true);
			revalidate();
			repaint();
		}
	}
 	public void actionPerformed(ActionEvent ae) {
 		Object eventObj = ae.getSource();
 		if(eventObj instanceof JMenuItem) {
 			String eventMenu = ae.getActionCommand();
 			if(eventMenu.equals("게임재고관리")){
 				mainPane.removeAll();
 				gamePage = new GameInvenMng(this, adminId);
 				
 				mainPane.add(gamePage);
 				setVisible(true);
 				revalidate();
 				repaint();
			}else if(eventMenu.equals("음식재고관리")){
				mainPane.removeAll();
				foodPage=new FoodInvenMng(this, adminId);
				
				mainPane.add(foodPage);
		 		setVisible(true);
				revalidate();
				repaint();
			}else if(eventMenu.equals("회원관리")){
				mainPane.removeAll();
				memberPage = new MemberMng (this);
				
				mainPane.add(memberPage);
				setVisible(true);
				revalidate();
				repaint();
			}else if(eventMenu.equals("홈")){
				change("adminMain");
			}else if(eventMenu.equals("게임순위통계")){
				mainPane.removeAll();
				borrowGame = new StatsPageBorrowGame(this);
				
				mainPane.add(borrowGame);
				setVisible(true);
				revalidate();
				repaint();
			}  else if(eventMenu.equals("음식(카테고리별)통계")){
				mainPane.removeAll();
				foodCate = new StatsPageFoodCate(this);
				
				mainPane.add(foodCate);
				setVisible(true);
				revalidate();
				repaint();
			} else if(eventMenu.equals("음식(아이템별)통계")){
				mainPane.removeAll();
				foodItemPage = new StatsPageFoodItem(this);
				
				mainPane.add(foodItemPage);
				setVisible(true);
				revalidate();
				repaint();
			} else if(eventMenu.equals("게임 판매 통계")){
				mainPane.removeAll();
				sellGamePage = new StatsPageSellGame(this);
				
				mainPane.add(sellGamePage);
				setVisible(true);
				revalidate();
				repaint();
			}else if(eventMenu.equals("종합 통계")){
				mainPane.removeAll();
				totalpage = new StatsPageTotal(this);
				
				mainPane.add(totalpage);
				setVisible(true);
				revalidate();
				repaint();
			}
 		}
	}
}
