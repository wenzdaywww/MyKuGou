package ctrl.playctrl;

import util.Const;
import java.io.File;
import model.MusicInfo;
import javax.media.Time;
import util.PictureUtil;
import ctrl.dao.MInfoDao;
import javax.media.Player;
import javax.media.Manager;
import view.view.MainFrame;
import view.view.TipDialog;
import java.text.DecimalFormat;
import javax.media.MediaLocator;
import javax.media.EndOfMediaEvent;
import javax.media.ControllerAdapter;
import javax.media.RealizeCompleteEvent;
import javax.media.PrefetchCompleteEvent;


public class PlayMusic {

	private Player player;
	private MusicInfo mInfo;
	private MediaLocator mLocator;
	private static PlayMusic playMusic;
	private String musicPath="musicPath";

	public static PlayMusic getPM(){
		if (playMusic==null) {
			playMusic=new PlayMusic();
		}
		return playMusic;
	}
	private PlayMusic(){

	}
	/**
	 * ��ʼ��������
	 * @param mInfo ��������Ϣ
	 */
	public void startPlay(MusicInfo mInfo){
		startPlay(mInfo.getMusicPath());
		this.mInfo=mInfo;
		Const.isListening=false;
		MInfoDao mDao=new MInfoDao();
		String isExist=mDao.isExist(mInfo.getMusicPath());
		if (isExist!=null) {
			String playCount=mDao.getPlayCount(mInfo.getMusicId());
			mDao.setPlayCount(mInfo.getMusicId(),Integer.toString(Integer.parseInt(playCount)+1));
			mDao.getPlayCount(mInfo.getMusicId());
		}
	} 
	/**
	 * ��ʼ��������
	 * @param mInfo ��������Ϣ
	 */
	public void startPlay(String musicPath,MusicInfo mInfo){
		Const.isListening=true;
		startPlay(musicPath);
		this.mInfo=mInfo;
	} 
	/**
	 * ��ʼ��������
	 * @param musicPath
	 */
	private void startPlay(String musicPath){
		File musicFile=new File(musicPath);
		if (musicFile.exists()) {
			this.musicPath=musicPath;
			Const.isPlaying=false;
			mLocator=new MediaLocator("file:///" + this.musicPath);
			try {
				player = Manager.createPlayer(mLocator);
				player.realize();//ʶ������
				player.addControllerListener(new MusicHandle());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			MainFrame.getMF().setLblPlayName("�����ļ������ڣ��޷����Ÿø���������");
			TipDialog.getTD().showSureMsg("������ʾ", "�޷����Ÿø������ø����ļ������ڡ�");
		}
	}
	/**
	 * �ر�����
	 */
	public void closePlay(){
		Const.isPlaying=false;
		player.close();
	}
	/**
	 * ���ò��ŵ�ʱ��,���ڻ�������
	 * @param playTime
	 */
	public void setPlayTime(int playTime){
		int a=playTime/100;
		int b=playTime%100;
		String string=Integer.toString(a)+"."+Integer.toString(b);
		double d=Double.parseDouble(string);
		player.setMediaTime(new Time(d));
	}
	/**
	 * ��ȡ����������ʱ�����������û������ֵ
	 * @return
	 */
	public int getMusicTime(){
		DecimalFormat df=new DecimalFormat("#####0.00");
		double d=0.0d;
		d=Double.parseDouble(df.format(player.getDuration().getSeconds()));
		return (int) (d*100);
	}
	/**
	 * ��ȡ������ǰ���ŵ�ʱ�䣬���ڽ�����ʾ
	 * @return
	 */
	public String getPlayTime(){
		return toSongTime(player.getMediaTime());
	}
	/**
	 * ��ȡ����ʱ�����û�������������
	 * @return
	 */
	public int getPlayingTime(){
		DecimalFormat df=new DecimalFormat("#####0.00");
		double d=Double.parseDouble(df.format(player.getMediaTime().getSeconds()));
		return (int) (d*100);
	}
	/**
	 * ��ȡ����ʱ�������ڽ�����ʾ
	 * @return
	 */
	public String getSongTime(){
		return toSongTime(player.getDuration());
	}
	/**
	 * ���ִ�����
	 * @author wWw
	 *
	 */
	private class MusicHandle extends ControllerAdapter{
		@Override
		//ʶ������
		public void realizeComplete(RealizeCompleteEvent e) {
			player.prefetch();
			MainFrame.getMF().getSliderSong().setValue(0);
			String musicName=mInfo.getSinger()+" - "+mInfo.getMusicName();
			String trayName="���ڲ��ţ�"+mInfo.getSinger()+" - "+mInfo.getMusicName();
			if (mInfo.getSinger().equals("δ֪")) {
				musicName=mInfo.getMusicName();
				trayName="���ڲ��ţ�"+mInfo.getMusicName();
			}else {
				if (Const.isListening) {
					musicName="������"+mInfo.getSinger()+" - "+mInfo.getMusicName();
				}else {
					musicName=mInfo.getSinger()+" - "+mInfo.getMusicName();
				}
				trayName="���ڲ��ţ�"+mInfo.getSinger()+" - "+mInfo.getMusicName();
			}
			MainFrame.getMF().setLblPlayName(musicName);
			MainFrame.getMF().getTrayIcon().setToolTip(trayName);
		}
		@Override
		//��������
		public void prefetchComplete(PrefetchCompleteEvent e) {
			player.start();
			Const.isPlaying=true;
			MainFrame.getMF().getLblPlay().setIcon(PictureUtil.STOP_ICO);
			MainFrame.getMF().getLblPlay().setToolTipText("��ͣ");
			MainFrame.getMF().getSliderSong().setMinimum(0);
			MainFrame.getMF().getSliderSong().setMaximum(PlayMusic.getPM().getMusicTime());
		}
		@Override
		//���ֲ��Ž���
		public void endOfMedia(EndOfMediaEvent e) {
			Const.isPlaying=false;
			MainFrame.getMF().getMFC().playEnd();
		}
	}
	/**
	 * ת������ʱ��
	 * @param stime
	 * @return
	 */
	private String toSongTime(Time stime){
		int intHour;
		int intMin;
		int intSec;
		String strHour;
		String strMin;
		String strSec;
		Time time=stime;
		String songTime=null;
		intHour=((int)(time.getSeconds()/3600));
		intMin=((int)((time.getSeconds()/60)%60));
		intSec=((int)(time.getSeconds()%60));
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
	public String getMusicPath() {
		return musicPath;
	}
	public Player getPlayer() {
		return player;
	}
	public MusicInfo getmInfo() {
		return mInfo;
	}

}
