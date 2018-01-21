package util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import view.view.GroupPanel;
import view.view.LocalSongPan;
/**
 * 各种常量和全局变量
 * @author wWw
 *
 */
public class Const {
	/**@see 本地列表集合*/
	public static ArrayList<GroupPanel> localGroupPanel=new ArrayList<GroupPanel>();
	/**@see 网络列表歌曲集合*/
	public static ArrayList<LocalSongPan> netSongPanel=new ArrayList<LocalSongPan>();
	/**@see 存放各个分组中的歌曲集合*/
	public static HashMap<String, ArrayList<LocalSongPan>> localSongPanel=new HashMap<String, ArrayList<LocalSongPan>>();
	public static final Color BRIGHT_WHITE=new Color(255, 255, 255, 255);
	public static final Color DARK_WHITE=new Color(255, 255, 255, 180);
	public static final Color BACK_COLOR=new Color(255, 255, 255, 255);
	
	public static final Color PLAYING_COLOR=new Color(235, 235, 235);
	public static final Color WHITE_COLOR=new Color(255, 255, 255);
	public static final Color MUSIC_MOUSE_IN=new Color(245, 245, 245);
	public static final Color MENU_MOUSE_IN=new Color(116, 192, 250);
	
	
	public static String openGroupId="1";
	public static boolean canHide=true;
	
	public static boolean isPlaying=false;
	public static int playStyle=DataUtil.getPlayStyle();
	public static final int PLAY_ONE=0;
	public static final int LOOP_ONE=1;
	public static final int PLAY_ORDER=2;
	public static final int PLAY_LIST=3;
	public static final int PLAY_RANDOM=4;
	
	public static int tipType=8;
	public static final int GROUP_BUILT=0;
	public static final int GROUP_CLEAR=1;
	public static final int GROUP_DEL=2;
	public static final int GROUP_RENAME=3;
	public static final int MUSIC_RENAME=4;
	public static final int MUSIC_DEL=5;
	public static final int MUSIC_LOVE=6;
	public static final int MUSIC_MOVE=7;
	public static final int SUCCEED=8;
	public static final int FAIL=9;
	public static final int EXIT=10;
	public static final int LOAD_MUSIC=11;
	
	public static final String IP="127.0.0.1";
	public static final int PORT=8888;
	/**@see 当前播放的是否为试听的歌曲*/
	public static boolean isListening=false;
	
	public static String histryPath="F:\\";
	public static String downloadPath=DataUtil.getDownloadPath();
	public static String listeingPath=DataUtil.getTempPath();
	public static int downloadNum=DataUtil.getDownloadNum();
}
