package Vista;

import javax.swing.*;
import java.awt.*;

/**
 * Created by natal on 20/5/2017.
 */
public class KeySelectMenu extends JFrame {

    public static void main(String[] args) {
        KeySelectMenu k = new KeySelectMenu();
        k.setVisible(true);
    }

    public static String SAVE = "save";


    public KeySelectMenu(){
        setSize(420,500);
        setTitle("MakiTetrix - Login");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //creamos un panerl superior con un grid para las teclas y un panel inferior para el boton de save
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        JPanel principal = new JPanel(new BorderLayout());

        principal.add(top, BorderLayout.CENTER);
        principal.add(bottom, BorderLayout.SOUTH);
        setContentPane(principal);

        JPanel gridPane = new JPanel(new GridLayout(6,2));

        //generamos los textArea y los Labels dentro del grid
        JLabel labelDerecha = new JLabel("Derecha");
        gridPane.add(labelDerecha);
        JTextField textDerecha = new JTextField("D");
        gridPane.add(textDerecha);

        JLabel labelIzquierda = new JLabel("Izquierda");
        gridPane.add(labelIzquierda);
        JTextField textIzquierda = new JTextField("A");
        gridPane.add(textIzquierda);

        JLabel labelAbajo = new JLabel("Abajo");
        gridPane.add(labelAbajo);
        JTextField textAbajo = new JTextField("S");
        gridPane.add(textAbajo);

        JLabel labelRotarIzquierda = new JLabel("Rotar izquierda");
        gridPane.add(labelRotarIzquierda);
        JTextField textRotarIzquierda = new JTextField("Q");
        gridPane.add(textRotarIzquierda);

        JLabel labelRotarDerecha = new JLabel("Rotar derecha");
        gridPane.add(labelRotarDerecha);
        JTextField textRotarDerecha = new JTextField("E");
        gridPane.add(textRotarDerecha);

        JLabel labelPause = new JLabel("Pause");
        gridPane.add(labelPause);
        JTextField textPause = new JTextField("P");
        gridPane.add(textPause);

        top.add(gridPane);

        JButton button = new JButton(SAVE);

        bottom.add(button);
    }
}
