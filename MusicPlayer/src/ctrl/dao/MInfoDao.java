package ctrl.dao;

import model.MusicInfo;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class MInfoDao extends BaseDao {
	/**
	 * �������
	 * @param mInfo
	 * @param musicPath
	 * @return true��ӳɹ�
	 */
	public boolean addMusic(MusicInfo mInfo){
		int row=0;
		boolean isOk=false;
		String sql="insert into tb_musicInfo values (null,'"+mInfo.getMusicName()+"','"+mInfo.getSinger()+"','"+
				mInfo.getMusicPath()+"','"+mInfo.getMusicTime()+"',0,'"+mInfo.getNetMusic()+"')";
		String seq="update sqlite_sequence set seq = "+getMaxId()+" where name='tb_musicInfo'";
		update(seq);
		row=update(sql);
		if (row!=0) {
			isOk=true;
		}
		return isOk;
	}
	/**
	 * ��������صĸ��������ݿ�
	 * @param mInfo
	 * @param musicPath
	 * @return ����ӵĸ�����ID
	 */
	public String addMusic(MusicInfo mInfo,String musicPath){
		int row=0;
		String musicId=null;
		String sql="insert into tb_musicInfo values (null,'"+mInfo.getMusicName()+"','"+mInfo.getSinger()+"','"+
				musicPath+"','"+mInfo.getMusicTime()+"',0,'"+mInfo.getNetMusic()+"')";
		String seq="update sqlite_sequence set seq = "+getMaxId()+" where name='tb_musicInfo'";
		update(seq);
		row=update(sql);
		if (row!=0) {
			musicId=getMaxId();
		}
		return musicId;
	}
	/**
	 * �������
	 * @param mInfo
	 * @param musicPath
	 * @return true��ӳɹ�
	 */
	public boolean addMusic(String name,String singer,String path,String time,String musicType){
		int row=0;
		boolean isOk=false;
		String sql="insert into tb_musicInfo values (null,'"+name+"','"+singer+"','"+
				path+"','"+time+"',0,'"+musicType+"')";
		String seq="update sqlite_sequence set seq = "+getMaxId()+" where name='tb_musicInfo'";
		update(seq);
		row=update(sql);
		if (row!=0) {
			isOk=true;
		}
		return isOk;
	}
	/**
	 * ��ȡ���һ��������ID
	 * @return ������ID
	 */
	public String getMaxId(){
		String strId=null;
		ResultSet idSet=null;
		ArrayList<String> strIdList=new ArrayList<String>();
		String sql="select musicId from tb_musicInfo order by musicId asc";
		idSet=select(sql);
		try {
			while (idSet.next()) {
				strIdList.add(idSet.getString("musicId"));
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
	 * ɾ������
	 * @param musicId
	 * @return true��ɾ���ɹ�
	 */
	public boolean deleteMusic(String musicId){
		int row=0;
		boolean isOk=false;
		String sql="delete from tb_musicInfo where musicId="+musicId;
		row=update(sql);
		if (row!=0) {
			isOk=true;
		}
		return isOk;
	}
	/**
	 * ��ȡ�����еĸ���
	 * @param groupId
	 * @return �����еĸ���
	 */
	public ArrayList<MusicInfo> getGroupMusic(String groupId){
		ResultSet set=null;
		String sql="select musicId,musicName,singer,musicPath,musicTime,playCount,netMusic from"
				+"(select a.groupId,b.musicId,b.musicName,b.singer,b.musicPath,b.musicTime,b.playCount,b.netMusic "
				+"from tb_groupMusic a left join tb_musicInfo b on a.musicId=b.musicId)"
				+"where groupId="+groupId;
		set=select(sql);
		return getMusicInfo(set);
	}
	/**
	 * ��ȡ��������
	 * @return ��ȡ��������
	 */
	public ArrayList<MusicInfo> getAllMusic(){
		ResultSet set=null;
		String sql="select * from tb_musicInfo";
		set=select(sql);
		return getMusicInfo(set);
	}
	/**
	 * �����Ƿ������׸���
	 * @param �ļ�������
	 * @return ������ַ
	 */
	public String isExist(String musicFileName){
		String musicPath=null;
		ResultSet set=null;
		String sql="select musicPath from tb_musicInfo where musicPath like '%"+musicFileName+"%'";
		set=select(sql);
		try {
			if (set.next()) {
				musicPath=set.getString("musicPath");
			}
		} catch (SQLException e) {
		}
		return musicPath;
	}
	/**
	 * ��ȡ���׸�����Ϣ
	 * @param musicId
	 * @return ���׸�����Ϣ
	 */
	public MusicInfo getOneMusic(String musicId){
		ResultSet set=null;
		MusicInfo music=null;
		String sql="select * from tb_musicInfo where musicId="+musicId;
		set=select(sql);
		try {
			if(set.next()) {
				music=new MusicInfo();
				music.setMusicId(set.getString("musicId"));
				music.setMusicName(set.getString("musicName"));
				music.setSinger(set.getString("singer"));
				music.setMusicPath(set.getString("musicPath"));
				music.setMusicTime(set.getString("musicTime"));
				music.setPlayCount(set.getString("playCount"));
				music.setNetMusic(set.getString("netMusic"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return music;
	}
	/**
	 * �޸Ĳ��Ŵ���
	 * @param playCount
	 */
	public void setPlayCount(String musicId,String playCount){
		String sql="update tb_musicInfo set playCount="+playCount+" where musicId="+musicId;
		update(sql);
	}
	/**
	 * ��ȡ�������Ŵ���
	 * @param musicId
	 * @return
	 */
	public String getPlayCount(String musicId){
		ResultSet set=null;
		String count="0";
		String sql="select playCount from tb_musicInfo where musicId="+musicId;
		set=select(sql);
		try {
			if (set.next()) {
				count=set.getString("playCount");
			}
		} catch (NumberFormatException | SQLException e) {
		}
		return count;
	}
	/**
	 * ������������
	 * @param musicId
	 * @param newName
	 * @return true�������ɹ�
	 */
	public boolean renameMusic(String musicId,String newName){
		int row=0;
		boolean isOk=false;
		String sql="update tb_musicInfo set musicName='"+newName+"' where musicId="+musicId;
		row=update(sql);
		if (row!=0) {
			isOk=true;
		}
		return isOk;
	}
	/**
	 * ��ȡ����������Ϣ
	 * @param set
	 * @return
	 */
	private ArrayList<MusicInfo> getMusicInfo(ResultSet set){
		ArrayList<MusicInfo> musicList=new ArrayList<MusicInfo>();
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
