package view.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import ctrl.viewctrl.LocalSongPanCtrl;
import model.Collect;
import model.Group;
import model.MusicInfo;
import util.PictureUtil;

@SuppressWarnings("serial")
public class LocalSongPan extends JPanel{
	private JLabel lblName;
	private JLabel lblDel;
	private JLabel lblLove;
	private JLabel lblTime;
	private MusicInfo mInfo;
	private LocalSongPanCtrl sCtrl;
	/**
	 * 收藏歌曲条的构造
	 * @param mInfo
	 * @param collect
	 * @wbp.parser.constructor
	 */
	public LocalSongPan(MusicInfo mInfo,Collect collect){
		init(mInfo);
		lblLove.setIcon(PictureUtil.DEL_LOVE_ICO);
		lblLove.setToolTipText("取消收藏");
		sCtrl=new LocalSongPanCtrl(this,collect);
	}
	/**
	 * 本地歌曲的构造
	 * @param mInfo
	 * @param group
	 */
	public LocalSongPan(MusicInfo mInfo,Group group) {
		init(mInfo);
		lblLove.setToolTipText("收藏");
		sCtrl=new LocalSongPanCtrl(this,group);
	}
	/**
	 * 组件初始化
	 */
	private void init(MusicInfo mInfo){
		this.mInfo=mInfo;
		setSize(267,30);
		setBackground(Color.WHITE);
		lblName = new JLabel();
		if (this.mInfo.getSinger().equals("未知")) {
			lblName.setText(this.mInfo.getMusicName());
		}else {
			lblName.setText(this.mInfo.getSinger()+" - "+this.mInfo.getMusicName());
		}
		lblName.setVerifyInputWhenFocusTarget(false);
		lblName.setOpaque(false);
		lblName.setToolTipText(lblName.getText());
		lblName.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		
		lblDel = new JLabel();
		lblDel.setIcon(PictureUtil.DEL_ICO);
		lblDel.setToolTipText("删除");
		lblDel.setVisible(false);
		lblDel.setOpaque(false);
		
		lblLove = new JLabel();
		lblLove.setIcon(PictureUtil.ADD_LOVE_ICO);
		lblLove.setToolTipText("我喜欢");
		lblLove.setVisible(false);
		lblLove.setOpaque(false);
		
		lblTime = new JLabel(this.mInfo.getMusicTime());
		lblTime.setOpaque(false);
		lblTime.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblDel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLove, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblTime)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(9)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblName, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTime, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
							.addGap(5))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblLove, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
								.addComponent(lblDel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
							.addGap(10))))
		);
		setLayout(groupLayout);
	}
	public JLabel getLblName() {
		return lblName;
	}
	public JLabel getLblDel() {
		return lblDel;
	}
	public JLabel getLblLove() {
		return lblLove;
	}
	public JLabel getLblTime() {
		return lblTime;
	}
	public MusicInfo getmInfo() {
		return mInfo;
	}
	public LocalSongPanCtrl getsCtrl() {
		return sCtrl;
	}
	
}
