package ctrl.dao;

import model.Group;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class GroupDao extends BaseDao {
	/**
	 * ��ӷ���
	 * @param groupName
	 * @return true��ӳɹ�
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
	 * ɾ������
	 * @param groupId
	 * @return trueΪɾ���ɹ�
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
	 * ��ȡ����������ID
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
	 * ����������
	 * @param groupId
	 * @param newGroupName
	 * @return true���޸ĳɹ�
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
	 * ��ȡ������Ϣ
	 * @return ���з���ID
	 */
	public ArrayList<Group> getGroup(){
		ResultSet set=null;
		String sql="select * from tb_group";
		set=select(sql);
		return getGroupInfo(set);
	}
	/**
	 * ��ȡ������Ϣ
	 * @return ���з���ID
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
