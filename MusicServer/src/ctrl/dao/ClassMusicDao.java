package ctrl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.MusicInfo;

public class ClassMusicDao extends BaseDao {
	
	/**
	 * 获取分类中的歌曲信息
	 * @param classId
	 * @return
	 */
	public ArrayList<MusicInfo> getClassMusic(String classId){
		ResultSet set=null;
		ArrayList<MusicInfo> musicList=new ArrayList<MusicInfo>();
		String sql="select musicId,musicName,singer,musicPath,musicTime,playCount,netMusic from "
		+"(select * from tb_musicInfo a left join tb_classMusic b on a.musicId=b.musicId)"
		+"where classId="+classId+" order by playCount desc";
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
			e.printStackTrace();
		}
		return musicList;
	}
	/**
	 * 添加音乐到分类
	 * @param className
	 * @return
	 */
	public boolean addMusicClass(String classId,String musicId){
		int row=0;
		boolean isOk=false;
		ResultSet set=null;
		String sql="insert into tb_classMusic values (null,"+classId+","+musicId+")";
		String seq="update sqlite_sequence set seq = "+getMaxId()+" where name='tb_classMusic'";
		String canAdd="select * from tb_classMusic where classId="+classId+" and musicId="+musicId;
		update(seq);
		set=select(canAdd);
		try {
			if (!set.next()) {
				row=update(sql);
				if (row!=0) {
					isOk=true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isOk;
	}
	/**
	 * 删除分组中音乐
	 * @param classId
	 * @return
	 */
	public boolean delMusicClass(String classId,String musicId){
		int row=0;
		boolean isOk=false;
		String sql="delete from tb_classMusic where classId="+classId+" and musicId="+musicId;
		row=update(sql);
		if (row!=0) {
			update(sql);
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
		String sql="select id from tb_classMusic order by id asc";
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
}
