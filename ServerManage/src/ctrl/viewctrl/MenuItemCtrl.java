package ctrl.viewctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import ctrl.client.Client;
import util.Const;
import view.view.TipDialog;

@SuppressWarnings("serial")
public class MenuItemCtrl extends JMenuItem {
	
	public static final String ADD_CLASS="��ӷ���";
	public static final String DEL_CLASS="ɾ������";
	public static final String DEL_MUSIC="ɾ������";
	public static final String ADD_MUSIC_TO_CLASS="��ӵ�����";
	public static final String DEL_USER="ɾ���û�";
	public static final String DEL_USER_MUSIC="ɾ���û�����";
	private String type;
	private String classId;
	private String musicId;
	private String userId;
	private String collectId;
	/**
	 * ���ַ��൯���˵�
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
	 * ���ֲ˵�
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
	 * �û��б�Ĳ˵�
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
	 * �û��ղ����ֲ˵�
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
					TipDialog.getTD().showClassInfo("��ӷ���", classId);
					TipDialog.getTD().getTextMusic().setText("\n\n�½��ķ�����Ϊ��");
					break;
				case DEL_CLASS:
					Const.type=Const.DEL_CLASS;
					Const.classId=classId;
					TipDialog.getTD().showSureMsg("ɾ������", "ȷ��ɾ��IDΪ��"+classId+"�ĸ���������");
					break;
				case DEL_MUSIC:
					Const.type=Const.DEL_MUSIC;
					Const.musicId=classId;
					if (Client.getCL().getNowClassId().equals("")||Client.getCL().getNowClassId().equals("All")) {
						TipDialog.getTD().showSureMsg("ɾ������", "ȷ���Ӹ�����Ϣ����ɾ��IDΪ��"+classId+"�ĸ����𣿰��������������еķ��ࡣ");
					}else {
						TipDialog.getTD().showSureMsg("ɾ������", "ȷ��ֻ�Ӹ����������ɾ��IDΪ��"+classId+"�ĸ�����");
					}
					break;
				case ADD_MUSIC_TO_CLASS:
					if (Client.isConnect) {
						Client.getCL().musicAddToClass(classId, musicId);
					}else {
						TipDialog.getTD().showSureMsg("����ʧ��", "��ȷ�������Ƿ�������");
					}
					break;
				case DEL_USER:
					if (Client.isConnect) {
						Client.getCL().delUser(collectId, userId);
					}else {
						TipDialog.getTD().showSureMsg("����ʧ��", "��ȷ�������Ƿ�������");
					}
					break;
				case DEL_USER_MUSIC:
					Const.type=Const.DEL_USER_MUSIC;
					Const.musicId=musicId;
					TipDialog.getTD().showSureMsg("ɾ������", "ȷ��ֻ�Ӹ����ղر���ɾ��IDΪ��"+musicId+"�ĸ�����");
					break;
				default:
					break;
				}			
			}
		});
	}
}
