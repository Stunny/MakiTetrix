package Vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del juego.
 * Created by jorti on 31/03/2017.
 */
public class GameView extends JFrame{

    private JPanel[][]caselles;
    private JPanel centre;
    private JLabel temps;
    private JLabel nivel;
    private JLabel puntuacion;
    private JPanel siguientePieza;
    private JLabel observador;

    public GameView(){

        setTitle("MakiTetrix - Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(432,800);

        //Pantalla principal del juego
        centre = new JPanel(new GridLayout(25,10));
        centre.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        caselles = new JPanel[25][10];

        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 10; j++) {
                JPanel p = new JPanel();
                caselles[i][j] = p;
                caselles[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                centre.add(p);
            }
        }

        //Información necesaria en la parte de arriba del nivel

        temps = new JLabel ("XX:XX");
        nivel = new JLabel ("Nivel X");
        puntuacion = new JLabel ("0000 - eres inútil");

        JPanel north = new JPanel (new FlowLayout());
        north.add(temps);
        north.add(nivel);
        north.add(puntuacion);

        getContentPane().add(north, BorderLayout.NORTH);

        //En la parte de arriba irá la siguiente figura, en la zona de abajo se verán la gente que te observa.
        JPanel east = new JPanel(new GridLayout(2,1));

        siguientePieza = new JPanel(new GridLayout(4,4));
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                JPanel p = new JPanel();
                p.setBackground(Color.blue);
                p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                siguientePieza.add(p);
            }
        }
        east.add(siguientePieza);

        //Se unen el panel del juego como el de proxima pieza
        JPanel auxmid = new JPanel (new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 144;
        c.ipady = 384;
        c.gridx = 0;
        c.gridy = 0;
        auxmid.add(centre,c);
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.PAGE_START;
        c.ipadx = 50;
        c.ipady = 72;
        c.gridx = 2;
        c.gridy = 0;
        auxmid.add(east,c);

        getContentPane().add(auxmid,BorderLayout.CENTER);

        //Creación panel de abajo para que se vean tus observadores

        JPanel observadores = new JPanel (new FlowLayout());
        observador = new JLabel ("Observador 1");
        observadores.add(observador);

        getContentPane().add(observadores,BorderLayout.SOUTH);

    }

}
