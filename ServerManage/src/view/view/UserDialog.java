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
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class UserDialog extends JDialog {
	
	private static UserDialog userDialog;
	private BackPanel mainPanel;
	private JLabel lblExit;
	private JButton btnOk;
	private JButton btnCancel;
	private JPanel typePanel;
	private JPanel registPanel;
	private JTextField textRName;
	private JPasswordField pwdRField;
	private JLabel lblTitle;
	
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
		
		lblTitle = new JLabel("Ìí¼ÓÓÃ»§");
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setIcon(PictureUtil.USER_ICO);
		lblTitle.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		lblTitle.setBounds(0, 0, 244, 35);
		mainPanel.add(lblTitle);
		
		lblExit = new JLabel("¡Á");
		lblExit.setBounds(280, 0, 20, 20);
		lblExit.setForeground(new Color(255, 255, 255, 180));
		lblExit.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 25));
		mainPanel.add(lblExit);
		
		typePanel = new JPanel();
		typePanel.setBorder(new LineBorder(Color.GRAY));
		typePanel.setBackground(Color.WHITE);
		typePanel.setBounds(0, 35, 300, 165);
		mainPanel.add(typePanel);
		typePanel.setLayout(null);
		
		btnOk = new JButton("×¢²á");
		btnOk.setFocusable(false);
		btnOk.setOpaque(false);
		btnOk.setBounds(10, 132, 93, 23);
		typePanel.add(btnOk);
		
		btnCancel = new JButton("È¡Ïû");
		btnCancel.setFocusable(false);
		btnCancel.setOpaque(false);
		btnCancel.setBounds(197, 132, 93, 23);
		typePanel.add(btnCancel);
		
		registPanel = new JPanel();
		registPanel.setOpaque(false);
		registPanel.setBounds(10, 10, 280, 112);
		registPanel.setLayout(null);
		
		JLabel lblName = new JLabel("ÓÃ»§Ãû:");
		lblName.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		lblName.setBounds(24, 20, 54, 15);
		registPanel.add(lblName);
		
		textRName = new JTextField();
		textRName.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		textRName.setBounds(88, 15, 150, 25);
		registPanel.add(textRName);
		textRName.setColumns(10);
		
		JLabel lblRPwd = new JLabel("ÃÜ   Âë:");
		lblRPwd.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		lblRPwd.setBounds(24, 70, 54, 15);
		registPanel.add(lblRPwd);
		
		pwdRField = new JPasswordField();
		pwdRField.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		pwdRField.setBounds(88, 65, 150, 25);
		registPanel.add(pwdRField);
		
		typePanel.add(registPanel);
		new UserDialogCtrl(this);
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

	public JTextField getTextName() {
		return textRName;
	}

	public JPasswordField getPwdRField() {
		return pwdRField;
	}

	public BackPanel getMainPanel() {
		return mainPanel;
	}
	
}
