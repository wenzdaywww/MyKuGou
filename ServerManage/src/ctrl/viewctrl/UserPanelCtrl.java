package ctrl.viewctrl;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import ctrl.client.Client;
import util.Const;
import view.view.MainFrame;
import view.view.TipDialog;
import view.view.UserPanel;

public class UserPanelCtrl {
private UserPanel uPanel;
	
	public UserPanelCtrl(UserPanel uPanel) {
		this.uPanel=uPanel;
		tabelModelCtr();
	}
	private void tabelModelCtr(){
		uPanel.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //获得行位置 
				int col=((JTable)e.getSource()).columnAtPoint(e.getPoint()); //获得列位置 
				String userId=(String)(uPanel.getTableModel().getValueAt(row,0));//分类的ID
				String collectId=(String)(uPanel.getTableModel().getValueAt(row,1));//分类的ID
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (e.getClickCount()==2) {
						if (col==0||col==1) {//双击分组ID获取分组中的所有音乐信息
							if (Client.isConnect) {
								Const.userId=userId;
								Const.collectId=collectId;
								Client.getCL().getUserMusic(collectId);
							}else {
								TipDialog.getTD().showSureMsg("操作失败", "请确认网络是否已连接");
							}
						}else if (col==2){
							Const.userId=userId;
							Const.type=Const.RENAME_USER_NAME;
							TipDialog.getTD().showUserInfo("修改用户名", userId, "用户名");
						}else if (col==3){
							Const.userId=userId;
							Const.type=Const.RENAME_PWD;
							TipDialog.getTD().showUserInfo("修改用户密码", userId, "密码");
						}
					}
				}else if (e.getButton()==MouseEvent.BUTTON3){
					MainFrame.getMF().getPopupMenu().removeAll();
					MainFrame.getMF().getPopupMenu().add(new MenuItemCtrl("删除用户", MenuItemCtrl.DEL_USER, userId, collectId, 0));
					MainFrame.getMF().getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}
}
