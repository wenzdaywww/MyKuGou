package util;

import java.io.File;
import java.util.ArrayList;

public class MyFileUtil {
		private static ArrayList<String> filePath=new ArrayList<String>();
		/**
		 * ��ȡ�ļ��������и���
		 * @param dirPath
		 * @return �����ļ�·������
		 */
		public static  ArrayList<String> addMusicDir(String dirPath){
			filePath.clear();
			File file=new File(dirPath);
			isMusicFile(file);
			return filePath;
		}
		/**
		 * ɾ���ļ�
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
		 * ��ȡ�ļ���
		 * @param filePath
		 * @return
		 */
		public static String getMusicName(String filePath){
			String musicPath=filePath.replaceAll("\\\\", "/");
			File file=new File(musicPath);
			return file.getName();
		}
		/**
		 * ��ѯ�ļ�������MP3��wav�ļ�
		 * @param file
		 */
		private static void isMusicFile(File file){
			if (file.isDirectory()) {
				File fileArray[] =file.listFiles();//��ȡ�ļ����е��ļ�����fileList�������ļ��к��ļ�
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
		 * ��ȡ���׸���
		 * @param musicPath
		 * @return mp3/wav+�ļ�����·��
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
