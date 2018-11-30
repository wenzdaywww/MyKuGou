package ctrl.viewctrl;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import ctrl.client.Client;
import util.Const;
import view.view.ClassPanel;
import view.view.MainFrame;
import view.view.TipDialog;

public class ClassPanelCtrl {
	
	private ClassPanel cPanel;
	
	public ClassPanelCtrl(ClassPanel cPanel) {
		this.cPanel=cPanel;
		tabelModelCtr();
	}
	private void tabelModelCtr(){
		cPanel.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //�����λ�� 
				int col=((JTable)e.getSource()).columnAtPoint(e.getPoint()); //�����λ�� 
				String classId=(String)(cPanel.getTableModel().getValueAt(row,0));//�����ID
				String className=(String)(cPanel.getTableModel().getValueAt(row,1)); //��������
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (e.getClickCount()==2) {
						if (col==0) {//˫������ID��ȡ�����е�����������Ϣ
							if (Client.isConnect) {
								Client.getCL().getClassMusic(classId, className);
							}else {
								TipDialog.getTD().showSureMsg("����ʧ��", "��ȷ�������Ƿ�������");
							}
						}else if (col==1){
							Const.classId=classId;
							Const.type=Const.RENAME_CLASS;
							TipDialog.getTD().showClassInfo("�޸ķ�������", classId);
						}
					}
				}else if (e.getButton()==MouseEvent.BUTTON3){
					Const.type=Const.RENAME_SINGER;
					MainFrame.getMF().getPopupMenu().removeAll();
					MainFrame.getMF().getPopupMenu().add(new MenuItemCtrl("ɾ������", MenuItemCtrl.DEL_CLASS,classId));
					MainFrame.getMF().getPopupMenu().add(new MenuItemCtrl("��ӷ���", MenuItemCtrl.ADD_CLASS,classId));
					MainFrame.getMF().getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}
}
