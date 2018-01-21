package view.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ctrl.viewctrl.SetDialogCtrl;
import util.Const;
import util.PictureUtil;
import view.ui.BackPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SetDialog extends JDialog {
	
	private static SetDialog tDialog;
	private BackPanel mainPanel;
	private JLabel lblTitle;
	private JLabel lblExit;
	private JButton btnCancel;
	private JButton btnOk;
	private JTextField txtListening;
	private JTextField txtDownload;
	private JTextField txtNumber;
	private JLabel lblListeningPath;
	private JPanel typePanel;
	private JLabel lblDownloadPath;
	private JLabel lblDefault;
	
	
	public static SetDialog getSD() {
		if (tDialog==null) {
			tDialog=new SetDialog();
		}
		return tDialog;
	}
	
	public SetDialog() {
		setUndecorated(true);
		setSize(357,200);
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
		
		btnOk = new JButton("Ó¦ÓÃ");
		btnOk.setFocusable(false);
		btnOk.setOpaque(false);
		btnOk.setBounds(145, 145, 80, 23);
		typePanel.add(btnOk);
		
		btnCancel = new JButton("È¡Ïû");
		btnCancel.setFocusable(false);
		btnCancel.setOpaque(false);
		btnCancel.setBounds(266, 145, 80, 23);
		typePanel.add(btnCancel);
		
		lblTitle = new JLabel("ÉèÖÃ");
		lblTitle.setIcon(PictureUtil.TIP_ICO);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 15));
		lblTitle.setBounds(0, 0, 175, 20);
		titlePanel.add(lblTitle);
		
		lblExit = new JLabel("¡Á");
		lblExit.setBounds(336, 0, 20, 20);
		titlePanel.add(lblExit);
		lblExit.setForeground(new Color(255, 255, 255, 180));
		lblExit.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 25));
		GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
		gl_mainPanel.setHorizontalGroup(
			gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup()
					.addGroup(gl_mainPanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(titlePanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(typePanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE))
					.addContainerGap(44, Short.MAX_VALUE))
		);
		gl_mainPanel.setVerticalGroup(
			gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup()
					.addGap(1)
					.addComponent(titlePanel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(typePanel, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
		);
		
		JLabel lblListeing = new JLabel("»º´æÂ·¾¶£º");
		lblListeing.setForeground(Color.GRAY);
		lblListeing.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		lblListeing.setBounds(10, 23, 74, 15);
		typePanel.add(lblListeing);
		
		txtListening = new JTextField();
		txtListening.setEditable(false);
		txtListening.setText(Const.listeingPath);
		txtListening.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		txtListening.setSelectedTextColor(Color.BLACK);
		txtListening.setBounds(110, 20, 170, 21);
		typePanel.add(txtListening);
		txtListening.setColumns(10);
		
		lblListeningPath = new JLabel("¸ü¸ÄÂ·¾¶");
		lblListeningPath.setForeground(Color.GRAY);
		lblListeningPath.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		lblListeningPath.setBounds(290, 23, 56, 15);
		typePanel.add(lblListeningPath);
		
		JLabel lblDownload = new JLabel("ÏÂÔØÂ·¾¶£º");
		lblDownload.setForeground(Color.GRAY);
		lblDownload.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		lblDownload.setBounds(10, 63, 74, 15);
		typePanel.add(lblDownload);
		
		txtDownload = new JTextField();
		txtDownload.setEditable(false);
		txtDownload.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		txtDownload.setText(Const.downloadPath);
		txtDownload.setSelectedTextColor(Color.BLACK);
		txtDownload.setDisabledTextColor(Color.WHITE);
		txtDownload.setColumns(10);
		txtDownload.setBounds(110, 60, 170, 21);
		typePanel.add(txtDownload);
		
		lblDownloadPath = new JLabel("¸ü¸ÄÂ·¾¶");
		lblDownloadPath.setForeground(Color.GRAY);
		lblDownloadPath.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		lblDownloadPath.setBounds(290, 63, 56, 15);
		typePanel.add(lblDownloadPath);
		
		JLabel lblNumber = new JLabel("Í¬Ê±ÏÂÔØÊý£º");
		lblNumber.setForeground(Color.GRAY);
		lblNumber.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		lblNumber.setBounds(10, 102, 91, 15);
		typePanel.add(lblNumber);
		
		txtNumber = new JTextField();
		txtNumber.setText(Integer.toString(Const.downloadNum));
		txtNumber.setBounds(110, 100, 66, 21);
		typePanel.add(txtNumber);
		txtNumber.setColumns(10);
		
		lblDefault = new JLabel("Ä¬ÈÏÂ·¾¶");
		lblDefault.setForeground(Color.GRAY);
		lblDefault.setToolTipText("Ä¬ÈÏÂ·¾¶Îª¹¤³ÌÏÂµÄÂ·¾¶");
		lblDefault.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		lblDefault.setBounds(224, 103, 56, 15);
		typePanel.add(lblDefault);
		mainPanel.setLayout(gl_mainPanel);
		new SetDialogCtrl(this);
	}

	public BackPanel getMainPanel() {
		return mainPanel;
	}
	public JLabel getLblExit() {
		return lblExit;
	}
	public JButton getBtnCancel() {
		return btnCancel;
	}
	public JButton getBtnOk() {
		return btnOk;
	}
	public JTextField getTxtListening() {
		return txtListening;
	}
	public JTextField getTxtDownload() {
		return txtDownload;
	}
	public JTextField getTxtNumber() {
		return txtNumber;
	}

	public JLabel getLblDownloadPath() {
		return lblDownloadPath;
	}

	public JLabel getLblListeningPath() {
		return lblListeningPath;
	}

	public JLabel getLblDefault() {
		return lblDefault;
	}
	
}
