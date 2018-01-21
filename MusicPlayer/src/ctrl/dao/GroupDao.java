package ctrl.dao;

import model.Group;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class GroupDao extends BaseDao {
	/**
	 * 添加分组
	 * @param groupName
	 * @return true添加成功
	 */
	public boolean addGroup(String groupName){
		int row=0;
		boolean isOk=false;
		String sql="insert into tb_group values (null,'"+groupName+"')";
		String seq="update sqlite_sequence set seq = "+getMaxId()+" where name='tb_group'";
		update(seq);
		row=update(sql);
		if (row!=0) {
			isOk=true;
		}
		return isOk;
	}
	/**
	 * 删除分组
	 * @param groupId
	 * @return true为删除成功
	 */
	public boolean deleteGroup(String groupId){
		int row=0;
		boolean isOk=false;
		String sql="delete from tb_group where groupId="+groupId;
		row=update(sql);
		if (row!=0) {
			isOk=true;
		}
		return isOk;
	}
	/**
	 * 获取自增长最大的ID
	 * @return
	 */
	public String getMaxId(){
		String strId=null;
		ResultSet idSet=null;
		ArrayList<String> strIdList=new ArrayList<String>();
		String sql="select groupId from tb_group order by groupId asc";
		idSet=select(sql);
		try {
			while (idSet.next()) {
				strIdList.add(idSet.getString("groupId"));
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
	 * 重命名分组
	 * @param groupId
	 * @param newGroupName
	 * @return true则修改成功
	 */
	public boolean renameGroupName(String groupId,String newGroupName){
		int row=0;
		boolean isOk=false;
		String sql="update tb_group set groupName='"+newGroupName+"' where groupId="+groupId;
		row=update(sql);
		if (row!=0) {
			isOk=true;
		}
		return isOk;
	}
	/**
	 * 获取分组信息
	 * @return 所有分组ID
	 */
	public ArrayList<Group> getGroup(){
		ResultSet set=null;
		String sql="select * from tb_group";
		set=select(sql);
		return getGroupInfo(set);
	}
	/**
	 * 获取分组信息
	 * @return 所有分组ID
	 */
	private ArrayList<Group> getGroupInfo(ResultSet set){
		ArrayList<Group> groupList=new ArrayList<Group>();
		try {
			while(set.next()) {
				Group group=new Group();
				group.setGroupId(set.getString("groupId"));
				group.setGroupName(set.getString("groupName"));
				groupList.add(group);
			}
		} catch (SQLException e) {
		}
		return groupList;
	}
}
