package util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

public class MusicInfoUtil {

	private static String songName; // 歌曲名4-33  
	private static String artist; // 歌手名34-63  
	private static String album; // 专辑名61-93  
	private static String year; // 年94-97  
	private static String comment; // 备注98-125  
	private static RandomAccessFile ranFile;  
	private static final String TAG = "TAG"; // 文件头1-3  
	
	/**
	 * 获取MP3文件信息，规则的MP3文件的TAG_V1(ID3V1)固定为128字节的数据
	 * 标签头"TAG"    3字节
     * 标题            30字节
     * 作者            30字节
     * 专辑            30字节
     * 出品年份        4字节
     * 备注信息        28字节
     * 保留            1字节
     * 音轨            1字节
     * 类型            1字节
	 * @param mp3File
	 */
	public static void mp3Info(String mp3FilePath){
		File mp3File=new File(mp3FilePath);
		mp3Info(mp3File);
	}
	public static void mp3Info(File mp3File){
		byte[] data=new byte[128];
		try {
			ranFile = new RandomAccessFile(mp3File, "r"); //随机读写方式打开MP3文件
			ranFile.seek(ranFile.length() - 128);  	//ranFile.length()文件的长度-128即为尾部的TAG信息
			ranFile.read(data);//将TAG信息写入字节数组中
			ranFile.close();
		} catch (IOException e) {
		}  
		String tag = new String(data, 0, 3);  
		if (tag.equalsIgnoreCase(TAG)) {  //是正规的MP3文件则获取信息
			songName = new String(data, 3, 30).trim();  
			artist = new String(data, 33, 30).trim();  
			album = new String(data, 63, 30).trim();  
			year = new String(data, 93, 4).trim();  
			comment = new String(data, 97, 28).trim();  
			if (songName.equals("")) {
				songName =mp3File.getName().substring(0, mp3File.getName().length()-4); 
			}
			if (artist.equals("")) {
				artist="未知";
			}
		} else {  //不是正规的MP3文件则手动设置信息
			songName =mp3File.getName().substring(0, mp3File.getName().length()-4);  
			artist = "未知";  
			album = null;  
			year = null;  
			comment =null;  
		}  
	}
	/**
	 * 获取MP3歌曲时间长度
	 * @param mp3File
	 * @return MP3歌曲时间长度
	 */
	public static String getMp3Time(String mp3FilePath){
		File mp3File=new File(mp3FilePath);
		return getMp3Time(mp3File);
	}
	public static String getMp3Time(File mp3File) {
		int iTime=0;
		try {
			MP3File f = (MP3File) AudioFileIO.read(mp3File);
			MP3AudioHeader audioHeader = (MP3AudioHeader)f.getAudioHeader();
			iTime=audioHeader.getTrackLength();
		} catch(Exception e) {
		}
		return toStrTime(iTime);
	}
	/**
	 * WAV文件信息
	 * @param wavFilePath
	 */
	public static void wavInfo(String wavFilePath) {
		File wavFile=new File(wavFilePath);
		wavInfo(wavFile);
	}
	public static void wavInfo(File wavFile){
		songName =wavFile.getName().substring(0, wavFile.getName().length()-4);
		artist="未知";
	}
	/**
	 * 获取wav歌曲时间长度
	 * @param wavFile
	 * @return
	 */
	public static String getWavTime(String wavFilePath){
		File wavFile=new File(wavFilePath);
		return getWavTime(wavFile);
	}
	public static String getWavTime(File wavFile){
		int iTime=0;
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(wavFile);
			clip.open(ais);
			iTime=(int) (clip.getMicrosecondLength()/1000000D);
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
		}
		return toStrTime(iTime);
	}
	/**
	 * 整型秒转换成时间格式00:00
	 * @param iTime
	 * @return
	 */
	private static String toStrTime(int iTime){
		String songTime="00:00";
		int intHour,intMin,intSec;
		String strHour,strMin,strSec;
		intSec=(int)(iTime%60);
		intMin=(int)((iTime/60)%60);
		intHour=(int)(iTime/3600);
		if (intHour>0&&intHour<10) {
			strHour="0"+Integer.toString(intHour)+":";
		}else if(intHour>9) {
			strHour=Integer.toString(intHour)+":";
		}else {
			strHour=null;
		}
		if (intMin>0&&intMin<10) {
			strMin="0"+Integer.toString(intMin)+":";
		}else if(intMin>9) {
			strMin=Integer.toString(intMin)+":";
		}else {
			strMin="00:";
		}
		if (intSec>0&&intSec<10) {
			strSec="0"+Integer.toString(intSec);
		}else if(intSec>9) {
			strSec=Integer.toString(intSec);
		}else {
			strSec="00";
		}
		if (strHour!=null) {
			songTime=strHour+strMin+strSec;
		}else {
			songTime=strMin+strSec;
		}
		return songTime;
	}
	public static String getSongName() {
		return songName;
	}
	public static String getArtist() {
		return artist;
	}
	public static String getAlbum() {
		return album;
	}
	public static String getYear() {
		return year;
	}
	public static String getComment() {
		return comment;
	}
}
