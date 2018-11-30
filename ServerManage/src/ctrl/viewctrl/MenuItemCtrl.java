package ctrl.viewctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import ctrl.client.Client;
import util.Const;
import view.view.TipDialog;

@SuppressWarnings("serial")
public class MenuItemCtrl extends JMenuItem {
	
	public static final String ADD_CLASS="添加分组";
	public static final String DEL_CLASS="删除分组";
	public static final String DEL_MUSIC="删除音乐";
	public static final String ADD_MUSIC_TO_CLASS="添加到分组";
	public static final String DEL_USER="删除用户";
	public static final String DEL_USER_MUSIC="删除用户歌曲";
	private String type;
	private String classId;
	private String musicId;
	private String userId;
	private String collectId;
	/**
	 * 音乐分类弹出菜单
	 * @param name
	 * @param type
	 * @param classId
	 */
	public MenuItemCtrl(String name,String type,String classId){
		this.type=type;
		this.classId=classId;
		setText(name);
		mouseCtrl();
	}
	/**
	 * 音乐菜单
	 * @param name
	 * @param type
	 * @param classId
	 * @param musicId
	 */
	public MenuItemCtrl(String name,String type,String classId,String musicId){
		this.type=type;
		this.classId=classId;
		this.musicId=musicId;
		setText(name);
		mouseCtrl();
	}
	/**
	 * 用户列表的菜单
	 * @param name
	 * @param type
	 * @param userId
	 * @param collectId
	 * @param i
	 */
	public MenuItemCtrl(String name,String type,String userId,String collectId,int i){
		this.type=type;
		this.userId=userId;
		this.collectId=collectId;
		setText(name);
		mouseCtrl();
	}
	/**
	 * 用户收藏音乐菜单
	 * @param name
	 * @param type
	 * @param classId
	 * @param musicId
	 */
	public MenuItemCtrl(String name,String type,String musicId,boolean b){
		this.type=type;
		this.musicId=musicId;
		setText(name);
		mouseCtrl();
	}
	private void mouseCtrl(){
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (type) {
				case ADD_CLASS:
					Const.type=Const.ADD_CLASS;
					TipDialog.getTD().showClassInfo("添加分类", classId);
					TipDialog.getTD().getTextMusic().setText("\n\n新建的分类名为：");
					break;
				case DEL_CLASS:
					Const.type=Const.DEL_CLASS;
					Const.classId=classId;
					TipDialog.getTD().showSureMsg("删除分类", "确定删除ID为："+classId+"的歌曲分类吗？");
					break;
				case DEL_MUSIC:
					Const.type=Const.DEL_MUSIC;
					Const.musicId=classId;
					if (Client.getCL().getNowClassId().equals("")||Client.getCL().getNowClassId().equals("All")) {
						TipDialog.getTD().showSureMsg("删除音乐", "确定从歌曲信息表中删除ID为："+classId+"的歌曲吗？包括歌曲所在所有的分类。");
					}else {
						TipDialog.getTD().showSureMsg("删除音乐", "确定只从歌曲分类表中删除ID为："+classId+"的歌曲吗？");
					}
					break;
				case ADD_MUSIC_TO_CLASS:
					if (Client.isConnect) {
						Client.getCL().musicAddToClass(classId, musicId);
					}else {
						TipDialog.getTD().showSureMsg("操作失败", "请确认网络是否已连接");
					}
					break;
				case DEL_USER:
					if (Client.isConnect) {
						Client.getCL().delUser(collectId, userId);
					}else {
						TipDialog.getTD().showSureMsg("操作失败", "请确认网络是否已连接");
					}
					break;
				case DEL_USER_MUSIC:
					Const.type=Const.DEL_USER_MUSIC;
					Const.musicId=musicId;
					TipDialog.getTD().showSureMsg("删除音乐", "确定只从歌曲收藏表中删除ID为："+musicId+"的歌曲吗？");
					break;
				default:
					break;
				}			
			}
		});
	}
}
