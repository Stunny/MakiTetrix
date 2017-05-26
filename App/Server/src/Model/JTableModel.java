package Model;

import javax.swing.table.DefaultTableModel;

/**
 * Created by angel on 26/05/2017.
 */
public class JTableModel extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
