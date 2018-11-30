package view.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ctrl.viewctrl.PlayModeDlgCtrl;
import util.PictureUtil;

@SuppressWarnings("serial")
public class PlayModeDlg extends JDialog{
	
	private JLabel lblPlayOne;
	private JLabel lblLoopOne;
	private JLabel lblPlayOrder;
	private JLabel lblPlayList;
	private JLabel lblPlayRandom;
	private static PlayModeDlg playModeDlg;
	
	public static PlayModeDlg getPM(){
		if (playModeDlg==null) {
			playModeDlg=new PlayModeDlg();
		}
		return playModeDlg;
	}
	private PlayModeDlg() {
		setUndecorated(true);
		setSize(125, 185);
		setAlwaysOnTop(true);
		setBackground(new Color(0, 0, 0, 100));//RGB+Í¸Ã÷¶È
		getContentPane().setLayout(null);
		
		JPanel mainPanle=new JPanel();
		mainPanle.setLayout(null);
		mainPanle.setOpaque(false);
		setContentPane(mainPanle);
		
		lblPlayOne = new JLabel("µ¥Çú²¥·Å");
		lblPlayOne.setForeground(Color.WHITE);
		lblPlayOne.setIcon(PictureUtil.PLAY_ONE_ICO);
		lblPlayOne.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 15));
		lblPlayOne.setBounds(10, 10, 124, 25);
		mainPanle.add(lblPlayOne);
		
		lblLoopOne = new JLabel("µ¥ÇúÑ­»·");
		lblLoopOne.setForeground(Color.WHITE);
		lblLoopOne.setIcon(PictureUtil.LOOP_ONE_ICO);
		lblLoopOne.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 15));
		lblLoopOne.setBounds(10, 45, 124, 25);
		mainPanle.add(lblLoopOne);
		
		lblPlayOrder = new JLabel("Ë³Ðò²¥·Å");
		lblPlayOrder.setForeground(Color.WHITE);
		lblPlayOrder.setIcon(PictureUtil.PLAY_ORDER_ICO);
		lblPlayOrder.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 15));
		lblPlayOrder.setBounds(10, 80, 124, 25);
		mainPanle.add(lblPlayOrder);
		
		lblPlayList = new JLabel("ÁÐ±í²¥·Å");
		lblPlayList.setForeground(Color.WHITE);
		lblPlayList.setIcon(PictureUtil.PLAY_LIST_ICO);
		lblPlayList.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 15));
		lblPlayList.setBounds(10, 115, 124, 25);
		mainPanle.add(lblPlayList);
		
		lblPlayRandom = new JLabel("Ëæ»ú²¥·Å");
		lblPlayRandom.setForeground(Color.WHITE);
		lblPlayRandom.setIcon(PictureUtil.PLAY_RANDOM_ICO);
		lblPlayRandom.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 15));
		lblPlayRandom.setBounds(10, 150, 124, 25);
		mainPanle.add(lblPlayRandom);
		
		new PlayModeDlgCtrl(this);
	}

	public JLabel getLblPlayOne() {
		return lblPlayOne;
	}

	public JLabel getLblLoopOne() {
		return lblLoopOne;
	}

	public JLabel getLblPlayOrder() {
		return lblPlayOrder;
	}

	public JLabel getLblPlayList() {
		return lblPlayList;
	}

	public JLabel getLblPlayRandom() {
		return lblPlayRandom;
	}
	
}
