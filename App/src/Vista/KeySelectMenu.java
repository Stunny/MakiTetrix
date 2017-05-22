package Vista;

import controller.KeySelectMenuController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by natal on 20/5/2017.
 */
public class KeySelectMenu extends JFrame {

    public static String SAVE = "save";
    private JButton button;
    private JTextField textDerecha;
    private JTextField textIzquierda;
    private JTextField textAbajo;
    private JTextField textRotarIzquierda;
    private JTextField textRotarDerecha;
    private JTextField textPause;

    public KeySelectMenu(){
        setSize(420,500);
        setTitle("MakiTetrix - Configuración");
        setLocationRelativeTo(null);

        //creamos un panerl superior con un grid para las teclas y un panel inferior para el boton de save
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        JPanel title = new JPanel();
        JPanel principal = new JPanel(new BorderLayout());

        principal.add(title, BorderLayout.NORTH);
        principal.add(top, BorderLayout.CENTER);
        principal.add(bottom, BorderLayout.SOUTH);
        setContentPane(principal);

        JLabel titleLabel = new JLabel("Configuración de las teclas");
        titleLabel.setFont (titleLabel.getFont ().deriveFont (30.0f));
        title.add(titleLabel);

        JPanel gridPane = new JPanel(new GridLayout(6,2));

        //generamos los textArea y los Labels dentro del grid
        JLabel labelDerecha = new JLabel("Derecha");
        gridPane.add(labelDerecha);
        textDerecha = new JTextField("D");
        gridPane.add(textDerecha);

        JLabel labelIzquierda = new JLabel("Izquierda");
        gridPane.add(labelIzquierda);
        textIzquierda = new JTextField("A");
        gridPane.add(textIzquierda);

        JLabel labelAbajo = new JLabel("Abajo");
        gridPane.add(labelAbajo);
        textAbajo = new JTextField("S");
        gridPane.add(textAbajo);

        JLabel labelRotarIzquierda = new JLabel("Rotar izquierda");
        gridPane.add(labelRotarIzquierda);
        textRotarIzquierda = new JTextField("Q");
        gridPane.add(textRotarIzquierda);

        JLabel labelRotarDerecha = new JLabel("Rotar derecha");
        gridPane.add(labelRotarDerecha);
        textRotarDerecha = new JTextField("E");
        gridPane.add(textRotarDerecha);

        JLabel labelPause = new JLabel("Pause");
        gridPane.add(labelPause);
        textPause = new JTextField("P");
        gridPane.add(textPause);

        top.add(gridPane, CENTER_ALIGNMENT);

        button = new JButton(SAVE);
        bottom.add(button);
    }

    public void registerKey(KeySelectMenuController c){
        button.addActionListener(c);
    }

    public void showKeySelectError(String errorMessage){
        String[] options = { "OK" };
        JOptionPane.showOptionDialog(this, errorMessage,
                "ERROR AL CONFIGURAR LAS TECLAS", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                null, options, options[0]);
    }

    public JTextField getTextDerecha() {
        return textDerecha;
    }

    public JTextField getTextIzquierda() {
        return textIzquierda;
    }

    public JTextField getTextAbajo() {
        return textAbajo;
    }

    public JTextField getTextRotarIzquierda() {
        return textRotarIzquierda;
    }

    public JTextField getTextRotarDerecha() {
        return textRotarDerecha;
    }

    public JTextField getTextPause() {
        return textPause;
    }
}
