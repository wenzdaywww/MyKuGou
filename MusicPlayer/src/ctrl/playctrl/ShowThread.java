package ctrl.playctrl;

import util.Const;
import view.view.MainFrame;

public class ShowThread extends Thread {

	private static ShowThread sThread;

	public static ShowThread getST(){
		if (sThread==null) {
			sThread=new ShowThread();
			sThread.start();
		}
		return sThread;
	}
	@Override
	public void run() {
		while (true) {
			if (Const.isPlaying) {
				try {
					sleep(500);
				} catch (InterruptedException e) {
				}
				MainFrame.getMF().getSliderSong().setValue(PlayMusic.getPM().getPlayingTime());
				MainFrame.getMF().setLblPlayTime(PlayMusic.getPM().getPlayTime(), PlayMusic.getPM().getSongTime());
			}
			if (!Client.isConnect) {
				Client.getCL();
			}
		}
	}
}
