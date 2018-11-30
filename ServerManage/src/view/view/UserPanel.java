package view.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ctrl.viewctrl.UserPanelCtrl;
import model.UserCollect;
import view.ui.MyTable;

@SuppressWarnings("serial")
public class UserPanel extends JPanel {
	private MyTable table;
	private DefaultTableModel tableModel;
	private Vector<String> colName=new Vector<String>();

	public UserPanel(){
		init();
		new UserPanelCtrl(this);
	}
	/**
	 * 初始化
	 * @param musicList
	 */
	public void init(){
		setLayout(new BorderLayout());
		setSize(500, 150);
		colName.add("用户ID");
		colName.add("收藏表ID");
		colName.add("用户名");
		colName.add("用户密码");
		table=new MyTable();
		tableModel=new DefaultTableModel(colName,0);
		table.setModel(tableModel);
		table.setOpaque(false);
		table.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		table.setRowHeight(25);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		table.getColumnModel().getColumn(1).setPreferredWidth(90);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);

		JScrollPane scrollPane=new JScrollPane();
		scrollPane.setViewportView(table);
		scrollPane.setAutoscrolls(true);
		scrollPane.setOpaque(false);
		add(scrollPane,BorderLayout.CENTER);
	}
	/**
	 * 初始化类数据
	 * @param classList
	 */
	public void insertMClass(ArrayList<UserCollect> userList){
		for (int i = 0; i < userList.size(); i++) {
			Vector<String> vector = new Vector<String>(); 
			vector.add(userList.get(i).getUserId());
			vector.add(userList.get(i).getCollectId());
			vector.add(userList.get(i).getName());
			vector.add(userList.get(i).getPwd());
			tableModel.addRow(vector);
		}
	}

	public Vector<String> getColName() {
		return colName;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}
	public JTable getTable() {
		return table;
	}
}
