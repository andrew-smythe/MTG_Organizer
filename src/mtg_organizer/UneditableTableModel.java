package mtg_organizer;
import javax.swing.table.*;

public class UneditableTableModel extends DefaultTableModel {
	
	public UneditableTableModel(Object[][] o, String[] s) {
		super(o, s);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
