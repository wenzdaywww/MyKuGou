package view.view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class MusicInfoDlg extends JDialog{
	
	private static MusicInfoDlg miDlg;
	private JLabel lblName;
	private JLabel lblStyle;
	private JLabel lblPlayCount;
	
	public static MusicInfoDlg getMID(){
		if (miDlg==null) {
			miDlg=new MusicInfoDlg();
		}
		return miDlg;
	}
	
	private MusicInfoDlg() {
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setUndecorated(true);
		setSize(200, 70);
		setAlwaysOnTop(true);
		
		JPanel mPanel=new JPanel();
		mPanel.setOpaque(false);
		mPanel.setBorder(new LineBorder(Color.GRAY));
		setContentPane(mPanel);
		
		getContentPane().setLayout(null);
		
		lblName = new JLabel("�ҵ�����");
		lblName.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		lblName.setBounds(10, 5, 180, 20);
		getContentPane().add(lblName);
		
		lblStyle = new JLabel("��ʽ��MP3");
		lblStyle.setForeground(Color.GRAY);
		lblStyle.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		lblStyle.setBounds(10, 40, 85, 20);
		getContentPane().add(lblStyle);
		
		lblPlayCount = new JLabel("���ţ�0��");
		lblPlayCount.setForeground(Color.GRAY);
		lblPlayCount.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		lblPlayCount.setBounds(107, 40, 85, 20);
		getContentPane().add(lblPlayCount);
	}
	/**
	 * ������Ϣ��ʾ
	 * @param mInfo
	 */
	public void showInfo(int x,int y,String name, String style,String playCount){
		setLocation(x, y);
		lblName.setText(name);
		lblStyle.setText("��ʽ��"+style);
		lblPlayCount.setText("���ţ�"+playCount+"��");
	}
}
