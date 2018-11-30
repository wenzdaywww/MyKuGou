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

	private static String songName; // ������4-33  
	private static String artist; // ������34-63  
	private static String album; // ר����61-93  
	private static String year; // ��94-97  
	private static String comment; // ��ע98-125  
	private static RandomAccessFile ranFile;  
	private static final String TAG = "TAG"; // �ļ�ͷ1-3  
	
	/**
	 * ��ȡMP3�ļ���Ϣ�������MP3�ļ���TAG_V1(ID3V1)�̶�Ϊ128�ֽڵ�����
	 * ��ǩͷ"TAG"    3�ֽ�
     * ����            30�ֽ�
     * ����            30�ֽ�
     * ר��            30�ֽ�
     * ��Ʒ���        4�ֽ�
     * ��ע��Ϣ        28�ֽ�
     * ����            1�ֽ�
     * ����            1�ֽ�
     * ����            1�ֽ�
	 * @param mp3File
	 */
	public static void mp3Info(String mp3FilePath){
		File mp3File=new File(mp3FilePath);
		mp3Info(mp3File);
	}
	public static void mp3Info(File mp3File){
		byte[] data=new byte[128];
		try {
			ranFile = new RandomAccessFile(mp3File, "r"); //�����д��ʽ��MP3�ļ�
			ranFile.seek(ranFile.length() - 128);  	//ranFile.length()�ļ��ĳ���-128��Ϊβ����TAG��Ϣ
			ranFile.read(data);//��TAG��Ϣд���ֽ�������
			ranFile.close();
		} catch (IOException e) {
		}  
		String tag = new String(data, 0, 3);  
		if (tag.equalsIgnoreCase(TAG)) {  //�������MP3�ļ����ȡ��Ϣ
			songName = new String(data, 3, 30).trim();  
			artist = new String(data, 33, 30).trim();  
			album = new String(data, 63, 30).trim();  
			year = new String(data, 93, 4).trim();  
			comment = new String(data, 97, 28).trim();  
			if (songName.equals("")) {
				songName =mp3File.getName().substring(0, mp3File.getName().length()-4); 
			}
			if (artist.equals("")) {
				artist="δ֪";
			}
		} else {  //���������MP3�ļ����ֶ�������Ϣ
			songName =mp3File.getName().substring(0, mp3File.getName().length()-4);  
			artist = "δ֪";  
			album = null;  
			year = null;  
			comment =null;  
		}  
	}
	/**
	 * ��ȡMP3����ʱ�䳤��
	 * @param mp3File
	 * @return MP3����ʱ�䳤��
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
	 * WAV�ļ���Ϣ
	 * @param wavFilePath
	 */
	public static void wavInfo(String wavFilePath) {
		File wavFile=new File(wavFilePath);
		wavInfo(wavFile);
	}
	public static void wavInfo(File wavFile){
		songName =wavFile.getName().substring(0, wavFile.getName().length()-4);
		artist="δ֪";
	}
	/**
	 * ��ȡwav����ʱ�䳤��
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
	 * ������ת����ʱ���ʽ00:00
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
