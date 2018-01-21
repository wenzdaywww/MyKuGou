package view.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import ctrl.viewctrl.UserDialogCtrl;
import util.PictureUtil;
import view.ui.BackPanel;

@SuppressWarnings("serial")
public class UserDialog extends JDialog {
	
	private static UserDialog userDialog;
	private BackPanel mainPanel;
	private JLabel lblExit;
	private JButton btnOk;
	private JButton btnCancel;
	private UserDialogCtrl uCtrl;
	private JTextField textUserId;
	private JPasswordField pwdLField;
	private JPanel loginPanel;
	private JPanel typePanel;
	private JPanel registPanel;
	private JTextField textRName;
	private JPasswordField pwdRField;
	private JLabel lblTitle;
	private int typeUi=1;
	private int loginUi=1;
	private int registUi=2;
	private JLabel lblType;
	
	public static UserDialog getUD(){
		if (userDialog==null) {
			userDialog=new UserDialog();
		}
		return userDialog;
	}

	public UserDialog(){
		setUndecorated(true);
		setSize(300, 200);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		
		mainPanel = new BackPanel();
		mainPanel.setOpaque(false);
		setContentPane(mainPanel);
		mainPanel.setLayout(null);
		mainPanel.setImage(PictureUtil.SKIN);
		
		lblTitle = new JLabel("”√ªßµ«»Î");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setIcon(PictureUtil.USER_TITLE_ICO);
		lblTitle.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		lblTitle.setBounds(0, 0, 244, 35);
		mainPanel.add(lblTitle);
		
		lblExit = new JLabel("°¡");
		lblExit.setBounds(280, 0, 20, 20);
		lblExit.setForeground(new Color(255, 255, 255, 180));
		lblExit.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 25));
		mainPanel.add(lblExit);
		
		typePanel = new JPanel();
		typePanel.setBorder(new LineBorder(Color.GRAY));
		typePanel.setBackground(Color.WHITE);
		typePanel.setBounds(0, 35, 300, 165);
		mainPanel.add(typePanel);
		typePanel.setLayout(null);
		
		btnOk = new JButton("µ«»Î");
		btnOk.setFocusable(false);
		btnOk.setOpaque(false);
		btnOk.setBounds(10, 132, 93, 23);
		typePanel.add(btnOk);
		
		btnCancel = new JButton("»°œ˚");
		btnCancel.setFocusable(false);
		btnCancel.setOpaque(false);
		btnCancel.setBounds(197, 132, 93, 23);
		typePanel.add(btnCancel);
		
		registPanel = new JPanel();
		registPanel.setOpaque(false);
		registPanel.setBounds(10, 10, 280, 112);
		registPanel.setLayout(null);
		
		JLabel lblName = new JLabel("”√ªß√˚:");
		lblName.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
		lblName.setBounds(24, 20, 54, 15);
		registPanel.add(lblName);
		
		textRName = new JTextField();
		textRName.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
		textRName.setBounds(88, 15, 150, 25);
		registPanel.add(textRName);
		textRName.setColumns(10);
		
		JLabel lblRPwd = new JLabel("√‹   ¬Î:");
		lblRPwd.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
		lblRPwd.setBounds(24, 70, 54, 15);
		registPanel.add(lblRPwd);
		
		pwdRField = new JPasswordField();
		pwdRField.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
		pwdRField.setBounds(88, 65, 150, 25);
		registPanel.add(pwdRField);
		
		loginPanel = new JPanel();
		loginPanel.setOpaque(false);
		loginPanel.setBackground(Color.WHITE);
		loginPanel.setBounds(10, 10, 280, 112);
		loginPanel.setLayout(null);
		
		textUserId = new JTextField("10001");
		textUserId.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
		textUserId.setColumns(10);
		textUserId.setBounds(66, 10, 160, 25);
		loginPanel.add(textUserId);
		
		lblType = new JLabel("◊¢≤·");
		lblType.setForeground(Color.BLUE);
		lblType.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 13));
		lblType.setBounds(135, 136, 34, 15);
		
		JLabel lblUser = new JLabel("");
		lblUser.setIcon(PictureUtil.USER_ID_ICO);
		lblUser.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
		lblUser.setBounds(36, 12, 20, 20);
		loginPanel.add(lblUser);
		
		JLabel lblPwd = new JLabel("");
		lblPwd.setIcon(PictureUtil.PWD_ICO);
		lblPwd.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
		lblPwd.setBounds(36, 65, 20, 20);
		loginPanel.add(lblPwd);
		
		pwdLField = new JPasswordField("www12345");
		pwdLField.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
		pwdLField.setBounds(66, 64, 160, 25);
		loginPanel.add(pwdLField);
		uCtrl=new UserDialogCtrl(this);
//		typePanel.add(registPanel);
		showLogin();
		setVisible(false);
	}
	/**
	 * œ‘ æ”√ªßµ«»Î
	 */
	public void showLogin(){
		setVisible(true);
		typeUi=loginUi;
		lblTitle.setText("”√ªßµ«»Î");
		lblType.setText("◊¢≤·");
		btnOk.setText("µ«»Î");
		typePanel.add(registPanel);
		typePanel.remove(registPanel);
		typePanel.add(loginPanel);
		typePanel.add(lblType);
		repaint();
	}
	/**
	 * œ‘ æ”√ªß◊¢≤·
	 */
	public void showRegist(){
		setVisible(true);
		typeUi=registUi;
		lblTitle.setText("”√ªß◊¢≤·");
		lblType.setText("µ«»Î");
		btnOk.setText("◊¢≤·");
		typePanel.add(loginPanel);
		typePanel.remove(loginPanel);
		typePanel.add(registPanel);
		repaint();
	}
	public JLabel getLblExit() {
		return lblExit;
	}

	public JButton getBtnOk() {
		return btnOk;
	}

	public JButton getBtnCancel() {
		return btnCancel;
	}

	public JTextField getTextId() {
		return textUserId;
	}

	public JPasswordField getPwdLField() {
		return pwdLField;
	}

	public JTextField getTextName() {
		return textRName;
	}

	public JPasswordField getPwdRField() {
		return pwdRField;
	}

	public UserDialogCtrl getuCtrl() {
		return uCtrl;
	}

	public int getTypeUi() {
		return typeUi;
	}

	public int isLoginUi() {
		return loginUi;
	}

	public int isRegistUi() {
		return registUi;
	}

	public JLabel getLblType() {
		return lblType;
	}

	public BackPanel getMainPanel() {
		return mainPanel;
	}
	
}
