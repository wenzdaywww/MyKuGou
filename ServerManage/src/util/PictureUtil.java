package util;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class PictureUtil {
	
	public static String SKIN="images/skin.jpg";
	
	public static final ImageIcon TIP_ICO=new ImageIcon("images/tip.png");
	public static final Image ICO=new ImageIcon("images/ico.png").getImage();
	public static final Image MAIN_BACKGROUP=new ImageIcon("images/main.png").getImage();
	public static final Image TRAY_ICO=Toolkit.getDefaultToolkit().getImage("images/trayico.png");
	public static final Image LOGIN_BACKGROUP=Toolkit.getDefaultToolkit().getImage("images/login.png");
	public static final ImageIcon ENTER_ICO=new ImageIcon("images/enter.png");
	public static final ImageIcon LOGIN_EXIT_ICO=new ImageIcon("images/loginexit.png");
	public static final ImageIcon MAIN_EXIT_ICO=new ImageIcon("images/mainexit.png");
	public static final ImageIcon MAIN_MAIN_ICO=new ImageIcon("images/mainmin.png");
	public static final ImageIcon USER_ICO=new ImageIcon("images/usertitle.png");
}
