package ctrl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.User;
import model.UserCollect;
import util.DBConnect;

public class UserDao extends BaseDao {
	/**
	 * �û�ע��
	 * @param user
	 * @return ע��ɹ����û�ID
	 */
	public String addUser(User user){
		int row=0;
		String registId=getMaxId();
		String sql="insert into tb_user values(null,'"+user.getPwd()+"','"+user.getName()+"')";
		String seq="update sqlite_sequence set seq = "+registId+" where name='tb_user'";
		update(seq);
		row=update(sql);
		if (row!=0) {
			registId=Integer.toString(Integer.parseInt(registId)+1);
		}else {
			registId=null;
		}
		return registId;
	}
	/**
	 * ��ȡ���һ���û���ID
	 * @return ����û�ID
	 */
	private String getMaxId(){
		String strId=null;
		ResultSet idSet=null;
		ArrayList<String> strIdList=new ArrayList<String>();
		String sql="select userId from tb_user order by userId asc";
		idSet=select(sql);
		try {
			while (idSet.next()) {
				strIdList.add(idSet.getString("userId"));
			}
		} catch (SQLException e) {
		}
		if (strIdList.size()>0) {
			strId=strIdList.get(strIdList.size()-1);
		}else {
			strId="0";
		}
		return strId;
	}
	/**
	 * ��ȡ�û����ղر�
	 * @param userId
	 * @return
	 */
	public ArrayList<UserCollect> getUser(String keyWord){
		ResultSet set=null;
		ArrayList<UserCollect> ucList=new ArrayList<UserCollect>();
		String sql;
		if (keyWord.equals("All")) {
			sql="select collectId, b.userId,pwd,b.name from tb_collect a left join "
					+ "tb_user b on a.userId=b.userId order by b.userId asc";
		}else {
			sql="select collectId, b.userId,pwd,b.name from tb_collect a left join tb_user b on a.userId=b.userId "
					+"where b.userId like '%"+keyWord+"%' or b.name like '%"+keyWord+"%' order by b.userId asc";
		}
		set=select(sql);
		try {
			while (set.next()) {
				UserCollect uCollect=new UserCollect();
				uCollect.setCollectId(set.getString("collectId"));
				uCollect.setUserId(set.getString("userId"));
				uCollect.setName(set.getString("name"));
				uCollect.setPwd(set.getString("pwd"));
				ucList.add(uCollect);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ucList;
	}
	/**
	 * �޸ĸ�����Ϣ
	 * @param musicId
	 * @param renameType
	 * @param rename
	 * @return
	 */
	public boolean updateUser(String userId,String renameType,String rename){
		int row=0;
		boolean isOk=false;
		String sql="update tb_user set "+renameType+" = '"+rename+"' where userId="+userId;
		row=update(sql);
		if (row!=0) {
			isOk=true;
		}
		return isOk;
	}
	/**
	 * ɾ���û�
	 * @param musicId
	 * @return
	 */
	public boolean deleteUser(String classId,String userId){
		int row=0;
		boolean isOk=false;
		String sqlm="delete from tb_collectMusic where collectId="+classId;
		String sqlc="delete from tb_collect where userId="+userId;
		String sqlu="delete from tb_user where userId="+userId;
		row=update(sqlu);
		if (row!=0) {
			update(sqlc);
			update(sqlm);
			isOk=true;
		}
		return isOk;
	}
	/**
	 * �û�����
	 * @param userId
	 * @param pwd
	 * @return ��Ϊnull�����ɹ�
	 */
	public User login(String userId,String pwd){
		User user=null;
		ResultSet set;
		try {
			String sql="select * from tb_user where userId= ? and pwd= ? ";
			PreparedStatement pStmt=DBConnect.getCon().prepareStatement(sql);
			pStmt.setString(1, userId);
			pStmt.setString(2, pwd);
			set=pStmt.executeQuery();
			if(set.next()) {
				user=new User();
				user.setUserId(set.getString("userId"));
				user.setPwd(set.getString("pwd"));
				user.setName(set.getString("name"));
				return user;
			}
		} catch (SQLException | ClassNotFoundException e) {
		} 
		return user;
	}
}
