package View;

import Controller.ServerController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Admin on 20/03/2017.
 */
public class View extends JFrame{
    public static final String ACTION_BORRAR = "Borrar";
    public static final String ACTION_SEARCH = "Busca";

    private JList leftList;
    private JButton borrar;
    private DefaultListModel model;
    private JTextField buscador;
    private JButton busca;


    public View() {
        //creem els components principals
        JPanel principal = new JPanel(new BorderLayout());
        JPanel north = new JPanel();
        JPanel south = new JPanel(new GridLayout(1, 2));
        JPanel rightPane = new JPanel(new BorderLayout());
        JPanel leftPane = new JPanel(new BorderLayout());
        rightPane.setBorder(BorderFactory.createTitledBorder("Informacio usuari"));
        leftPane.setBorder(BorderFactory.createTitledBorder("Usuaris"));

        //afegim un panell a la dreta i un a l'esquerra al panell inferior
        south.add(leftPane);
        south.add(rightPane);

        //afegim el panell inferior i superior al panell principal
        principal.add(north, BorderLayout.NORTH);
        principal.add(south, BorderLayout.CENTER);
        setContentPane(principal);

        //afegim els components al panell superior
        JPanel topPane = new JPanel(new BorderLayout());
        JLabel topLabel = new JLabel("ADMINISTRADOR");
        topLabel.setFont (topLabel.getFont ().deriveFont (40.0f));
        buscador = new JTextField();
        busca = new JButton("Busca Usuari");
        busca.setActionCommand(ACTION_SEARCH);
        topPane.add(topLabel, BorderLayout.NORTH);
        topPane.add(buscador, BorderLayout.CENTER);
        topPane.add(busca, BorderLayout.SOUTH);
        north.add(topPane);

        //afegim els components al panell inferior
        JTextArea rightTextArea = new JTextArea();
        model = new DefaultListModel();
        leftList = new JList(model);

        rightTextArea.setEditable(false);
        borrar = new JButton("Borrar");
        borrar.setActionCommand(ACTION_BORRAR);
        final JScrollPane rightScroll = new JScrollPane(rightTextArea);
        final JScrollPane leftScroll = new JScrollPane(leftList);
        rightPane.add(borrar, BorderLayout.SOUTH);
        rightPane.add(rightScroll, BorderLayout.CENTER);
        leftPane.add(leftScroll, BorderLayout.CENTER);

        //configuracio basica de la nostra finestra
        setSize(500, 700);
        setTitle("Administrator view");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void controladorBoto(ServerController sController){
        leftList.addMouseListener(sController);
        borrar.addActionListener(sController);
        busca.addActionListener(sController);
    }

    public DefaultListModel getModel() {
        return model;
    }

    public JList getLeftList() {
        return leftList;
    }

    public JTextField getBuscador() {
        return buscador;
    }
}
