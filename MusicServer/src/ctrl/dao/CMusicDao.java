package ctrl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.CollectMusic;
import model.MusicInfo;

public class CMusicDao extends BaseDao {
	/**
	 * �ղ��б���Ӹ���
	 * @param collectId
	 * @param musicId
	 * @return true��ӳɹ�
	 */
	public boolean addCMusic(String collectId,String musicId){
		int row=0;
		boolean isOk=false;
		String sql="insert into tb_collectMusic values(null,"+collectId+","+musicId+")";
		String seq="update sqlite_sequence set seq = "+getMaxId()+" where name='tb_collectMusic'";
		update(seq);
		row=update(sql);
		if (row!=0) {
			isOk=true;
		}
		return isOk;
	}
	/**
	 * ɾ���ղ��еĸ���
	 * @param collectId
	 * @param musicId
	 * @return trueɾ���ɹ�
	 */
	public boolean delCMusic(String collectId,String musicId){
		int row=0;
		boolean isOk=false;
		String sql="delete from tb_collectMusic where collectId="+collectId+" and musicId="+musicId;
		row=update(sql);
		if (row!=0) {
			isOk=true;
		}
		return isOk;
	}
	/**
	 * ��ȡ�ղ��б��е����и���
	 * @param collectId
	 * @return ��ȡ�ղ��б��е����и���
	 */
	public ArrayList<CollectMusic> getCMusic(String collectId){
		ResultSet set=null;
		ArrayList<CollectMusic> musicList=new ArrayList<CollectMusic>();
		String sql="select * from tb_collectMusic where collectId="+collectId;
		set=select(sql);
		try {
			while(set.next()) {
				CollectMusic music=new CollectMusic();
				music.setCllectId(set.getString("collectId"));
				music.setMusicId(set.getString("musicId"));
				musicList.add(music);
			}
		} catch (SQLException e) {
		}
		return musicList;
	}
	/**
	 * �ղر����Ƿ��Ѿ��иø���
	 * @return
	 */
	public boolean isExist(String collectId,String musicId){
		boolean isExist=false;
		ResultSet set=null;
		String sql="select * from tb_collectMusic where collectId="+collectId+" and musicId="+musicId;
		set=select(sql);
		try {
			if (set.next()) {
				isExist=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isExist;
	}
	/**
	 * ��ȡID
	 * @return ����û�ID
	 */
	private String getMaxId(){
		String strId=null;
		ResultSet idSet=null;
		ArrayList<String> strIdList=new ArrayList<String>();
		String sql="select id from tb_collectMusic order by id asc";
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
	 * ��ȡ�ղر��еĸ�����Ϣ
	 * @param colloectId
	 * @return
	 */
	public ArrayList<MusicInfo> getCollectMusic(String colloectId){
		ArrayList<MusicInfo> musicList=new ArrayList<MusicInfo>();
		ResultSet set=null;
		String sql="select b.musicId,b.musicName,b.singer,b.musicPath,b.musicTime,b.playCount,b.netMusic from "+
		"tb_collectMusic a left join tb_musicInfo b on a.musicId=b.musicId where collectId="+colloectId+" order by playCount desc";
		set=select(sql);
		try {
			while(set.next()) {
				MusicInfo music=new MusicInfo();
				music.setMusicId(set.getString("musicId"));
				music.setMusicName(set.getString("musicName"));
				music.setSinger(set.getString("singer"));
				music.setMusicPath(set.getString("musicPath"));
				music.setMusicTime(set.getString("musicTime"));
				music.setPlayCount(set.getString("playCount"));
				music.setNetMusic(set.getString("netMusic"));
				musicList.add(music);
			}
		} catch (SQLException e) {
		}
		return musicList;
	}
}
