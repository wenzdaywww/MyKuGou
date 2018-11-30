package ctrl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.MClass;

public class MClassDao extends BaseDao {
	/**
	 * 获取所有音乐分类
	 * @return
	 */
	public ArrayList<MClass> getAllClass(){
		ResultSet set=null;
		ArrayList<MClass> classList=new ArrayList<MClass>();
		String sql="select * from tb_class";
		set=select(sql);
		try {
			while(set.next()) {
				MClass mClass=new MClass();
				mClass.setClassId(set.getString("classId"));
				mClass.setName(set.getString("name"));
				classList.add(mClass);
			}
		} catch (SQLException e) {
		}
		return classList;
	}
	/**
	 * 修改分类名
	 * @param classId
	 * @param className
	 * @return
	 */
	public boolean renameClass(String classId,String className){
		int row=0;
		boolean isOk=false;
		String sql="update tb_class set name = '"+className+"' where classId="+classId;
		row=update(sql);
		if (row!=0) {
			isOk=true;
		}
		return isOk;
	}
	/**
	 * 删除分组及其分组音乐
	 * @param classId
	 * @return
	 */
	public boolean delMusicClass(String classId){
		int row=0;
		boolean isOk=false;
		String sqlc="delete from tb_class where classId="+classId;
		String sqlm="delete from tb_classMusic where classId="+classId;
		row=update(sqlc);
		if (row!=0) {
			update(sqlm);
			isOk=true;
		}
		return isOk;
	}
	/**
	 * 添加音乐分类
	 * @param className
	 * @return
	 */
	public boolean addClassMusic(String className){
		int row=0;
		boolean isOk=false;
		String sql="insert into tb_class values (null,'"+className+"')";
		String seq="update sqlite_sequence set seq = "+getMaxId()+" where name='tb_class'";
		update(seq);
		row=update(sql);
		if (row!=0) {
			isOk=true;
		}
		return isOk;
	}
	/**
	 * 获取最后一个歌曲的ID
	 * @return 最大歌曲ID
	 */
	private String getMaxId(){
		String strId=null;
		ResultSet idSet=null;
		ArrayList<String> strIdList=new ArrayList<String>();
		String sql="select classId from tb_class order by classId asc";
		idSet=select(sql);
		try {
			while (idSet.next()) {
				strIdList.add(idSet.getString("classId"));
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
