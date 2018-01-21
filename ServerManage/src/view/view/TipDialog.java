package view.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import ctrl.viewctrl.TipDialogCtrl;
import util.PictureUtil;
import view.ui.BackPanel;

@SuppressWarnings("serial")
public class TipDialog extends JDialog {
	
	private static TipDialog tDialog;
	private BackPanel mainPanel;
	private JLabel lblTitle;
	private JLabel lblExit;
	private JButton btnCancel;
	private JTextField txtName;
	private JPanel panelNewName;
	private JPanel typePanel;
	private JPanel panelSure;
	private JButton btnOk;
	private TipDialogCtrl tCtrl;
	private JTextArea txtASureType;
	private JTextArea textMusic;
	
	
	public static TipDialog getTD() {
		if (tDialog==null) {
			tDialog=new TipDialog();
		}
		return tDialog;
	}
	
	public TipDialog() {
		setUndecorated(true);
		setSize(200,180);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		
		mainPanel = new BackPanel();
		mainPanel.setOpaque(true);
		mainPanel.setImage(PictureUtil.SKIN);
		setContentPane(mainPanel);
		
		JPanel titlePanel=new JPanel();
		titlePanel.setOpaque(false);
		titlePanel.setLayout(null);
		titlePanel.setSize(200, 20);
		
		typePanel = new JPanel();
		typePanel.setBorder(new LineBorder(new Color(128, 128, 128)));
		typePanel.setBackground(Color.WHITE);
		typePanel.setLayout(null);
		
		btnOk = new JButton("ȷ��");
		btnOk.setFocusable(false);
		btnOk.setOpaque(false);
		btnOk.setBounds(10, 125, 80, 23);
		typePanel.add(btnOk);
		
		btnCancel = new JButton("ȡ��");
		btnCancel.setFocusable(false);
		btnCancel.setOpaque(false);
		btnCancel.setBounds(110, 125, 80, 23);
		typePanel.add(btnCancel);
		
		panelSure = new JPanel();
		panelSure.setBackground(Color.WHITE);
		panelSure.setBounds(2, 0, 196, 85);
		panelSure.setLayout(null);
		
		panelNewName = new JPanel();
		panelNewName.setBackground(Color.WHITE);
		panelNewName.setBounds(2, 0, 196, 115);
		panelNewName.setLayout(null);
		
		txtName = new JTextField();
		txtName.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		txtName.setColumns(10);
		txtName.setBounds(10, 84, 176, 21);
		panelNewName.add(txtName);
		
		lblTitle = new JLabel("New label");
		lblTitle.setIcon(PictureUtil.TIP_ICO);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		lblTitle.setBounds(0, 0, 175, 20);
		titlePanel.add(lblTitle);
		
		lblExit = new JLabel("��");
		lblExit.setBounds(180, 0, 20, 20);
		titlePanel.add(lblExit);
		lblExit.setForeground(new Color(255, 255, 255, 180));
		lblExit.setFont(new Font("΢���ź�", Font.PLAIN, 25));
		GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
		gl_mainPanel.setHorizontalGroup(
			gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup()
					.addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(titlePanel, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
						.addComponent(typePanel, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_mainPanel.setVerticalGroup(
			gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup()
					.addGap(1)
					.addComponent(titlePanel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(typePanel, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
		);
		mainPanel.setLayout(gl_mainPanel);
		tCtrl=new TipDialogCtrl(this);
		
		txtASureType = new JTextArea();
		txtASureType.setLineWrap(true);
		txtASureType.setRows(2);
		txtASureType.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		txtASureType.setEditable(false);
		txtASureType.setAutoscrolls(false);
		txtASureType.setBounds(10, 10, 180, 75);
		panelSure.add(txtASureType);
		typePanel.add(panelNewName);
		
		textMusic = new JTextArea();
		textMusic.setRows(2);
		textMusic.setLineWrap(true);
		textMusic.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		textMusic.setEditable(false);
		textMusic.setAutoscrolls(false);
		textMusic.setBounds(10, 5, 180, 74);
		panelNewName.add(textMusic);
	}
	/**
	 * �޸�������Ϣ�Ի�����ʾ
	 * @param title
	 */
	public void showMusicInfo(String title,String musicId,String renameType){
		setSize(200,180);
		typePanel.add(panelSure);
		typePanel.remove(panelSure);
		lblTitle.setText(title);
		textMusic.setText("\n"+"�޸ĵĸ���IDΪ��"+musicId+"\n��"+renameType+":");
		typePanel.add(panelNewName);
		btnOk.setLocation(10, getHeight()-55);
		btnCancel.setLocation(getWidth()-90, getHeight()-55);
		btnOk.setVisible(true);
		btnCancel.setVisible(true);
		setVisible(true);
		repaint();
	}
	/**
	 * �޸��û���Ϣ�Ի�����ʾ
	 * @param title
	 */
	public void showUserInfo(String title,String userId,String renameType){
		setSize(200,180);
		typePanel.add(panelSure);
		typePanel.remove(panelSure);
		lblTitle.setText(title);
		textMusic.setText("\n"+"�޸ĵ��û�IDΪ��"+userId+"\n��"+renameType+":");
		typePanel.add(panelNewName);
		btnOk.setLocation(10, getHeight()-55);
		btnCancel.setLocation(getWidth()-90, getHeight()-55);
		btnOk.setVisible(true);
		btnCancel.setVisible(true);
		setVisible(true);
		repaint();
	}
	/**
	 * �޸ķ�����Ϣ�Ի�����ʾ
	 * @param title
	 */
	public void showClassInfo(String title,String classId){
		setSize(200,180);
		typePanel.add(panelSure);
		typePanel.remove(panelSure);
		lblTitle.setText(title);
		textMusic.setText("\n"+"�޸ĵĸ�������IDΪ��"+classId+"\n�·�������:");
		typePanel.add(panelNewName);
		btnOk.setLocation(10, getHeight()-55);
		btnCancel.setLocation(getWidth()-90, getHeight()-55);
		btnOk.setVisible(true);
		btnCancel.setVisible(true);
		setVisible(true);
		repaint();
	}
	/**
	 * ��Ϣȷ�϶Ի�����ʾ
	 * @param title
	 * @param sureType
	 */
	public void showSureMsg(String title,String sureType){
		setSize(200,180);
		typePanel.add(panelNewName);
		lblTitle.setText("");
		typePanel.remove(panelNewName);
		lblTitle.setText(title);
		txtASureType.setText("\n"+sureType);
		typePanel.add(panelSure);
		btnOk.setLocation(10, getHeight()-55);
		btnCancel.setLocation(getWidth()-90, getHeight()-55);
		btnOk.setVisible(true);
		btnCancel.setVisible(true);
		setVisible(true);
		repaint();
	}
	public JPanel getMainPanel() {
		return mainPanel;
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

	public JTextField getTxtName() {
		return txtName;
	}

	public TipDialogCtrl gettCtrl() {
		return tCtrl;
	}

	public JTextArea getTextMusic() {
		return textMusic;
	}
	
}
