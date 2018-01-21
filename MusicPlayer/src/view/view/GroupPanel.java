package view.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Collect;
import model.Group;
import util.PictureUtil;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import javax.swing.LayoutStyle.ComponentPlacement;

import ctrl.viewctrl.GroupPanelCtrl;

@SuppressWarnings("serial")
public class GroupPanel extends JPanel {
	
	private Group group;
	private JLabel lblOpen;
	private JLabel lblName;
	private JLabel lblNumber;
	private JLabel lblOption;
	private boolean isOpen=false;
	private boolean canDelete;
	private Collect collect;
	/**
	 * 下载列表的分组
	 * @param msg
	 * @param downloadNum
	 */
	public GroupPanel(String msg,int downloadNum){
		init();
		lblName.setText(msg);
		lblOption.setVisible(false);
		isOpen=true;
		lblOpen.setIcon(PictureUtil.OPEN_ICO);
		setLblNumber(downloadNum);
		new GroupPanelCtrl(this, 0);
	}
	/**
	 * 收藏列表信息显示
	 * @param msg
	 */
	public GroupPanel(String msg){
		init();
		lblOpen.setVisible(false);
		lblNumber.setVisible(false);
		lblOption.setVisible(false);
		lblName.setText(msg);
	}
	/**
	 * 收藏列表的分组
	 * @param collect
	 */
	public GroupPanel(Collect collect){
		this.collect=collect;
		init();
		new GroupPanelCtrl(this,collect);
		lblName.setText(this.collect.getName());
	}
	/**
	 * 本地列表的分组
	 * @param group
	 * @param canDelete
	 */
	public GroupPanel(Group group,boolean canDelete) {
		this.group=group;
		this.canDelete=canDelete;
		init();
		new GroupPanelCtrl(this);
		lblName.setText(this.group.getGroupName());
	}
	private void init(){
		setSize(267,35);
		setBackground(Color.white);
		
		lblOpen = new JLabel();
		lblOpen.setIcon(PictureUtil.OPEN_ICO);
		
		lblName = new JLabel();
		lblName.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		
		lblNumber = new JLabel("[10]");
		lblNumber.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		
		lblOption = new JLabel();
		lblOption.setIcon(PictureUtil.OPTION_ICO);
		lblOption.setToolTipText("列表菜单");
		lblOption.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblOpen, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(2)
					.addComponent(lblName)
					.addGap(5)
					.addComponent(lblNumber)
					.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
					.addComponent(lblOption, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNumber, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblOpen)
							.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblOption, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
	
	public JLabel getLblOpen() {
		return lblOpen;
	}
	public JLabel getLblName() {
		return lblName;
	}
	public void setLblNumber(int num) {
		lblNumber.setText("["+num+"]");
	}
	public JLabel getLblOption() {
		return lblOption;
	}
	public Group getGroup() {
		return group;
	}
	public boolean isOpen() {
		return isOpen;
	}
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	public boolean isCanDelete() {
		return canDelete;
	}

	public JLabel getLblNumber() {
		return lblNumber;
	}
	
}
