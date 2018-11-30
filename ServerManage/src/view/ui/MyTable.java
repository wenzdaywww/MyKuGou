package view.ui;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class MyTable extends JTable{

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	
}
