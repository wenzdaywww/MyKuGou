package view.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ctrl.viewctrl.MenuDialogCtrl;
import util.PictureUtil;

@SuppressWarnings("serial")
public class MenuDialog extends JDialog {
	private JLabel lblBuiltGroup;
	private JLabel lblAddOneMusic;
	private JLabel lblAddMusicDir;
	private JLabel lblClearGroup;
	private JLabel lblDelGroup;
	private JLabel lblGroupRename;
	private static MenuDialog mDialog;
	private MenuDialogCtrl mCtrl;
	private JPanel groupPanel;
	private JPanel musicPanel;
	private JPanel mainPanel;
	private JLabel lblMusicRename;
	private JLabel lblMusicDel;
	private JLabel lblMusicLove;
	
	public static MenuDialog getMD(){
		if (mDialog==null) {
			mDialog=new MenuDialog();
		}
		return mDialog;
	}
	private MenuDialog() {
		setUndecorated(true);
		getContentPane().setBackground(Color.WHITE);
		setSize(160,125);
		mainPanel=new JPanel();
		mainPanel.setBackground(Color.white);
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		setContentPane(mainPanel);
		mainPanel.setLayout(new BorderLayout(0, 0));

		//歌曲菜单
		musicPanel=new JPanel();
		musicPanel.setLayout(null);
		musicPanel.setBackground(Color.white);
		
		lblMusicRename = new JLabel("重命名");
		lblMusicRename.setOpaque(true);
		lblMusicRename.setIcon(PictureUtil.RENAME_ICO);
		lblMusicRename.setForeground(Color.BLACK);
		lblMusicRename.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblMusicRename.setBackground(Color.WHITE);
		lblMusicRename.setBounds(5, 10, 150, 29);
		musicPanel.add(lblMusicRename);
		
		lblMusicDel = new JLabel("删除");
		lblMusicDel.setOpaque(true);
		lblMusicDel.setIcon(PictureUtil.DEL_ICO);
		lblMusicDel.setForeground(Color.BLACK);
		lblMusicDel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblMusicDel.setBackground(Color.WHITE);
		lblMusicDel.setBounds(5, 49, 150, 29);
		musicPanel.add(lblMusicDel);
		
		lblMusicLove = new JLabel("收藏");
		lblMusicLove.setOpaque(true);
		lblMusicLove.setIcon(PictureUtil.ADD_LOVE_ICO);
		lblMusicLove.setForeground(Color.BLACK);
		lblMusicLove.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblMusicLove.setBackground(Color.WHITE);
		lblMusicLove.setBounds(5, 88, 150, 29);
		musicPanel.add(lblMusicLove);
		
		//分组菜单组件
		groupPanel=new JPanel();
		groupPanel.setLayout(null);
		groupPanel.setBackground(Color.white);
		
		lblBuiltGroup = new JLabel("新建列表");
		lblBuiltGroup.setIcon(PictureUtil.BUILT_GROUP_ICO);
		lblBuiltGroup.setForeground(Color.BLACK);
		lblBuiltGroup.setOpaque(true);
		lblBuiltGroup.setBackground(Color.WHITE);
		lblBuiltGroup.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblBuiltGroup.setBounds(5, 10, 150, 29);
		groupPanel.add(lblBuiltGroup);
		
		lblAddOneMusic = new JLabel("添加本地音乐");
		lblAddOneMusic.setIcon(PictureUtil.ONE_MUSIC_ICO);
		lblAddOneMusic.setForeground(Color.BLACK);
		lblAddOneMusic.setOpaque(true);
		lblAddOneMusic.setBackground(Color.WHITE);
		lblAddOneMusic.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblAddOneMusic.setBounds(5, 49, 150, 29);
		groupPanel.add(lblAddOneMusic);
		
		lblAddMusicDir = new JLabel("添加本地音乐文件夹");
		lblAddMusicDir.setIcon(PictureUtil.MUSIC_DIR_ICO);
		lblAddMusicDir.setForeground(Color.BLACK);
		lblAddMusicDir.setOpaque(true);
		lblAddMusicDir.setBackground(Color.WHITE);
		lblAddMusicDir.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblAddMusicDir.setBounds(5, 88, 150, 29);
		groupPanel.add(lblAddMusicDir);
		
		lblClearGroup = new JLabel("清空列表");
		lblClearGroup.setIcon(PictureUtil.CLEAR_GROUP_ICO);
		lblClearGroup.setForeground(Color.BLACK);
		lblClearGroup.setOpaque(true);
		lblClearGroup.setBackground(Color.WHITE);
		lblClearGroup.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblClearGroup.setBounds(5, 127, 150, 29);
		groupPanel.add(lblClearGroup);
		
		lblDelGroup = new JLabel("删除列表");
		lblDelGroup.setIcon(PictureUtil.DEL_ICO);
		lblDelGroup.setForeground(Color.BLACK);
		lblDelGroup.setOpaque(true);
		lblDelGroup.setBackground(Color.WHITE);
		lblDelGroup.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblDelGroup.setBounds(5, 166, 150, 29);
		groupPanel.add(lblDelGroup);
		
		lblGroupRename = new JLabel("重命名");
		lblGroupRename.setIcon(PictureUtil.RENAME_ICO);
		lblGroupRename.setForeground(Color.BLACK);
		lblGroupRename.setOpaque(true);
		lblGroupRename.setBackground(Color.WHITE);
		lblGroupRename.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblGroupRename.setBounds(5, 205, 150, 29);
		groupPanel.add(lblGroupRename);
		mCtrl=new MenuDialogCtrl(this);
		shwoMusicMenu();
		setVisible(false);
	}
	/**
	 * 显示分组菜单
	 */
	public void shwoGroupMenu(){
		setSize(160,245);
		mainPanel.add(musicPanel);
		mainPanel.remove(musicPanel);
		mainPanel.add(groupPanel);
		repaint();
	}
	/**
	 * 显示歌曲菜单
	 */
	public void shwoMusicMenu(){
		setSize(160,125);
		mainPanel.add(groupPanel);
		mainPanel.remove(groupPanel);
		mainPanel.add(musicPanel);
		repaint();
	}
	
	public JLabel getLblBuiltGroup() {
		return lblBuiltGroup;
	}
	public JLabel getLblAddOneMusic() {
		return lblAddOneMusic;
	}
	public JLabel getLblAddMusicDir() {
		return lblAddMusicDir;
	}
	public JLabel getLblClearGroup() {
		return lblClearGroup;
	}
	public JLabel getLblDelGroup() {
		return lblDelGroup;
	}
	public JLabel getLblRename() {
		return lblGroupRename;
	}
	public MenuDialogCtrl getmCtrl() {
		return mCtrl;
	}
	public JLabel getLblMusicRename() {
		return lblMusicRename;
	}
	public JLabel getLblMusicDel() {
		return lblMusicDel;
	}
	public JLabel getLblMusicLove() {
		return lblMusicLove;
	}
	
}
