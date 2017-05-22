package Vista;

import controller.EspectatorController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by natal on 20/5/2017.
 */
public class EspectatorView extends JFrame {

    private JList List;
    private DefaultListModel model;
    private JPanel rightList;
    private JPanel leftList;

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

        rightList = new JPanel();
        leftList = new JPanel();

        model = new DefaultListModel();
        model.addElement(rightList);
        model.addElement(leftList);
        List = new JList(model);

        final JScrollPane auxScroll = new JScrollPane(List);
        auxPane.add(auxScroll, BorderLayout.CENTER);
    }

    public JList getLeftList() {
        return List;
    }

    public void setLeftList(JList List) {
        this.List = List;
    }

    public DefaultListModel getModel() {
        return model;
    }

    public void setModel(DefaultListModel model) {
        this.model = model;
    }

    public JPanel getRightList() {
        return rightList;
    }

    public void setRightList(JPanel rightList) {
        this.rightList = rightList;
    }

    public void setLeftList(JPanel leftList) {
        this.leftList = leftList;
    }

    public void registerEspectator(EspectatorController espectatorController) {
        leftList.addMouseListener(espectatorController);
    }
}
