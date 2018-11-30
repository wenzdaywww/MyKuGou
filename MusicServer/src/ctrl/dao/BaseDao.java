package ctrl.dao;

import java.sql.*;
import javax.swing.JOptionPane;
import util.DBConnect;


public class BaseDao {

	private Statement stmt;

	public BaseDao(){
		super();
		if (stmt==null) {
				try {
					stmt=DBConnect.getCon().createStatement();
				} catch (ClassNotFoundException | SQLException e) {
					JOptionPane.showConfirmDialog(null, "���ݿ�����ʧ�ܣ�", "��ʾ", JOptionPane.CLOSED_OPTION);
				}
		}
	}
	/**
	 * ���ݿ��ѯ
	 * @param sql
	 * @return
	 */
	public ResultSet select(String sql){
		synchronized(BaseDao.class){
			ResultSet set=null;
			if (stmt!=null) {
				try {
					set=stmt.executeQuery(sql);
				} catch (SQLException e) {
					JOptionPane.showConfirmDialog(null, "��ѯʧ�ܣ�", "��ʾ", JOptionPane.CLOSED_OPTION);
				}
			}
			return set;
		}
	}
	/**
	 * ���ݿ����
	 * @param sql
	 * @return
	 */
	public synchronized int update(String sql){
		synchronized(BaseDao.class){
			int row=0;
			if (stmt!=null) {
				try {
					row=stmt.executeUpdate(sql);
					stmt.clearBatch();
				} catch (SQLException e) {
					JOptionPane.showConfirmDialog(null, "�޸�ʧ�ܣ�", "��ʾ", JOptionPane.CLOSED_OPTION);
				}
			}
			return row;
		}
	}
}
