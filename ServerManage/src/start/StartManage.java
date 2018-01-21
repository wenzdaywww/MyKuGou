package start;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import view.view.LoginFrame;


public class StartManage {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				LoginFrame.getLF().setVisible(true);;
			}
		});
	}
}
