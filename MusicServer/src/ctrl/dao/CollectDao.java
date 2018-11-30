package ctrl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Collect;

public class CollectDao extends BaseDao {

	/**
	 * Ĭ�����һ���û������ղ��б�
	 * @param userId
	 * @return �ղ��б�ID
	 */
	public String addCollect(String userId){
		int row=0;
		String registId=getMaxId();
		String sql="insert into tb_collect values(null,'��ϲ����',"+userId+")";
		String seq="update sqlite_sequence set seq = "+registId+" where name='tb_collect'";
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
	 * ��ȡ�û��ղ��б�
	 * @param collectId
	 * @return �û��ղ��б�
	 */
	public Collect getCollect(String userId){
		ResultSet set=null;
		Collect collect=null;
		String sql="select * from tb_collect where userId="+userId;
		set=select(sql);
		try {
			if(set.next()) {
				collect=new Collect();
				collect.setCollectId(set.getString("collectId"));
				collect.setName(set.getString("name"));
				collect.setUserId(set.getString("userId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return collect;
	}
	/**
	 * ��ȡ���һ���û���ID
	 * @return ����û�ID
	 */
	public String getMaxId(){
		String strId=null;
		ResultSet idSet=null;
		ArrayList<String> strIdList=new ArrayList<String>();
		String sql="select collectId from tb_collect order by collectId asc";
		idSet=select(sql);
		try {
			while (idSet.next()) {
				strIdList.add(idSet.getString("collectId"));
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
}
