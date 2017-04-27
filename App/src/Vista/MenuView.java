package Vista;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Admin on 27/04/2017.
 */
public class MenuView extends JFrame{
    private static final String CREATE = "Create";
    private static final String INSERT = "Insert";

    private JPanel principal;
    private JPanel top;
    private JPanel bottom;
    private JButton topButton;
    private JButton bottomButton;

    public MenuView(){
        principal = new JPanel(new BorderLayout());
        top = new JPanel();
        bottom = new JPanel();
        topButton = new JButton("Crear usuari");
        bottomButton = new JButton("Inserir dades");

        principal.add(top, BorderLayout.NORTH);
        principal.add(bottom, BorderLayout.CENTER);

        top.add(topButton);
        bottom.add(bottomButton);
        setContentPane(principal);

        topButton.setActionCommand(CREATE);
        bottomButton.setActionCommand(INSERT);

        //configuracio basica de la nostra finestra
        setSize(250, 120);
        setTitle("BBDD - Practica 2");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void registreControlador(Menu menu) {
        //topButton.addActionListener(menu);
        //bottomButton.addActionListener(menu);
    }
}
