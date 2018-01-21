package util;

import java.io.File;
import java.util.ArrayList;

public class MyFileUtil {
		private static ArrayList<String> filePath=new ArrayList<String>();
		/**
		 * 获取文件夹中所有歌曲
		 * @param dirPath
		 * @return 音乐文件路径集合
		 */
		public static  ArrayList<String> addMusicDir(String dirPath){
			filePath.clear();
			File file=new File(dirPath);
			isMusicFile(file);
			return filePath;
		}
		/**
		 * 删除文件
		 * @param filePath
		 */
		public static boolean delFile(String filePath){
			boolean isOk=false;
			String musicPath=filePath.replaceAll("\\\\", "/");
			File file=new File(musicPath);
			if (file.isFile()&&file.exists()) {
				isOk=file.getAbsoluteFile().delete();
			}
			return isOk;
		}
		/**
		 * 获取文件名
		 * @param filePath
		 * @return
		 */
		public static String getMusicName(String filePath){
			String musicPath=filePath.replaceAll("\\\\", "/");
			File file=new File(musicPath);
			return file.getName();
		}
		/**
		 * 查询文件中所有MP3、wav文件
		 * @param file
		 */
		private static void isMusicFile(File file){
			if (file.isDirectory()) {
				File fileArray[] =file.listFiles();//获取文件夹中的文件数组fileList，包括文件夹和文件
				for (int i = 0; i < fileArray.length; i++) {
					if (fileArray[i].isDirectory()) {
					}else {
						String str=fileArray[i].getName();
						if (str.substring(str.length()-3, str.length()).equals("mp3")) {
							filePath.add("mp3"+fileArray[i].getAbsolutePath());
						}else if (str.substring(str.length()-3, str.length()).equals("wav")){
							filePath.add("wav"+fileArray[i].getAbsolutePath());
						}
					}
				}
			}
		}
		/**
		 * 获取单首歌曲
		 * @param musicPath
		 * @return mp3/wav+文件绝对路径
		 */
		public static String addOneMusic(String musicPath){
			String musicType=null;
			File file=new File(musicPath);
			String str=file.getName();
			if (str.substring(str.length()-3, str.length()).equals("mp3")) {
				musicType="mp3"+file.getAbsolutePath();
			}else if (str.substring(str.length()-3, str.length()).equals("wav")){
				musicType="wav"+file.getAbsolutePath();
			}
			return musicType;
		}
		public static ArrayList<String> getFilePath() {
			return filePath;
		}
	}
