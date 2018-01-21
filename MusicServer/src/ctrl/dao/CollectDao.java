package ctrl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Collect;

public class CollectDao extends BaseDao {

	/**
	 * 默认添加一个用户歌曲收藏列表
	 * @param userId
	 * @return 收藏列表ID
	 */
	public String addCollect(String userId){
		int row=0;
		String registId=getMaxId();
		String sql="insert into tb_collect values(null,'我喜欢的',"+userId+")";
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
	 * 获取用户收藏列表
	 * @param collectId
	 * @return 用户收藏列表
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
	 * 获取最后一个用户的ID
	 * @return 最大用户ID
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
