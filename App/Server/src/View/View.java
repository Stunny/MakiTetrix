package View;

import Controller.ServerController;
import Model.JTableModel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Admin on 20/03/2017.
 */
public class View extends JFrame{
    public static final String ACTION_BORRAR = "Borrar";
    public static final String ACTION_SEARCH = "Busca";
    public static final String UPDATE = "Actualiza";

    private JTable leftJTable;
    private JButton borrar;
    private JTextField buscador;
    private JButton busca;
    private JButton actualitza;
    private JTable rightJTable;

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
        JPanel aux = new JPanel(new GridLayout(1, 2));
        buscador = new JTextField();
        busca = new JButton("Busca Usuari");
        busca.setActionCommand(ACTION_SEARCH);
        actualitza = new JButton("Actualitza llistat");
        actualitza.setActionCommand(UPDATE);
        aux.add(busca);
        aux.add(actualitza);
        topPane.add(topLabel, BorderLayout.NORTH);
        topPane.add(buscador, BorderLayout.CENTER);
        topPane.add(aux, BorderLayout.SOUTH);
        north.add(topPane);

        //afegim els components al panell inferior
        rightJTable = new JTable();
        JTableModel jTableModelLeft = new JTableModel();
        JTableModel jTableModelRight = new JTableModel();
        leftJTable = new JTable(jTableModelLeft);
        leftJTable.getTableHeader().setReorderingAllowed(false);
        rightJTable = new JTable(jTableModelRight);
        rightJTable.getTableHeader().setReorderingAllowed(false);

        borrar = new JButton("Borrar");
        borrar.setActionCommand(ACTION_BORRAR);
        final JScrollPane rightScroll = new JScrollPane(rightJTable);
        final JScrollPane leftScroll = new JScrollPane(leftJTable);
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
        leftJTable.addMouseListener(sController);
        borrar.addActionListener(sController);
        busca.addActionListener(sController);
        actualitza.addActionListener(sController);
    }

    public JTable getLeftTable() {
        return leftJTable;
    }

    public JTable getRightJTable() {
        return rightJTable;
    }

    public void setTable(JTable leftList) {
        this.leftJTable = leftList;
    }

    public JTextField getBuscador() {
        return buscador;
    }
}
