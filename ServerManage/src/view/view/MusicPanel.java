package view.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ctrl.viewctrl.MusicPanelCtrl;
import model.MusicInfo;
import view.ui.MyTable;

@SuppressWarnings("serial")
public class MusicPanel extends JPanel {

	private MyTable table;
	private Vector<String> colName=new Vector<String>();
	private DefaultTableModel tableModel;
	
	/**
	 * �û��ղص���Ϣ
	 * @param musicList
	 */
	public MusicPanel(int i){
		init();
		new MusicPanelCtrl(this, 0);
	}
	/**
	 * ���ֹ������Ϣ
	 */
	public MusicPanel(){
		init();
		new MusicPanelCtrl(this);
	}
	/**
	 * ��ʼ��
	 * @param musicList
	 */
	public void init(){
		setLayout(new BorderLayout());
		setOpaque(false);
		colName.add("����ID");
		colName.add("������");
		colName.add("����");
		colName.add("�����ļ�·��");
		colName.add("����ʱ��");
		colName.add("���ش���");

		tableModel=new DefaultTableModel(colName,0);
		table=new MyTable();
		table.setOpaque(false);
		table.setModel(tableModel);
		table.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		table.setRowHeight(25);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(130);
		table.getColumnModel().getColumn(4).setPreferredWidth(60);
		table.getColumnModel().getColumn(5).setPreferredWidth(60);

		JScrollPane scrollPane=new JScrollPane();
		scrollPane.setViewportView(table);
		scrollPane.setAutoscrolls(true);
		scrollPane.setOpaque(false);
		add(scrollPane,BorderLayout.CENTER);
	}
	/**
	 * ���������Ϣ
	 * @param musicList
	 */
	public void insertMusic(ArrayList<MusicInfo> musicList){
		for (int i = 0; i < musicList.size(); i++) {
			Vector<String> vector = new Vector<String>(); 
			vector.add(musicList.get(i).getMusicId());
			vector.add(musicList.get(i).getMusicName());
			vector.add(musicList.get(i).getSinger());
			vector.add(musicList.get(i).getMusicPath());
			vector.add(musicList.get(i).getMusicTime());
			vector.add(musicList.get(i).getPlayCount());
			tableModel.addRow(vector);
		}
	}
	/**
	 * ���������Ϣ
	 * @param musicList
	 */
	public void insertMusic(MusicInfo music){
		Vector<String> vector = new Vector<String>(); 
		vector.add(music.getMusicId());
		vector.add(music.getMusicName());
		vector.add(music.getSinger());
		vector.add(music.getMusicPath());
		vector.add(music.getMusicTime());
		vector.add(music.getPlayCount());
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
