package view.view;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;

import ctrl.viewctrl.DownloadPanCtrl;
import model.MusicInfo;

@SuppressWarnings("serial")
public class DownloadPan extends JPanel{
	private JLabel lblName;
	private JProgressBar proBar;
	
	public DownloadPan(MusicInfo mInfo,long max) {
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		setSize(200, 50);
		
		lblName = new JLabel(mInfo.getSinger()+" - "+mInfo.getMusicName());
		lblName.setForeground(new Color(0, 0, 0));
		lblName.setToolTipText(lblName.getText());
		lblName.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		
		proBar = new JProgressBar();
		proBar.setOpaque(true);
		proBar.setBorderPainted(false);
		proBar.setMinimum(0);
		proBar.setMaximum((int)max);
		proBar.setStringPainted(true);
		
		JLabel lblLength = new JLabel(toLength(max)+"MB");
		lblLength.setForeground(Color.GRAY);
		lblLength.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(proBar, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
							.addGap(5)
							.addComponent(lblLength, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(4)
					.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(proBar, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLength, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		setLayout(groupLayout);
		new DownloadPanCtrl(this);
	}
	public JProgressBar getProBar() {
		return proBar;
	}
	public void setProBar(JProgressBar proBar) {
		this.proBar = proBar;
	}
	public JLabel getLblName() {
		return lblName;
	}
	public void setLblName(JLabel lblName) {
		this.lblName = lblName;
	}
	private String toLength(long length){
		DecimalFormat df=new DecimalFormat("#####0.00");
		double d=Double.parseDouble(df.format((double)length/1048576));
		return Double.toString(d);
	}
}
