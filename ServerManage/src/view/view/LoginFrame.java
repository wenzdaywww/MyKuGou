package view.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ctrl.viewctrl.LoginFrameCtrl;
import util.PictureUtil;
import view.ui.BackPanel;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame {

	private BackPanel mainPane;
	private JTextField textId;
	private JPasswordField pwdField;
	private static LoginFrame lFrame;
	private JLabel lblLogin;
	private JLabel lblExit;
	private JLabel lblMin;
	private JLabel lblTip;
	
	public static LoginFrame getLF(){
		if (lFrame==null) {
			lFrame=new LoginFrame();
		}
		return lFrame;
	}

	/**
	 * Create the frame.
	 */
	private  LoginFrame() {
		setUndecorated(true);
		setSize(680, 420);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setTitle("音乐后台管理");
		setIconImage(PictureUtil.ICO);
		
		mainPane = new BackPanel();
		mainPane.setOpaque(true);
		mainPane.setImage(PictureUtil.LOGIN_BACKGROUP);
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPane);
		mainPane.setLayout(null);
		
		lblExit = new JLabel();
		lblExit.setIcon(PictureUtil.LOGIN_EXIT_ICO);
		lblExit.setBounds(450, 265, 65, 20);
		mainPane.add(lblExit);
		
		lblLogin = new JLabel();
		lblLogin.setIcon(PictureUtil.ENTER_ICO);
		lblLogin.setBounds(338, 265, 65, 20);
		mainPane.add(lblLogin);
		
		textId = new JTextField();
		textId.setText("10000");
		textId.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textId.setBounds(336, 206, 180, 18);
		mainPane.add(textId);
		textId.setColumns(10);
		
		pwdField = new JPasswordField();
		pwdField.setText("www12345");
		pwdField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		pwdField.setBounds(336, 240, 180, 18);
		mainPane.add(pwdField);
		
		lblMin = new JLabel("-");
		lblMin.setFont(new Font("宋体", Font.BOLD, 15));
		lblMin.setForeground(Color.WHITE);
		lblMin.setHorizontalTextPosition(SwingConstants.CENTER);
		lblMin.setHorizontalAlignment(SwingConstants.CENTER);
		lblMin.setBounds(655, 5, 20, 20);
		mainPane.add(lblMin);
		
		lblTip = new JLabel("正在连接服务器。。。");
		lblTip.setHorizontalAlignment(SwingConstants.CENTER);
		lblTip.setForeground(Color.WHITE);
		lblTip.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblTip.setBounds(290, 305, 269, 20);
		mainPane.add(lblTip);
		new LoginFrameCtrl(this);
	}

	public BackPanel getMainPane() {
		return mainPane;
	}

	public JTextField getTextId() {
		return textId;
	}

	public JPasswordField getPwdField() {
		return pwdField;
	}

	public JLabel getLblLogin() {
		return lblLogin;
	}

	public JLabel getLblExit() {
		return lblExit;
	}

	public JLabel getLblMin() {
		return lblMin;
	}

	public JLabel getLblTip() {
		return lblTip;
	}
	
}
