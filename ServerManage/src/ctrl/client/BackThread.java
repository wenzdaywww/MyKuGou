package ctrl.client;

import view.view.LoginFrame;
import view.view.MainFrame;

public class BackThread extends Thread {

	private static BackThread bThread;

	public static BackThread getBT(){
		if (bThread==null) {
			bThread=new BackThread();
			bThread.start();
		}
		return bThread;
	}
	@Override
	public void run() {
		while (true) {
			try {
				sleep(300);
			} catch (InterruptedException e) {
			}
			if (!Client.isConnect) {
				Client.getCL();
			}else {
				LoginFrame.getLF().getLblTip().setText("���������ӳɹ��������");
				MainFrame.getMF().getLblConnectState().setText("������");
			}
		}
	}
}
