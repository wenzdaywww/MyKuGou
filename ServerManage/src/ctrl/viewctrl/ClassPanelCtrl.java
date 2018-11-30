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
				int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //获得行位置 
				int col=((JTable)e.getSource()).columnAtPoint(e.getPoint()); //获得列位置 
				String classId=(String)(cPanel.getTableModel().getValueAt(row,0));//分类的ID
				String className=(String)(cPanel.getTableModel().getValueAt(row,1)); //分类名称
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (e.getClickCount()==2) {
						if (col==0) {//双击分组ID获取分组中的所有音乐信息
							if (Client.isConnect) {
								Client.getCL().getClassMusic(classId, className);
							}else {
								TipDialog.getTD().showSureMsg("操作失败", "请确认网络是否已连接");
							}
						}else if (col==1){
							Const.classId=classId;
							Const.type=Const.RENAME_CLASS;
							TipDialog.getTD().showClassInfo("修改分类名称", classId);
						}
					}
				}else if (e.getButton()==MouseEvent.BUTTON3){
					Const.type=Const.RENAME_SINGER;
					MainFrame.getMF().getPopupMenu().removeAll();
					MainFrame.getMF().getPopupMenu().add(new MenuItemCtrl("删除分类", MenuItemCtrl.DEL_CLASS,classId));
					MainFrame.getMF().getPopupMenu().add(new MenuItemCtrl("添加分类", MenuItemCtrl.ADD_CLASS,classId));
					MainFrame.getMF().getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}
}
