package ctrl.dao;

import model.GroupMusic;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class GMusicDao extends BaseDao {
	/**
	 * ��ӷ�������
	 * @param groupId
	 * @param musicId
	 * @return true��ӳɹ�
	 */
	public boolean addGMusic(String groupId,String musicId){
		int row=0;
		boolean isOk=false;
		String sql="insert into tb_groupMusic values (null,"+groupId+","+musicId+")";
		String seq="update sqlite_sequence set seq = "+getMaxId()+" where name='tb_groupMusic'";
		update(seq);
		row=update(sql);
		if (row!=0) {
			isOk=true;
		}
		return isOk;
	}
	/**
	 * ��ȡ����������ID
	 * @return ���ID
	 */
	public String getMaxId(){
		String strId=null;
		ResultSet idSet=null;
		ArrayList<String> strIdList=new ArrayList<String>();
		String sql="select id from tb_groupMusic order by id asc";
		idSet=select(sql);
		try {
			while (idSet.next()) {
				strIdList.add(idSet.getString("id"));
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
	 * ɾ����������
	 * @param groupId
	 * @param musicId
	 * @return trueΪɾ���ɹ�
	 */
	public boolean deleteGMusic(String groupId,String musicId){
		int row=0;
		boolean isOk=false;
		String sql="delete from tb_groupMusic where groupId="+groupId+" and musicId="+musicId;
		row=update(sql);
		if (row!=0) {
			isOk=true;
		}
		return isOk;
	}
	/**
	 * ��ȡ�����и�������
	 * @param groupId
	 * @return �������ID
	 */
	public int getMusicNum(String groupId){
		String sql="select * from tb_groupMusic where groupId="+groupId;
		ResultSet set=select(sql);
		return getMusic(set).size();
	}
	/**
	 * ��ȡ���������и���ID
	 * @param groupId
	 * @return ��������������ID
	 */
	public ArrayList<GroupMusic> getGMusic(String groupId){
		String sql="select groupId, musicId from tb_groupMusic where groupId="+groupId;
		ResultSet set=select(sql);
		return getMusic(set);
	}
	/**
	 * ���и���ID
	 * @param set
	 * @return ��������������ID
	 */
	private ArrayList<GroupMusic> getMusic(ResultSet set){
		ArrayList<GroupMusic> groupList=new ArrayList<GroupMusic>();
		try {
			while(set.next()) {
				GroupMusic gMusic=new GroupMusic();
				gMusic.setGroupId(set.getString("groupId"));
				gMusic.setMusicId(set.getString("musicId"));
				groupList.add(gMusic);
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "���ظ���ʧ�ܣ�", "��ʾ", JOptionPane.CLOSED_OPTION);
		}
		return groupList;
	}
	/**
	 *�жϷ������Ƿ��иø���
	 * @return trueΪ����
	 */
	public boolean isExist(String groupId,String musicId){
		boolean isExist=false;
		String sql="select * from tb_groupMusic where groupId="+groupId+" and musicId="+musicId;
		ResultSet set=select(sql);
		try {
			if (set.next()) {
				isExist=true;
			}
		} catch (SQLException e) {
		}
		return isExist;
	}
}
