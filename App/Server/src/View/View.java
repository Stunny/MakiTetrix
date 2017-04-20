package View;

import Controller.ServerController;

import javax.swing.*;
import javax.xml.bind.Marshaller;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Admin on 20/03/2017.
 */
public class View extends JFrame{
    public static final String ACTION_BORRAR = "Borrar";

    private JPanel principal;
    private JPanel north;
    private JPanel south;
    private JPanel rightPane;
    private JPanel leftPane;
    private JLabel topLabel;
    private JTextArea rightTextArea;
    private JList leftList;
    private JButton borrar;
    private DefaultListModel model;
    private int indexSelectedUser;
    private JPanel topPane;
    private JTextField buscador;
    private JButton busca;


    public View() {
        //creem els components principals
        principal = new JPanel(new BorderLayout());
        north = new JPanel();
        south = new JPanel(new GridLayout(1,2));
        rightPane = new JPanel(new BorderLayout());
        leftPane = new JPanel(new BorderLayout());
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
        topPane = new JPanel(new BorderLayout());
        topLabel = new JLabel("ADMINISTRADOR");
        topLabel.setFont (topLabel.getFont ().deriveFont (40.0f));
        buscador = new JTextField();
        busca = new JButton("Busca Usuari");
        topPane.add(topLabel, BorderLayout.NORTH);
        topPane.add(buscador, BorderLayout.CENTER);
        topPane.add(busca, BorderLayout.SOUTH);
        north.add(topPane);

        //afegim els components al panell inferior
        rightTextArea = new JTextArea();
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

    public int getIndexSelectedUser() {
        return indexSelectedUser;
    }

    public JList getLeftList() {
        return leftList;
    }

    public JTextField getBuscador() {
        return buscador;
    }
}
