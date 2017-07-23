package View;

import Controller.ReplaysController;
import Model.JTableModel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by natal on 22/5/2017.
 */
public class ReplaysView extends JFrame {
    private JTable table;

    private JButton jbDeleteReplay;

    public ReplaysView(){
        setSize(420,500);
        setTitle("MakiTetrix - Replays");
        setLocationRelativeTo(null);

        //creem els components principals
        JPanel principal = new JPanel(new BorderLayout());
        JPanel north = new JPanel();
        JPanel south = new JPanel(new GridLayout(1, 2));
        JPanel auxPane = new JPanel(new BorderLayout());

        // Afegim el boto de eliminar replay a un panell inferior
        JPanel deletePane = new JPanel(new BorderLayout());
        jbDeleteReplay = new JButton("Delete Replay");
        jbDeleteReplay.setVerticalAlignment(SwingConstants.CENTER);
        jbDeleteReplay.setHorizontalAlignment(SwingConstants.CENTER);
        deletePane.add(jbDeleteReplay, BorderLayout.CENTER);

        //afegim un panell a la dreta i un a l'esquerra al panell inferior
        south.add(auxPane);

        //afegim el panell inferior i superior al panell principal
        principal.add(north, BorderLayout.NORTH);
        principal.add(south, BorderLayout.CENTER);
        principal.add(deletePane, BorderLayout.SOUTH);
        setContentPane(principal);

        //afegim els components al panell superior
        JPanel topPane = new JPanel();
        JLabel topLabel = new JLabel("REPLAYS");
        topLabel.setFont (topLabel.getFont ().deriveFont (40.0f));
        topPane.add(topLabel, BorderLayout.NORTH);
        north.add(topPane);

        JTableModel jTableModel = new JTableModel();
        table = new JTable(jTableModel);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane auxScroll = new JScrollPane(table);
        auxPane.add(auxScroll, BorderLayout.CENTER);
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public void registerReplay(ReplaysController controller){
        table.addMouseListener(controller);
        jbDeleteReplay.setActionCommand("DELETE");

        jbDeleteReplay.addActionListener(controller);
    }
}
