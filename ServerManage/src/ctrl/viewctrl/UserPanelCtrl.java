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
				int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //�����λ�� 
				int col=((JTable)e.getSource()).columnAtPoint(e.getPoint()); //�����λ�� 
				String userId=(String)(uPanel.getTableModel().getValueAt(row,0));//�����ID
				String collectId=(String)(uPanel.getTableModel().getValueAt(row,1));//�����ID
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (e.getClickCount()==2) {
						if (col==0||col==1) {//˫������ID��ȡ�����е�����������Ϣ
							if (Client.isConnect) {
								Const.userId=userId;
								Const.collectId=collectId;
								Client.getCL().getUserMusic(collectId);
							}else {
								TipDialog.getTD().showSureMsg("����ʧ��", "��ȷ�������Ƿ�������");
							}
						}else if (col==2){
							Const.userId=userId;
							Const.type=Const.RENAME_USER_NAME;
							TipDialog.getTD().showUserInfo("�޸��û���", userId, "�û���");
						}else if (col==3){
							Const.userId=userId;
							Const.type=Const.RENAME_PWD;
							TipDialog.getTD().showUserInfo("�޸��û�����", userId, "����");
						}
					}
				}else if (e.getButton()==MouseEvent.BUTTON3){
					MainFrame.getMF().getPopupMenu().removeAll();
					MainFrame.getMF().getPopupMenu().add(new MenuItemCtrl("ɾ���û�", MenuItemCtrl.DEL_USER, userId, collectId, 0));
					MainFrame.getMF().getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}
}
