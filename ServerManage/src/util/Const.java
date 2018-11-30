package util;

import java.awt.Color;

/**
 * 各种常量和全局变量
 * @author wWw
 *
 */
public class Const {
	
	public static final Color BRIGHT_WHITE=new Color(255, 255, 255, 255);
	public static final Color DARK_WHITE=new Color(255, 255, 255, 180);
	public static final String IP="127.0.0.1";
	public static final int PORT=8888;
	public static final String MANAGE_ID="10000";
	
	public static int type=0;
	public static final int SUCCEED=0;
	public static final int FAIL=1;
	public static final int MAIN_EXIT=2;
	public static final int RENAME_MUSIC_NAME=3;
	public static final int RENAME_SINGER=4;
	public static final int RENAME_MUSIC_TIME=5;
	public static final int RENAME_PLAY_COUNT=6;
	public static final int DEL_CLASS=7;
	public static final int ADD_CLASS=8;
	public static final int ADD_CLASS_MUSIC=9;
	public static final int RENAME_CLASS=10;
	public static final int DEL_MUSIC=11;
	public static final int RENAME_PWD=12;
	public static final int RENAME_USER_NAME=13;
	public static final int DEL_USER_MUSIC=14;
	public static boolean canUpload=true;
	public static String musicId="";
	public static String classId="";
	public static String userId="";
	public static String collectId="";
}
