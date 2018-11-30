package ctrl.dao;

import java.sql.*;
import util.DBConnect;
import javax.swing.JOptionPane;

public class BaseDao {

	private Statement stmt;

	public BaseDao(){
		super();
		if (stmt==null) {
			try {
				stmt=DBConnect.getCon().createStatement();
			} catch (ClassNotFoundException | SQLException e) {
				JOptionPane.showConfirmDialog(null, "数据库连接失败！", "提示", JOptionPane.CLOSED_OPTION);
			}
		}
	}
	/**
	 * 数据库查询
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
					JOptionPane.showConfirmDialog(null, "查询失败！", "提示", JOptionPane.CLOSED_OPTION);
				}
			}
			return set;
		}
	}
	/**
	 * 数据库更新
	 * @param sql
	 * @return
	 */
	public int update(String sql){
		synchronized(BaseDao.class){
			int row=0;
			if (stmt!=null) {
				try {
					row=stmt.executeUpdate(sql);
					stmt.clearBatch();
				} catch (SQLException e) {
					JOptionPane.showConfirmDialog(null, "修改失败！", "提示", JOptionPane.CLOSED_OPTION);
				}
			}
			return row;
		}
	}
}
