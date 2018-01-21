package ctrl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.MusicInfo;

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
	 * ����ϴ�����
	 * @param mInfo
	 * @param musicPath
	 * @return true��ӳɹ�
	 */
	public boolean addMusic(String musicName,String singer,String musicPath,String musicTime){
		int row=0;
		boolean isOk=false;
		String sql="insert into tb_musicInfo values (null,'"+musicName+"','"+singer+"','"+
				musicPath+"','"+musicTime+"',0,'��������')";
		String seq="update sqlite_sequence set seq = "+getMaxId()+" where name='tb_musicInfo'";
		update(seq);
		row=update(sql);
		if (row!=0) {
			isOk=true;
		}
		return isOk;
	}
	/**
	 * �޸ĸ�����Ϣ
	 * @param musicId
	 * @param renameType
	 * @param rename
	 * @return
	 */
	public boolean updateMusic(String musicId,String renameType,String rename){
		int row=0;
		boolean isOk=false;
		String sql="update tb_musicInfo set "+renameType+" = '"+rename+"' where musicId="+musicId;
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
	private String getMaxId(){
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
	 * ɾ������������
	 * @param musicId
	 * @return true��ɾ���ɹ�
	 */
	public boolean deleteMusic(String musicId){
		int row=0;
		boolean isOk=false;
		String sqlm="delete from tb_musicInfo where musicId="+musicId;
		String sqlc="delete from tb_classMusic where musicId="+musicId;
		String sqlu="delete from tb_collectMusic where musicId="+musicId;
		row=update(sqlm);
		if (row!=0) {
			update(sqlc);
			update(sqlu);
			isOk=true;
		}
		return isOk;
	}
	/**
	 * ��ȡ�û��ղ��б��еĸ���
	 * @param groupId
	 * @return �����еĸ���
	 */
	public ArrayList<MusicInfo> getCollectMusic(String collectId){
		ResultSet set=null;
		String sql="select musicId,musicName,singer,musicPath,musicTime,playCount,netMusic from"
				+"(select a.collectId,b.musicId,b.musicName,b.singer,b.musicPath,b.musicTime,b.playCount,b.netMusic  "
				+"from tb_collectMusic a left join tb_musicInfo b on a.musicId=b.musicId)"
				+"where collectId="+collectId;
		set=select(sql);
		return getMusicInfo(set);
	}
	/**
	 * ���صĸ����Ƿ��������д���
	 * @param musicName
	 * @param singer
	 * @param musicTime
	 * @return ����ID
	 */
	public String isExist(String musicName,String singer,String musicTime){
		String musicId=null;
		ResultSet set=null;
		String sql="select * from tb_musicInfo where musicName='"+musicName+"' and singer='"+singer+"' and musicTime='"+musicTime+"'";
		set=select(sql);
		try {
			if (set.next()) {
				musicId=set.getString("musicId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return musicId;
	}
	/**
	 * ���Ŵ����Ӵ�С���еĸ�����Ϣ
	 * @return ��ȡ��������
	 */
	public ArrayList<MusicInfo> getAllMusic(){
		ResultSet set=null;
		String sql="select * from tb_musicInfo order by playCount desc";
		set=select(sql);
		return getMusicInfo(set);
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
	 * ��������
	 * @param musicName
	 * @return
	 */
	public ArrayList<MusicInfo> searchMusic(String musicName){
		ResultSet set=null;
		String sql="select * from tb_musicInfo where musicName like '%"+musicName+"%' or singer like '%"+musicName+"%'";
		set=select(sql);
		return getMusicInfo(set);
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
