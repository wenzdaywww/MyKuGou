package view.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ctrl.viewctrl.UploadDialogCtrl;
import util.PictureUtil;
import view.ui.BackPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class UploadDialog extends JDialog {
	
	private static UploadDialog userDialog;
	private BackPanel mainPanel;
	private JLabel lblExit;
	private JButton btnUpload;
	private JButton btnCancel;
	private JPanel typePanel;
	private JLabel lblTitle;
	private JTextField textName;
	private JTextField textSinger;
	private JTextField textTime;
	private JTextField textPath;
	private JLabel label_1;
	
	public static UploadDialog getUD(){
		if (userDialog==null) {
			userDialog=new UploadDialog();
		}
		return userDialog;
	}

	public UploadDialog(){
		setUndecorated(true);
		setSize(300, 291);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		
		mainPanel = new BackPanel();
		mainPanel.setOpaque(false);
		setContentPane(mainPanel);
		mainPanel.setLayout(null);
		mainPanel.setImage(PictureUtil.SKIN);
		
		lblTitle = new JLabel("上传歌曲信息");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setIcon(PictureUtil.TIP_ICO);
		lblTitle.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblTitle.setBounds(0, 0, 244, 35);
		mainPanel.add(lblTitle);
		
		lblExit = new JLabel("×");
		lblExit.setBounds(280, 0, 20, 20);
		lblExit.setForeground(new Color(255, 255, 255, 180));
		lblExit.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		mainPanel.add(lblExit);
		
		typePanel = new JPanel();
		typePanel.setBorder(new LineBorder(Color.GRAY));
		typePanel.setBackground(Color.WHITE);
		typePanel.setBounds(0, 35, 300, 256);
		mainPanel.add(typePanel);
		typePanel.setLayout(null);
		
		btnUpload = new JButton("上传");
		btnUpload.setFocusable(false);
		btnUpload.setOpaque(false);
		btnUpload.setBounds(10, 223, 93, 23);
		typePanel.add(btnUpload);
		
		btnCancel = new JButton("取消");
		btnCancel.setFocusable(false);
		btnCancel.setOpaque(false);
		btnCancel.setBounds(197, 223, 93, 23);
		typePanel.add(btnCancel);
		
		JLabel lblNewLabel = new JLabel("歌曲名：");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel.setBounds(26, 22, 70, 15);
		typePanel.add(lblNewLabel);
		
		textName = new JTextField();
		textName.setBounds(106, 20, 169, 21);
		typePanel.add(textName);
		textName.setColumns(10);
		
		JLabel label = new JLabel("歌   手：");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label.setBounds(26, 73, 70, 15);
		typePanel.add(label);
		
		textSinger = new JTextField();
		textSinger.setColumns(10);
		textSinger.setBounds(106, 71, 169, 21);
		typePanel.add(textSinger);
		
		JLabel label_2 = new JLabel("歌曲时长：");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_2.setBounds(26, 120, 70, 15);
		typePanel.add(label_2);
		
		textTime = new JTextField();
		textTime.setColumns(10);
		textTime.setBounds(106, 118, 169, 21);
		typePanel.add(textTime);
		
		textPath = new JTextField();
		textPath.setEditable(false);
		textPath.setColumns(10);
		textPath.setBounds(106, 165, 169, 21);
		typePanel.add(textPath);
		
		label_1 = new JLabel("文件地址：");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_1.setBounds(26, 167, 70, 15);
		typePanel.add(label_1);
		
		new UploadDialogCtrl(this);
	}
	/**
	 * 显示要传的歌曲信息
	 */
	public void showMusicInfo(String musicName,String singer,String path,String musicTime){
		textName.setText(musicName);
		textSinger.setText(singer);
		textPath.setText(path);
		textTime.setText(musicTime);
	}
	public JLabel getLblExit() {
		return lblExit;
	}

	public JButton getBtnUpload() {
		return btnUpload;
	}

	public JButton getBtnCancel() {
		return btnCancel;
	}

	public BackPanel getMainPanel() {
		return mainPanel;
	}

	public JTextField getTextName() {
		return textName;
	}

	public JTextField getTextSinger() {
		return textSinger;
	}

	public JTextField getTextTime() {
		return textTime;
	}

	public JTextField getTextPath() {
		return textPath;
	}
	
}
