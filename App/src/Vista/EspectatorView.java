package Vista;

import javax.swing.*;
import java.awt.*;

/**
 * Created by natal on 20/5/2017.
 */
public class EspectatorView extends JFrame {

    private JList leftList;
    private DefaultListModel model;

    public EspectatorView(){
        setSize(420,500);
        setTitle("MakiTetrix - Espectador");
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
        JLabel topLabel = new JLabel("ESPECTADOR");
        topLabel.setFont (topLabel.getFont ().deriveFont (40.0f));
        topPane.add(topLabel, BorderLayout.NORTH);
        north.add(topPane);

        JTextArea rightTextArea = new JTextArea();
        model = new DefaultListModel();
        leftList = new JList(model);

        rightTextArea.setEditable(false);
        final JScrollPane auxScroll = new JScrollPane(rightTextArea);
        auxPane.add(auxScroll, BorderLayout.CENTER);
    }
}
