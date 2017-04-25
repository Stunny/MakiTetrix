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

    public GameView(){

        //Pantalla principal del juego
        centre = new JPanel(new GridLayout(25,10));
        centre.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        setSize(800,1260);
        caselles = new JPanel[25][10];

        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 10; j++) {
                JPanel p = new JPanel();
                p.setBackground(Color.yellow);
                caselles[i][j] = p;
                caselles[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                centre.add(p);
            }
        }
        getContentPane().add(centre, BorderLayout.CENTER);

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

        getContentPane().add(east,BorderLayout.EAST);

    }

}
