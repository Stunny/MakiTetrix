package Vista;

import controller.ReplaySelectController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by natal on 22/5/2017.
 */
public class ReplaySelectView extends JFrame {
    private JTable table;

    public ReplaySelectView(){
        setSize(420,500);
        setTitle("MakiTetrix - Replays");
        setLocationRelativeTo(null);

        //creem els components principals
        JPanel principal = new JPanel(new BorderLayout());
        JPanel north = new JPanel();
        JPanel south = new JPanel(new GridLayout(1, 2));
        JPanel auxPane = new JPanel(new BorderLayout());

        //afegim un panell a la dreta i un a l'esquerra al panell inferior
        south.add(auxPane);

        //afegim el panell inferior i superior al panell principal
        principal.add(north, BorderLayout.NORTH);
        principal.add(south, BorderLayout.CENTER);
        setContentPane(principal);

        //afegim els components al panell superior
        JPanel topPane = new JPanel();
        JLabel topLabel = new JLabel("REPLAYS");
        topLabel.setFont (topLabel.getFont ().deriveFont (40.0f));
        topPane.add(topLabel, BorderLayout.NORTH);
        north.add(topPane);

        table = new JTable();
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

    public void registerReplay(ReplaySelectController registerController){
        table.addMouseListener(registerController);
    }
}
