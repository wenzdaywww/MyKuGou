package view.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ctrl.viewctrl.MClassPanelCtrl;
import model.MClass;

@SuppressWarnings("serial")
public class MusicClassPan extends JPanel {

	private MClass mClass;
	private JLabel lblName;
	private MClassPanelCtrl mCtrl;
	

	public MusicClassPan(MClass mClass){
		this.mClass=mClass;
		setSize(200,20);
		setBackground(Color.white);
		setLayout(new FlowLayout());
		
		lblName = new JLabel(mClass.getName());
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setForeground(Color.BLACK);
		lblName.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 15));
		add(lblName);
		mCtrl=new MClassPanelCtrl(this, mClass);
	}
	
	public MClass getmClass(){
		return mClass;
	}
	public MClassPanelCtrl getmCtrl(){
		return mCtrl;
	}
	public JLabel getLblName() {
		return lblName;
	}
	
}
