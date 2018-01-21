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
	 * 用户收藏的信息
	 * @param musicList
	 */
	public MusicPanel(int i){
		init();
		new MusicPanelCtrl(this, 0);
	}
	/**
	 * 音乐管理的信息
	 */
	public MusicPanel(){
		init();
		new MusicPanelCtrl(this);
	}
	/**
	 * 初始化
	 * @param musicList
	 */
	public void init(){
		setLayout(new BorderLayout());
		setOpaque(false);
		colName.add("歌曲ID");
		colName.add("歌曲名");
		colName.add("歌手");
		colName.add("歌曲文件路径");
		colName.add("歌曲时长");
		colName.add("下载次数");

		tableModel=new DefaultTableModel(colName,0);
		table=new MyTable();
		table.setOpaque(false);
		table.setModel(tableModel);
		table.setFont(new Font("微软雅黑", Font.PLAIN, 13));
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
	 * 插入歌曲信息
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
	 * 插入歌曲信息
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
