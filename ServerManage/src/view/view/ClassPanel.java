package view.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ctrl.viewctrl.ClassPanelCtrl;
import model.MClass;
import view.ui.MyTable;

@SuppressWarnings("serial")
public class ClassPanel extends JPanel {
	private MyTable table;
	private DefaultTableModel tableModel;
	private Vector<String> colName=new Vector<String>();

	public ClassPanel(){
		init();
		new ClassPanelCtrl(this);
	}
	/**
	 * 初始化
	 * @param musicList
	 */
	public void init(){
		setLayout(new BorderLayout());
		setBounds(257, 145, 500, 99);
		colName.add("分组ID");
		colName.add("分组名");
		table=new MyTable();
		tableModel=new DefaultTableModel(colName,0);
		table.setModel(tableModel);
		table.setOpaque(false);
		table.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		table.setRowHeight(25);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(249);
		table.getColumnModel().getColumn(1).setPreferredWidth(249);

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
	public void insertMClass(ArrayList<MClass> classList){
		for (int i = 0; i < classList.size(); i++) {
			Vector<String> vector = new Vector<String>(); 
			vector.add(classList.get(i).getClassId());
			vector.add(classList.get(i).getName());
			tableModel.addRow(vector);
		}
	}
	/**
	 * 初始化类数据
	 * @param classList
	 */
	public void insertMClass(MClass mClass){
		Vector<String> vector = new Vector<String>(); 
		vector.add(mClass.getClassId());
		vector.add(mClass.getName());
		tableModel.addRow(vector);
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
