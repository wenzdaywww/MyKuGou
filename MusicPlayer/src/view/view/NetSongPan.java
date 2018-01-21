package view.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import ctrl.viewctrl.NetSongPanCtrl;
import model.MusicInfo;
import util.PictureUtil;

@SuppressWarnings("serial")
public class NetSongPan extends JPanel {
	
	private JLabel lblName;
	private JLabel lblListen;
	private JLabel lblDownload;
	private JLabel lblTime;
	private JLabel lblCollect;
	
	/**
	 * 未搜索到结果构造方法
	 * @wbp.parser.constructor
	 */
	public NetSongPan(String msg){
		setSize(300,30);
		setBackground(Color.WHITE);
		JLabel lblMsg = new JLabel(msg);
		lblMsg.setIcon(PictureUtil.WARN_ICO);
		lblMsg.setForeground(Color.BLACK);
		lblMsg.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		add(lblMsg);
	}
	/**
	 * 网络歌曲条构造方法
	 * @param num
	 * @param mInfo
	 */
	public NetSongPan(int num,MusicInfo mInfo) {
		init(num,mInfo);
	}
	/**
	 * 组件初始化
	 */
	private void init(int num,MusicInfo mInfo){
		setSize(315,30);
		setBackground(Color.WHITE);
		lblName = new JLabel();
		if (mInfo.getSinger().equals("未知")) {
			lblName.setText(mInfo.getMusicName());
		}else {
			lblName.setText(mInfo.getSinger()+" - "+mInfo.getMusicName());
		}
		lblName.setVerifyInputWhenFocusTarget(false);
		lblName.setOpaque(false);
		lblName.setToolTipText(lblName.getText());
		lblName.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		
		lblListen = new JLabel();
		lblListen.setBackground(Color.WHITE);
		lblListen.setIcon(PictureUtil.LISTEN_ICO);
		lblListen.setToolTipText("试听");
		lblListen.setOpaque(false);
		
		lblDownload = new JLabel();
		lblDownload.setIcon(PictureUtil.DOWNLOAD_ICO);
		lblDownload.setToolTipText("下载");
		lblDownload.setOpaque(false);
		
		lblTime = new JLabel(mInfo.getMusicTime());
		lblTime.setOpaque(false);
		lblTime.setToolTipText("歌曲下载量："+mInfo.getPlayCount()+"次");
		lblTime.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		
		JLabel lblNum = new JLabel(Integer.toString(num));
		lblNum.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNum.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblCollect = new JLabel();
		lblCollect.setToolTipText("收藏该歌曲");
		lblCollect.setOpaque(false);
		lblCollect.setBackground(Color.WHITE);
		lblCollect.setIcon(PictureUtil.ADD_LOVE_ICO);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblNum, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblCollect, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblListen, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDownload, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblTime, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(5))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCollect, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblListen, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDownload, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTime, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNum, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(5, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		new NetSongPanCtrl(this,mInfo);
	}
	public JLabel getLblName() {
		return lblName;
	}
	public JLabel getLblListen() {
		return lblListen;
	}
	public JLabel getLblDownload() {
		return lblDownload;
	}
	public JLabel getLblTime() {
		return lblTime;
	}
	public JLabel getLblCollect() {
		return lblCollect;
	}
	
}
