package View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Admin on 20/03/2017.
 */
public class View extends JFrame{
    private JPanel principal;
    private JPanel north;
    private JPanel south;
    private JPanel rightPane;
    private JPanel leftPane;
    private JLabel topLabel;
    private JTextArea rightTextArea;
    private JTextArea leftTextArea;
    private JButton borrar;

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
        topLabel = new JLabel("ADMINISTRADOR");
        topLabel.setFont (topLabel.getFont ().deriveFont (40.0f));
        north.add(topLabel);

        //afegim els components al panell inferior
        rightTextArea = new JTextArea();
        leftTextArea = new JTextArea();
        rightTextArea.setEditable(false);
        leftTextArea.setEditable(false);
        borrar = new JButton("Borrar");
        final JScrollPane rightScroll = new JScrollPane(rightTextArea);
        final JScrollPane leftScroll = new JScrollPane(leftTextArea);
        rightPane.add(borrar, BorderLayout.SOUTH);
        rightPane.add(rightScroll, BorderLayout.CENTER);
        leftPane.add(leftScroll, BorderLayout.CENTER);


        //configuracio basica de la nostra finestra
        setSize(500, 700);
        setTitle("Administrator view");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
